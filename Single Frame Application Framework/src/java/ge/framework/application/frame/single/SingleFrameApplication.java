package ge.framework.application.frame.single;

import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.application.core.objects.ApplicationBean;
import ge.framework.application.frame.core.FrameApplication;
import ge.framework.application.frame.single.objects.SingleFrameApplicationConfiguration;
import ge.framework.application.frame.single.properties.SingleGeneralApplicationPropertiesPage;
import ge.utils.properties.PropertiesDialogPage;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 14:19
 */
public abstract class SingleFrameApplication<FRAME extends SingleFrameApplicationFrame,CONFIGURATION extends SingleFrameApplicationConfiguration> extends FrameApplication<FRAME, CONFIGURATION>
{
    private FRAME applicationFrame;

    public SingleFrameApplication( ApplicationBean applicationBean )
    {
        super( applicationBean );
    }

    @Override
    protected final void initialiseApplicationConfiguration()
    {
        initialiseSingleApplicationConfiguration();
    }

    protected abstract void initialiseSingleApplicationConfiguration();

    @Override
    protected void startupVisibleApplication()
    {
        try
        {
            Class<FRAME> frameClass = getFrameClass();
            applicationFrame = ConstructorUtils.invokeConstructor( frameClass, this );
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
    protected boolean applicationRunning()
    {
        if ( applicationFrame != null )
            return true;
        else
            return false;
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
    public FRAME discoverFocusedFrame()
    {
        return applicationFrame;
    }

    public File getLayoutDirectory()
    {
        return new File( userDirectory, getApplicationMetaDataName() );
    }

    private class SingleApplicationShutdownWindowAdapter extends WindowAdapter
    {
        private SingleFrameApplication application;

        public SingleApplicationShutdownWindowAdapter( SingleFrameApplication application )
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
