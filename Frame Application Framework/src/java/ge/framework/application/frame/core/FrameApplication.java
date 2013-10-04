package ge.framework.application.frame.core;

import ge.framework.application.core.objects.ApplicationBean;
import ge.framework.application.frame.core.dialog.ApplicationPropertiesDialog;
import ge.framework.application.frame.core.dialog.ExitDialog;
import ge.framework.application.frame.core.objects.FrameApplicationConfiguration;
import ge.framework.application.visible.VisibleApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.utils.log.LoggerEx;
import ge.utils.message.enums.MessageResult;
import ge.utils.properties.PropertiesDialogPage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/09/13
 * Time: 10:18
 */
public abstract class FrameApplication<FRAME extends ApplicationFrame, CONFIGURATION extends FrameApplicationConfiguration> extends
                                                                                                                            VisibleApplication<CONFIGURATION>
{
    public FrameApplication( ApplicationBean applicationBean )
    {
        super( applicationBean );
    }

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

    public void processApplicationProperties()
    {
        ApplicationPropertiesDialog dialog = new ApplicationPropertiesDialog( this );

        dialog.doModal();

        saveApplicationConfiguration();
    }

    public abstract List<PropertiesDialogPage> getApplicationConfigurationPages();

    public abstract Class<FRAME> getFrameClass();

    public abstract FRAME discoverFocusedFrame();
}
