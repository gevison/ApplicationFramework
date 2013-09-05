package ge.framework.frame.single;

import ge.framework.application.core.Application;
import ge.framework.application.single.SingleApplication;
import ge.framework.application.single.objects.SingleApplicationConfiguration;
import ge.framework.frame.core.ApplicationFrame;
import ge.utils.bundle.Resources;
import ge.utils.text.StringArgumentMessageFormat;

import javax.swing.Icon;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 13:25
 */
public abstract class SingleApplicationFrame extends ApplicationFrame
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.single" );

    public SingleApplicationFrame( Application application ) throws HeadlessException
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
    protected String getProfileKey()
    {
        Application application = getApplication();
        return application.getBeanName();
    }

    @Override
    public final Icon getSmallIcon()
    {
        Application application = getApplication();
        return application.getSmallIcon();
    }

    @Override
    public final Image getSmallImage()
    {
        Application application = getApplication();
        return application.getSmallImage();
    }

    @Override
    public final Icon getLargeIcon()
    {
        Application application = getApplication();
        return application.getLargeIcon();
    }

    @Override
    public final Image getLargeImage()
    {
        Application application = getApplication();
        return application.getLargeImage();
    }

    @Override
    public final Icon getMacIcon()
    {
        Application application = getApplication();
        return application.getMacIcon();
    }

    @Override
    public final Image getMacImage()
    {
        Application application = getApplication();
        return application.getMacImage();
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
        SingleApplication application = ( SingleApplication ) getApplication();
        return application.getLayoutDirectory();
    }

    @Override
    public final void setTitle( String title )
    {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "applicationName", getApplication().getName() );

        String resourceString;
        if ( title == null )
        {
            resourceString = resources.getResourceString( SingleApplicationFrame.class, "frame", "exTitle" );
        }
        else
        {
            resourceString = resources.getResourceString( SingleApplicationFrame.class, "frame", "title" );
            arguments.put( "title", title );
        }

        resourceString = StringArgumentMessageFormat.format( resourceString, arguments );

        super.setTitle( resourceString );
    }
}
