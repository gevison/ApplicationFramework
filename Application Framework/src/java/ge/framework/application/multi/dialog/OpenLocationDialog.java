package ge.framework.application.multi.dialog;

import ge.framework.application.multi.objects.enums.OpenLocationEnum;
import ge.utils.bundle.Resources;
import ge.utils.message.MessageDialog;
import ge.utils.message.enums.MessageLevel;
import ge.utils.message.enums.MessageResult;
import ge.utils.message.enums.MessageType;

import java.awt.HeadlessException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/02/13
 * Time: 13:29
 */
public class OpenLocationDialog extends MessageDialog
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    private static final String alternateCheckBoxText =
            resources.getResourceString( OpenLocationDialog.class, "alternate", "checkbox" );

    public OpenLocationDialog() throws HeadlessException
    {
        super( resources.getResourceString( OpenLocationDialog.class, "message" ),
               resources.getResourceString( OpenLocationDialog.class, "title" ),
               MessageType.YES_NO_CANCEL,
               MessageLevel.QUESTION,
               true, alternateCheckBoxText, getAlternateButtonText() );
    }

    private static Map<MessageResult, String> getAlternateButtonText()
    {
        Map<MessageResult, String> alternateButtonText = new HashMap<MessageResult, String>();

        alternateButtonText
                .put( MessageResult.YES, resources.getResourceString( OpenLocationDialog.class, "alternate", "yes" ) );
        alternateButtonText
                .put( MessageResult.NO, resources.getResourceString( OpenLocationDialog.class, "alternate", "no" ) );

        return alternateButtonText;
    }

    public OpenLocationEnum showMessage()
    {
        MessageResult messageResult = doModal();

        if ( messageResult == MessageResult.YES )
        {
            return OpenLocationEnum.EXISTING;
        }
        else if ( messageResult == MessageResult.NO )
        {
            return OpenLocationEnum.NEW;
        }
        else
        {
            return null;
        }
    }
}
