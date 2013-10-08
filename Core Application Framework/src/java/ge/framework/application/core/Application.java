package ge.framework.application.core;

import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.application.core.objects.ApplicationBean;
import ge.framework.application.core.configuration.ApplicationConfiguration;
import ge.framework.application.core.restarter.ApplicationRestarter;
import ge.framework.application.core.restarter.ApplicationRestarterCommandDetails;
import ge.framework.application.core.restarter.Install4jApplicationRestarterCommandDetails;
import ge.framework.application.core.restarter.JavaApplicationRestarterCommandDetails;
import ge.utils.VMInstance;
import ge.utils.log.LoggerEx;
import ge.utils.xml.bind.MarshallerListener;
import ge.utils.xml.bind.TypedMarshallerListener;
import ge.utils.xml.bind.TypedUnmarshallerListener;
import ge.utils.xml.bind.UnmarshallerListener;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 14:19
 */
public abstract class Application<CONFIGURATION extends ApplicationConfiguration>
{
    protected static final File userDirectory = new File( System.getProperty( "user.home" ) );

    protected ApplicationBean applicationBean;

    private ApplicationRestarter applicationRestarter;

    protected CONFIGURATION configuration;

    private TypedMarshallerListener marshallerListener = new TypedMarshallerListener();

    private TypedUnmarshallerListener unmarshallerListener = new TypedUnmarshallerListener();

    private boolean restarting;

    public Application( ApplicationBean applicationBean )
    {
        this.applicationBean = applicationBean;
    }

    public final void startup( String[] args )
    {
        LoggerEx.entry( ( Object ) args );

        Class<? extends ApplicationRestarterCommandDetails> applicationRestarterCommandDetailsClass =
                getApplicationRestarterCommandDetailsClass();

        if ( applicationRestarterCommandDetailsClass != null )
        {
            try
            {
                ApplicationRestarterCommandDetails applicationRestarterCommandDetails =
                        ConstructorUtils.invokeConstructor( applicationRestarterCommandDetailsClass );

                applicationRestarter = new ApplicationRestarter(applicationRestarterCommandDetails);
                applicationRestarter.initialiseRestarter( args );

                applicationRestarter.holdStartup();
            }
            catch ( Exception e )
            {
                LoggerEx.fatal( e.getMessage(), e );
                throw new IllegalStateException( e.getMessage(), e );
            }
        }

        if ( getAllowMultipleApplications() == false )
        {
            if ( VMInstance.isVmUnique() == false )
            {
                LoggerEx.fatal( "Another instance of this app already exists." );
                return;
            }
        }

        unmarshallerListener = new TypedUnmarshallerListener();
        marshallerListener = new TypedMarshallerListener();

        initialiseApplication( args );

        loadApplicationConfiguration();

        initialiseApplicationConfiguration();

        startupApplication();

        while ( applicationRunning() )
        {
            try
            {
                Thread.sleep( 1000 );
            }
            catch ( InterruptedException e )
            {
                // ignore
            }
        }

        LoggerEx.exit();
    }

    protected abstract boolean getAllowMultipleApplications();

    protected Class<? extends ApplicationRestarterCommandDetails> getApplicationRestarterCommandDetailsClass()
    {
        String classPath = System.getProperty( "java.class.path" );

        if ( ( classPath != null ) && ( classPath.toLowerCase().contains( "i4jruntime.jar".toLowerCase() ) == true ) )
        {
            return Install4jApplicationRestarterCommandDetails.class;
        }
        else
        {
            return JavaApplicationRestarterCommandDetails.class;
        }
    }

    protected abstract void initialiseApplication( String[] args );

    protected abstract void initialiseApplicationConfiguration();

    protected abstract void startupApplication();

    protected abstract boolean applicationRunning();

    private void loadApplicationConfiguration()
    {
        File metadataDirectory = new File( userDirectory, getApplicationMetaDataName() );
        File configFile = new File( metadataDirectory, getApplicationConfigurationName() );

        LoggerEx.trace( "Loading ApplicationConfiguration from: " + configFile.toString() );

        try
        {
            if ( configFile.exists() == false )
            {
                LoggerEx.trace( "Failed to find config file: " + configFile.toString() );

                configuration =
                        ConstructorUtils
                                .invokeConstructor( getApplicationConfigurationClass() );

                saveApplicationConfiguration();
            }
            else
            {
                JAXBContext requestContext = JAXBContext.newInstance( getApplicationConfigurationClass() );

                Unmarshaller unmarshaller = requestContext.createUnmarshaller();
                unmarshaller.setListener( unmarshallerListener );

                LoggerEx.trace( "Found config file: " + configFile.toString() );
                configuration = ( CONFIGURATION ) unmarshaller.unmarshal( configFile );
            }
        }
        catch ( JAXBException e )
        {
            LoggerEx.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( NoSuchMethodException e )
        {
            LoggerEx.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( InvocationTargetException e )
        {
            LoggerEx.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( InstantiationException e )
        {
            LoggerEx.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( IllegalAccessException e )
        {
            LoggerEx.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    protected void saveApplicationConfiguration()
    {
        File metadataDirectory = new File( userDirectory, getApplicationMetaDataName() );
        File configFile = new File( metadataDirectory, getApplicationConfigurationName() );

        LoggerEx.trace( "Saving ApplicationConfiguration to: " + configFile.toString() );

        try
        {
            if ( configFile.exists() == false )
            {
                File parentFile = configFile.getParentFile();

                if ( ( parentFile.exists() == false ) || ( parentFile.isDirectory() == false ) )
                {
                    parentFile.mkdirs();
                }
            }

            JAXBContext requestContext = JAXBContext.newInstance( getApplicationConfigurationClass() );

            Marshaller marshaller = requestContext.createMarshaller();
            marshaller.setListener( marshallerListener );

            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );

            FileOutputStream fos = new FileOutputStream( configFile );

            marshaller.marshal( configuration, fos );
        }
        catch ( JAXBException e )
        {
            LoggerEx.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( FileNotFoundException e )
        {
            LoggerEx.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    protected final String getApplicationMetaDataName()
    {
        return "."+getName()+"App";
    }

    protected final String getApplicationConfigurationName()
    {
        return getName()+"App.xml";
    }

    protected abstract Class<CONFIGURATION> getApplicationConfigurationClass();

    public abstract void processExit();

    public final void processRestart()
    {
        if ( applicationRestarter != null )
        {
            restarting = true;

            processExit();
        }
        else
        {
            LoggerEx.error( "Cannot restart the application: No restarter specified." );
        }
    }

    protected abstract void processApplicationExit();

    protected final void processShutdown()
    {
        processApplicationShutdown();

        saveApplicationConfiguration();

        if ( restarting == true )
        {
            applicationRestarter.restartApplication();
        }
    }

    protected abstract void processApplicationShutdown();

    protected abstract boolean terminateNow();

    public abstract CloseOrExitEnum closeOrExit();

    public final String getName()
    {
        return applicationBean.getName();
    }

    public final String getDisplayName()
    {
        return applicationBean.getDisplayName();
    }

    public final String getDescription()
    {
        return applicationBean.getDescription();
    }

    public ApplicationConfiguration getConfiguration()
    {
        return configuration;
    }

    public final UnmarshallerListener getUnmarshallerListener( Class aClass )
    {
        return unmarshallerListener.getListener( aClass );
    }

    public final void setUnmarshallerListener( Class aClass, UnmarshallerListener listener )
    {
        unmarshallerListener.setListener( aClass, listener );
    }

    public final MarshallerListener getMarshallerListener( Class aClass )
    {
        return marshallerListener.getListener( aClass );
    }

    public final void setMarshallerListener( Class aClass, MarshallerListener listener )
    {
        marshallerListener.setListener( aClass, listener );
    }

    public boolean isRestarting()
    {
        return restarting;
    }
}
