package ge.framework.application.frame.multi.dialog.buttons;


import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.application.frame.multi.dialog.InitialDialog;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/01/13
 * Time: 10:42
 */
public class OpenFrameButton extends InitialDialogButton
{
    private InitialDialog initialDialog;

    public OpenFrameButton( InitialDialog initialDialog )
    {
        super();
        this.initialDialog = initialDialog;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiFrameApplication application = initialDialog.getApplication();
        if ( application.processOpen( null ) == true )
        {
            initialDialog.dispose();
        }
    }
}
