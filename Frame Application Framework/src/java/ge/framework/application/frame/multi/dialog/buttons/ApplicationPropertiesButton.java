package ge.framework.application.frame.multi.dialog.buttons;


import ge.framework.application.frame.core.FrameApplication;
import ge.framework.application.frame.multi.dialog.InitialDialog;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/02/13
 * Time: 17:58
 */
public class ApplicationPropertiesButton extends InitialDialogButton
{
    private final InitialDialog initialDialog;

    public ApplicationPropertiesButton( InitialDialog initialDialog )
    {
        super();
        this.initialDialog = initialDialog;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        FrameApplication application = initialDialog.getApplication();
        application.processApplicationProperties();
    }

}
