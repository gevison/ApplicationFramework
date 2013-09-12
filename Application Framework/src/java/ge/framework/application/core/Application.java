package ge.framework.application.core;

import com.jidesoft.plaf.LookAndFeelFactory;
import ge.framework.application.core.dialog.ApplicationPropertiesDialog;
import ge.framework.application.core.dialog.ExitDialog;
import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.application.core.objects.ApplicationConfiguration;
import ge.framework.application.core.utils.ApplicationRestarter;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.single.SingleFrameApplicationFrame;
import ge.utils.VMInstance;
import ge.utils.log.LoggerEx;
import ge.utils.message.enums.MessageResult;
import ge.utils.os.OS;
import ge.utils.properties.PropertiesDialogPage;
import ge.utils.spring.ApplicationContextAwareObject;
import ge.utils.xml.bind.MarshallerListener;
import ge.utils.xml.bind.TypedMarshallerListener;
import ge.utils.xml.bind.TypedUnmarshallerListener;
import ge.utils.xml.bind.UnmarshallerListener;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import javax.swing.Icon;
import javax.swing.UIManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.springframework.util.Assert.hasLength;
import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 14:19
 */
public abstract class Application extends ApplicationContextAwareObject
{
    protected static final File userDirectory = new File( System.getProperty( "user.home" ) );

    private Class<? extends ApplicationRestarter> applicationRestarterClass;

    private ApplicationRestarter applicationRestarter;

    private Boolean allowMultipleApplications;

    private String name;

    private String description;

    private Image smallImage;

    private Icon smallIcon;

    private Image largeImage;

    private Icon largeIcon;

    private Icon macIcon;

    private Image macImage;

    private String metaDataName;

    private String configurationName;

    private Class<? extends ApplicationConfiguration> configurationClass;

    protected Class<? extends ApplicationFrame> frameClass;

    private ApplicationConfiguration configuration;

    private TypedMarshallerListener marshallerListener = new TypedMarshallerListener();

    private TypedUnmarshallerListener unmarshallerListener = new TypedUnmarshallerListener();

    private boolean restarting;

    @Override
    protected void validateBeanObject()
    {
        notNull( allowMultipleApplications );
        hasLength( name );
        notNull( smallImage );
        notNull( smallIcon );
        notNull( largeImage );
        notNull( largeIcon );

        if ( OS.isMac() == true )
        {
            notNull( macImage );
            notNull( smallIcon );
        }

        hasLength( metaDataName );
        hasLength( configurationName );
        notNull( configurationClass );

        notNull( frameClass );

        validateApplicationObject();
    }

    protected abstract void validateApplicationObject();

    public final void startup( String[] args )
    {
        LoggerEx.entry( args );
        if ( applicationRestarterClass != null )
        {
            try
            {
                applicationRestarter = ConstructorUtils.invokeConstructor( applicationRestarterClass );
                applicationRestarter.initialiseRestarter( args );

                String pidString = System.getProperty( ApplicationRestarter.RESTART_PID );

                if ( ( pidString != null ) && ( pidString.isEmpty() == false ) )
                {
                    int pid = Integer.parseInt( pidString );

                    while ( VMInstance.isVmRunning( pid ) == true )
                    {
                        try
                        {
                            Thread.sleep( 1000 );
                        }
                        catch ( InterruptedException e )
                        {
                        }
                    }
                }
            }
            catch ( NoSuchMethodException e )
            {
                LoggerEx.fatal( e.getMessage(), e );
                throw new IllegalStateException( e.getMessage(), e );
            }
            catch ( IllegalAccessException e )
            {
                LoggerEx.fatal( e.getMessage(), e );
                throw new IllegalStateException( e.getMessage(), e );
            }
            catch ( InvocationTargetException e )
            {
                LoggerEx.fatal( e.getMessage(), e );
                throw new IllegalStateException( e.getMessage(), e );
            }
            catch ( InstantiationException e )
            {
                LoggerEx.fatal( e.getMessage(), e );
                throw new IllegalStateException( e.getMessage(), e );
            }
            catch ( NumberFormatException e )
            {
                LoggerEx.fatal( e.getMessage(), e );
                throw new IllegalStateException( e.getMessage(), e );
            }
        }

        if ( allowMultipleApplications == false )
        {
            if ( VMInstance.isVmUnique() == false )
            {
                LoggerEx.fatal( "Another instance of this app already exists." );
                System.exit( -1 );
            }
        }

        initialiseJide();

        unmarshallerListener = new TypedUnmarshallerListener();
        marshallerListener = new TypedMarshallerListener();

        initialiseApplication( args );

        loadApplicationConfiguration();

        initialiseApplicationConfiguration();

        startupApplication();

        LoggerEx.exit();
    }

    private void initialiseJide()
    {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();

        if ( OS.isWindows() == true )
        {
            LookAndFeelFactory.installJideExtension( LookAndFeelFactory.EXTENSION_STYLE_ECLIPSE3X );
        }

        UIManager.put( "DockableFrameTitlePane.showIcon", Boolean.TRUE );
        UIManager.put( "JideTabbedPane.showIconOnTab", Boolean.TRUE );
        UIManager.put( "DockableFrameTitlePane.titleBarComponent", Boolean.FALSE );
        UIManager.put( "SidePane.alwaysShowTabText", Boolean.TRUE );

        Color contentBackground = new Color( 240, 240, 240 );
        Color workspaceBackground = new Color( 220, 220, 220 );

        UIManager.put( "ContentContainer.background", contentBackground );

        UIManager.put( "Workspace.background", workspaceBackground );

        UIManager.put( "ButtonPanel.order", "ACO" );
        UIManager.put( "ButtonPanel.oppositeOrder", "H" );
    }

    protected abstract void initialiseApplication( String[] args );

    protected abstract void initialiseApplicationConfiguration();

    protected abstract void startupApplication();

    private void loadApplicationConfiguration()
    {
        File metadataDirectory = new File( userDirectory, metaDataName );
        File configFile = new File( metadataDirectory, configurationName );

        LoggerEx.trace( "Loading ApplicationConfiguration from: " + configFile.toString() );

        try
        {
            if ( configFile.exists() == false )
            {
                LoggerEx.trace( "Failed to find config file: " + configFile.toString() );

                configuration = ConstructorUtils.invokeConstructor( configurationClass );

                saveApplicationConfiguration();
            }
            else
            {
                JAXBContext requestContext = JAXBContext.newInstance( configurationClass );

                Unmarshaller unmarshaller = requestContext.createUnmarshaller();
                unmarshaller.setListener( unmarshallerListener );

                LoggerEx.trace( "Found config file: " + configFile.toString() );
                configuration = ( ApplicationConfiguration ) unmarshaller.unmarshal( configFile );
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
        File metadataDirectory = new File( userDirectory, metaDataName );
        File configFile = new File( metadataDirectory, configurationName );

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

            JAXBContext requestContext = JAXBContext.newInstance( configurationClass );

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

    public void processApplicationProperties()
    {
        ApplicationPropertiesDialog dialog = new ApplicationPropertiesDialog( this );

        dialog.doModal();

        saveApplicationConfiguration();
    }

    public abstract List<PropertiesDialogPage> getApplicationConfigurationPages();

    public final void processExit()
    {
        LoggerEx.debug( "Processing Exit" );
        if ( terminateNow() == true )
        {
            System.exit( 0 );
        }
        else
        {
            if ( restarting == false )
            {
                if ( configuration.isAskBeforeExit() == true )
                {
                    ExitDialog exitDialog = new ExitDialog( this );

                    MessageResult messageResult = exitDialog.doModal();

                    configuration.setAskBeforeExit( !exitDialog.isCheckBoxSelected() );

                    if ( messageResult == MessageResult.CANCEL )
                    {
                        return;
                    }
                }
            }

            LoggerEx.debug( "Closing frames." );

            processApplicationExit();
        }
    }

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

    public abstract ApplicationFrame discoverFocusedFrame();

    public void setApplicationRestarterClass( Class<? extends ApplicationRestarter> applicationRestarterClass )
    {
        testInitialised();
        this.applicationRestarterClass = applicationRestarterClass;
    }

    public final void setAllowMultipleApplications( Boolean allowMultipleApplications )
    {
        testInitialised();
        this.allowMultipleApplications = allowMultipleApplications;
    }

    public final String getName()
    {
        return name;
    }

    public final void setName( String name )
    {
        testInitialised();
        this.name = name;
    }

    public final String getDescription()
    {
        return description;
    }

    public final void setDescription( String description )
    {
        testInitialised();
        this.description = description;
    }

    public final Image getSmallImage()
    {
        return smallImage;
    }

    public final void setSmallImage( Image smallImage )
    {
        testInitialised();
        this.smallImage = smallImage;
    }

    public final Icon getSmallIcon()
    {
        return smallIcon;
    }

    public final void setSmallIcon( Icon smallIcon )
    {
        testInitialised();
        this.smallIcon = smallIcon;
    }

    public final Image getLargeImage()
    {
        return largeImage;
    }

    public final void setLargeImage( Image largeImage )
    {
        testInitialised();
        this.largeImage = largeImage;
    }

    public final Icon getLargeIcon()
    {
        return largeIcon;
    }

    public final void setLargeIcon( Icon largeIcon )
    {
        testInitialised();
        this.largeIcon = largeIcon;
    }

    public final Icon getMacIcon()
    {
        return macIcon;
    }

    public final void setMacIcon( Icon macIcon )
    {
        testInitialised();
        this.macIcon = macIcon;
    }

    public final Image getMacImage()
    {
        return macImage;
    }

    public final void setMacImage( Image macImage )
    {
        testInitialised();
        this.macImage = macImage;
    }

    public final String getMetaDataName()
    {
        return metaDataName;
    }

    public final void setMetaDataName( String metaDataName )
    {
        testInitialised();
        this.metaDataName = metaDataName;
    }

    public final void setConfigurationName( String configurationName )
    {
        testInitialised();
        this.configurationName = configurationName;
    }

    public final void setConfigurationClass(
            Class<? extends ApplicationConfiguration> configurationClass )
    {
        testInitialised();
        this.configurationClass = configurationClass;
    }

    public ApplicationConfiguration getConfiguration()
    {
        return configuration;
    }

    public Class<? extends ApplicationFrame> getFrameClass()
    {
        return frameClass;
    }

    public void setFrameClass( Class<? extends SingleFrameApplicationFrame> frameClass )
    {
        testInitialised();
        this.frameClass = frameClass;
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
