package ge.framework.application.frame.core;

import com.jidesoft.plaf.LookAndFeelFactory;
import ge.framework.application.core.Application;
import ge.framework.application.core.objects.ApplicationBean;
import ge.framework.application.frame.core.dialog.ApplicationPropertiesDialog;
import ge.framework.application.frame.core.dialog.ExitDialog;
import ge.framework.application.frame.core.objects.FrameApplicationConfiguration;
import ge.framework.frame.core.ApplicationFrame;
import ge.utils.log.LoggerEx;
import ge.utils.message.enums.MessageResult;
import ge.utils.os.OS;
import ge.utils.properties.PropertiesDialogPage;

import javax.swing.Icon;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Image;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/09/13
 * Time: 10:18
 */
public abstract class FrameApplication<FRAME extends ApplicationFrame, CONFIGURATION extends FrameApplicationConfiguration> extends Application<CONFIGURATION>
{
    public FrameApplication( ApplicationBean applicationBean )
    {
        super( applicationBean );
    }

    @Override
    protected final void startupApplication()
    {
        initialiseJide();

        startupFrameApplication();
    }

    protected abstract void startupFrameApplication();

    public final void processExit()
    {
        LoggerEx.debug( "Processing Exit" );
        if ( terminateNow() == true )
        {
            System.exit( 0 );
        }
        else
        {
            if ( isRestarting() == false )
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

    public void processApplicationProperties()
    {
        ApplicationPropertiesDialog dialog = new ApplicationPropertiesDialog( this );

        dialog.doModal();

        saveApplicationConfiguration();
    }

    public abstract List<PropertiesDialogPage> getApplicationConfigurationPages();

    public abstract Image getSmallImage();

    public abstract Icon getSmallIcon();

    public abstract Image getLargeImage();

    public abstract Icon getLargeIcon();

    public abstract Icon getMacIcon();

    public abstract Image getMacImage();

    public abstract Class<FRAME> getFrameClass();

    public abstract FRAME discoverFocusedFrame();
}
