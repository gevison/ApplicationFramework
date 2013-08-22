package ge.framework.application.single;

import ge.framework.application.core.Application;
import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.application.single.properties.SingleGeneralApplicationPropertiesPage;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.single.SingleApplicationFrame;
import ge.utils.properties.PropertiesDialogPage;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 14:19
 */
public abstract class SingleApplication extends Application
{
    private Class<? extends SingleApplicationFrame> frameClass;

    private SingleApplicationFrame applicationFrame;

    @Override
    protected void validateApplicationObject()
    {
        notNull( frameClass );

        validateSingleApplicationObject();
    }

    protected abstract void validateSingleApplicationObject();

    @Override
    protected final void initialiseApplication( String[] args )
    {
        initialiseSingleApplication( args );
    }

    protected abstract void initialiseSingleApplication( String[] args );

    @Override
    protected final void initialiseApplicationConfiguration()
    {
        initialiseSingleApplicationConfiguration();
    }

    protected abstract void initialiseSingleApplicationConfiguration();

    @Override
    protected void startupApplication()
    {
        try
        {
            applicationFrame = ConstructorUtils.invokeConstructor(frameClass, this );
            applicationFrame.initialise();
            applicationFrame.loadFrame();
        }
        catch ( NoSuchMethodException e )
        {
            e.printStackTrace();
        }
        catch ( InvocationTargetException e )
        {
            e.printStackTrace();
        }
        catch ( InstantiationException e )
        {
            e.printStackTrace();
        }
        catch ( IllegalAccessException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    protected final void processApplicationShutdown()
    {
        applicationFrame = null;
        processSingleApplicationShutdown();
    }

    protected abstract void processSingleApplicationShutdown();

    @Override
    public List<PropertiesDialogPage> getApplicationConfigurationPages()
    {
        List<PropertiesDialogPage> retVal = new ArrayList<PropertiesDialogPage>();

        retVal.add( new SingleGeneralApplicationPropertiesPage() );

        List<PropertiesDialogPage> pages = getSingleApplicationConfigurationPages();

        if ( ( pages != null ) && ( pages.isEmpty() == false ) )
        {
            retVal.addAll( pages );
        }

        return retVal;
    }

    protected abstract List<PropertiesDialogPage> getSingleApplicationConfigurationPages();

    @Override
    protected void processApplicationExit()
    {
        if ( applicationFrame != null )
        {
            applicationFrame.addWindowListener( new SingleApplicationShutdownWindowAdapter( this ) );
            applicationFrame.close();
        }
    }

    @Override
    protected boolean terminateNow()
    {
        if ( applicationFrame == null )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public CloseOrExitEnum closeOrExit()
    {
        return CloseOrExitEnum.EXIT;
    }

    @Override
    public ApplicationFrame discoverFocusedFrame()
    {
        return applicationFrame;
    }

    public File getLayoutDirectory()
    {
        return new File( userDirectory, getMetaDataName() );
    }

    public Class<? extends SingleApplicationFrame> getFrameClass()
    {
        return frameClass;
    }

    public void setFrameClass( Class<? extends SingleApplicationFrame> frameClass )
    {
        testInitialised();
        this.frameClass = frameClass;
    }

    private class SingleApplicationShutdownWindowAdapter extends WindowAdapter
    {
        private SingleApplication application;

        public SingleApplicationShutdownWindowAdapter( SingleApplication application )
        {
            this.application = application;
        }

        @Override
        public final void windowClosed( WindowEvent e )
        {
            application.processShutdown();
        }
    }
}
