package ge.framework.application.visible;

import com.jidesoft.plaf.LookAndFeelFactory;
import ge.framework.application.core.Application;
import ge.framework.application.core.configuration.ApplicationConfiguration;
import ge.framework.application.core.objects.ApplicationBean;
import ge.utils.bundle.Resources;
import ge.utils.os.OS;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import javax.swing.Icon;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 03/10/13
 * Time: 16:33
 */
public abstract class VisibleApplication<CONFIGURATION extends ApplicationConfiguration> extends Application<CONFIGURATION>
{
    private final Resources resources;

    private Image smallImage;

    private Icon smallIcon;

    private Image largeImage;

    private Icon largeIcon;

    private Image macImage;

    private Icon macIcon;

    public VisibleApplication( ApplicationBean applicationBean )
    {
        super(applicationBean);

        Class<? extends VisibleApplication> thisClass = getClass();

        Package aPackage = thisClass.getPackage();

        resources = Resources.getInstance( aPackage.getName() );
    }

    @Override
    protected final void startupApplication()
    {
        initialiseJide();

        smallImage = resources.getResourceImage( getClass(), "icon", "small" );
        smallIcon = resources.getResourceIcon( getClass(), "icon", "small" );
        largeImage = resources.getResourceImage( getClass(), "icon", "large" );
        largeIcon = resources.getResourceIcon( getClass(), "icon", "large" );

        if ( OS.isMac() == true )
        {
            macImage = resources.getResourceImage( getClass(), "icon", "mac" );
            macIcon = resources.getResourceIcon( getClass(), "icon", "mac" );

            try
            {
                Class appleExtensionClass = ClassUtils.getClass( "ge.framework.application.apple.AppleExtension" );
                MethodUtils.invokeStaticMethod( appleExtensionClass, "initialiseAppleApplication", new Object[]{this}, new Class[]{Application.class} );
            }
            catch ( ClassNotFoundException e )
            {
                e.printStackTrace();
            }
            catch ( InvocationTargetException e )
            {
                e.printStackTrace();
            }
            catch ( NoSuchMethodException e )
            {
                e.printStackTrace();
            }
            catch ( IllegalAccessException e )
            {
                e.printStackTrace();
            }
        }

        startupVisibleApplication();
    }

    protected abstract void startupVisibleApplication();

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

    public final Image getSmallImage()
    {
        return smallImage;
    }

    public final Icon getSmallIcon()
    {
        return smallIcon;
    }

    public final Image getLargeImage()
    {
        return largeImage;
    }

    public final Icon getLargeIcon()
    {
        return largeIcon;
    }

    public final Image getMacImage()
    {
        return macImage;
    }

    public final Icon getMacIcon()
    {
        return macIcon;
    }
}
