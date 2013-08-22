package ge.framework.application.multi.dialog;

import ge.framework.frame.core.ApplicationFrame;
import ge.utils.bundle.Resources;
import ge.utils.message.MessageDialog;
import ge.utils.message.enums.MessageLevel;
import ge.utils.message.enums.MessageResult;
import ge.utils.message.enums.MessageType;
import ge.utils.text.StringArgumentMessageFormat;

import java.awt.HeadlessException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/02/13
 * Time: 13:29
 */
public class MissingLocationDialog extends MessageDialog
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    public MissingLocationDialog( ApplicationFrame applicationFrame, String name ) throws HeadlessException
    {
        super( applicationFrame, formatMessage( name ),
               resources.getResourceString( MissingLocationDialog.class, "title" ),
               MessageType.YES_NO,
               MessageLevel.WARNING );
    }

    private static String formatMessage( String name )
    {
        String message = resources.getResourceString( MissingLocationDialog.class, "message" );

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "name", name );

        return StringArgumentMessageFormat.format( message, arguments );
    }

    public boolean showMessage()
    {
        MessageResult messageResult = doModal();

        if ( messageResult == MessageResult.YES )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
