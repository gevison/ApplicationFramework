package ge.framework.frame.multi;

import ge.framework.application.core.Application;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.multi.dialog.FramePropertiesDialog;
import ge.framework.frame.multi.objects.FrameConfiguration;
import ge.framework.frame.multi.objects.FrameDefinition;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;
import ge.utils.file.LockFile;
import ge.utils.properties.PropertiesDialogPage;
import ge.utils.text.StringArgumentMessageFormat;
import ge.utils.xml.bind.MarshallerListener;
import ge.utils.xml.bind.TypedMarshallerListener;
import ge.utils.xml.bind.TypedUnmarshallerListener;
import ge.utils.xml.bind.UnmarshallerListener;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.Icon;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 13:25
 */
public abstract class MultiApplicationFrame extends ApplicationFrame
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.multi" );

    private static Logger logger = LogManager.getLogger( ApplicationFrame.class );

    private TypedMarshallerListener marshallerListener = new TypedMarshallerListener();

    private TypedUnmarshallerListener unmarshallerListener = new TypedUnmarshallerListener();

    private FrameDefinition frameDefinition;

    private FrameInstanceDetailsObject frameInstanceDetailsObject;

    private FrameConfiguration frameConfiguration;

    private LockFile lockFile;

    protected MultiApplicationFrame( Application application, FrameDefinition frameDefinition ) throws HeadlessException
    {
        super( application );
        this.frameDefinition = frameDefinition;
    }

    public final void open( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        this.frameInstanceDetailsObject = frameInstanceDetailsObject;

        loadFrameConfiguration( frameInstanceDetailsObject );

        loadFrame();
    }

    private void loadFrameConfiguration( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        File configFile = frameInstanceDetailsObject.getConfigurationFile();

        logger.trace( "Loading FrameConfiguration from: " + configFile.toString() );

        try
        {
            Class<? extends FrameConfiguration> frameConfigurationClass = getFrameConfigurationClass();

            if ( configFile.exists() == false )
            {
                logger.trace( "Failed to find config file: " + configFile.toString() );

                frameConfiguration = ConstructorUtils.invokeConstructor( frameConfigurationClass, frameInstanceDetailsObject.getName() );

                saveFrameConfiguration();
            }
            else
            {
                JAXBContext requestContext = JAXBContext.newInstance( frameConfigurationClass );

                Unmarshaller unmarshaller = requestContext.createUnmarshaller();
                unmarshaller.setListener( unmarshallerListener );

                logger.trace( "Found config file: " + configFile.toString() );

                frameConfiguration = ( FrameConfiguration ) unmarshaller.unmarshal( configFile );
            }

            lockFile = new LockFile( configFile );
            lockFile.lockFile();
        }
        catch ( JAXBException e )
        {
            logger.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( NoSuchMethodException e )
        {
            logger.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( InvocationTargetException e )
        {
            logger.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( InstantiationException e )
        {
            logger.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( IllegalAccessException e )
        {
            logger.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( IOException e )
        {
            logger.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    public void saveFrameConfiguration()
    {
        File configFile = frameInstanceDetailsObject.getConfigurationFile();

        logger.trace( "Saving FrameConfiguration to: " + configFile.toString() );

        Class<? extends FrameConfiguration> frameConfigurationClass = getFrameConfigurationClass();

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

            JAXBContext requestContext = JAXBContext.newInstance( frameConfigurationClass );

            Marshaller marshaller = requestContext.createMarshaller();
            marshaller.setListener( marshallerListener );

            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );

            FileOutputStream fos = new FileOutputStream( configFile );

            marshaller.marshal( frameConfiguration, fos );
        }
        catch ( JAXBException e )
        {
            logger.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( FileNotFoundException e )
        {
            logger.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
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

    private Class<? extends FrameConfiguration> getFrameConfigurationClass()
    {
        return frameDefinition.getConfigurationClass();
    }

    @SuppressWarnings( "unchecked" )
    public final boolean shouldCreateFrameConfigurationMenu()
    {
        if ( isFrameConfigurationDialogAllow() == true )
        {
            Class clazz = getFrameConfigurationClass();

            while ( clazz != FrameConfiguration.class )
            {
                Field[] fields = clazz.getDeclaredFields();

                if ( ( fields != null ) && ( fields.length != 0 ) )
                {
                    return true;
                }

                clazz = clazz.getSuperclass();
            }

            return false;
        }
        else
        {
            return false;
        }
    }

    protected abstract boolean isFrameConfigurationDialogAllow();

    public abstract List<PropertiesDialogPage> getFrameConfigurationPages();

    @Override
    protected final void saveFrameEx()
    {
        saveFrameConfiguration();
    }

    @Override
    protected final void processWindowCloseEx()
    {
        try
        {
            lockFile.release();
        }
        catch ( IOException e )
        {
        }
    }

    @Override
    protected final String getProfileKey()
    {
        return frameDefinition.getBeanName();
    }

    @Override
    public final Icon getSmallIcon()
    {
        return frameDefinition.getSmallIcon();
    }

    @Override
    public final Image getSmallImage()
    {
        return frameDefinition.getSmallImage();
    }

    @Override
    public final Icon getLargeIcon()
    {
        return frameDefinition.getLargeIcon();
    }

    @Override
    public final Image getLargeImage()
    {
        return frameDefinition.getLargeImage();
    }

    @Override
    public final Icon getMacIcon()
    {
        return frameDefinition.getMacIcon();
    }

    @Override
    public final Image getMacImage()
    {
        return frameDefinition.getMacImage();
    }

    @Override
    protected final boolean isStatusBarConfiguredVisible()
    {
        return frameConfiguration.isStatusBarVisible();
    }

    @Override
    protected final void setStatusBarConfiguredVisible( boolean statusBarVisible )
    {
        frameConfiguration.setStatusBarVisible( statusBarVisible );
    }

    @Override
    protected final boolean isToolButtonsConfiguredVisible()
    {
        return frameConfiguration.isToolButtonsVisible();
    }

    @Override
    protected final void setToolButtonsConfiguredVisible( boolean autoHideAreaVisible )
    {
        frameConfiguration.setToolButtonsVisible( autoHideAreaVisible );
    }

    @Override
    protected final File getLayoutDirectory()
    {
        return frameInstanceDetailsObject.getMetadataDirectory();
    }

    @Override
    public final void setTitle( String title )
    {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "applicationName", getApplication().getName() );
        arguments.put( "frameName", frameDefinition.getName() );
        arguments.put( "configName", frameDefinition.getName() );

        String resourceString;
        if ( title == null )
        {
            resourceString = resources.getResourceString( MultiApplicationFrame.class, "frame", "title" );
        }
        else
        {
            resourceString = resources.getResourceString( MultiApplicationFrame.class, "frame", "exTitle" );
            arguments.put( "title", title );
        }

        resourceString = StringArgumentMessageFormat.format( resourceString, arguments );

        super.setTitle( resourceString );
    }

    public FrameConfiguration getFrameConfiguration()
    {
        return frameConfiguration;
    }

    public FrameDefinition getFrameDefinition()
    {
        return frameDefinition;
    }

    public void processFrameProperties(  )
    {
        FramePropertiesDialog framePropertiesDialog = new FramePropertiesDialog( this );

        framePropertiesDialog.doModal();

        saveFrameConfiguration();
    }

    public FrameInstanceDetailsObject getFrameInstanceDetailsObject()
    {
        return frameInstanceDetailsObject;
    }
}
