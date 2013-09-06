package ge.framework.frame.single;

import ge.framework.application.core.Application;
import ge.framework.application.single.SingleFrameApplication;
import ge.framework.application.single.objects.SingleApplicationConfiguration;
import ge.framework.frame.core.ApplicationFrame;

import java.awt.HeadlessException;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 13:25
 */
public abstract class SingleFrameApplicationFrame extends ApplicationFrame
{
    public SingleFrameApplicationFrame( Application application ) throws HeadlessException
    {
        super( application );
    }

    @Override
    protected final void saveFrameEx()
    {
    }

    @Override
    protected final void processWindowCloseEx()
    {
    }

    @Override
    protected boolean isStatusBarConfiguredVisible()
    {
        Application application = getApplication();
        SingleApplicationConfiguration configuration =
                ( SingleApplicationConfiguration ) application.getConfiguration();
        return configuration.isStatusBarVisible();
    }

    @Override
    protected void setStatusBarConfiguredVisible( boolean statusBarVisible )
    {
        Application application = getApplication();
        SingleApplicationConfiguration configuration =
                ( SingleApplicationConfiguration ) application.getConfiguration();
        configuration.setStatusBarVisible( statusBarVisible );
    }

    @Override
    protected boolean isToolButtonsConfiguredVisible()
    {
        Application application = getApplication();
        SingleApplicationConfiguration configuration =
                ( SingleApplicationConfiguration ) application.getConfiguration();
        return configuration.isToolButtonsVisible();
    }

    @Override
    protected void setToolButtonsConfiguredVisible( boolean autoHideAreaVisible )
    {
        Application application = getApplication();
        SingleApplicationConfiguration configuration =
                ( SingleApplicationConfiguration ) application.getConfiguration();
        configuration.setToolButtonsVisible( autoHideAreaVisible );
    }

    @Override
    protected File getLayoutDirectory()
    {
        SingleFrameApplication application = ( SingleFrameApplication ) getApplication();
        return application.getLayoutDirectory();
    }
}
