package ge.framework.application.core.dialog;

import com.jidesoft.dialog.StandardDialog;
import ge.framework.application.core.Application;
import ge.framework.application.multi.dialog.utils.RootPaneDisposeActionListener;
import ge.framework.frame.core.ApplicationFrame;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 07/03/13
 * Time: 18:09
 */
public abstract class ApplicationStandardDialog extends StandardDialog
{
    protected Application application;

    protected ApplicationStandardDialog( ApplicationFrame applicationFrame, Application application )
            throws HeadlessException
    {
        super( applicationFrame );
        this.application = application;

        initialiseApplicationStandardDialog();
    }

    private void initialiseApplicationStandardDialog()
    {
        setIconImage( application.getSmallImage() );
        setTitle( application.getName() );

        rootPane.registerKeyboardAction( new RootPaneDisposeActionListener(),
                                         KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ),
                                         JComponent.WHEN_IN_FOCUSED_WINDOW );

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setLocationRelativeTo( getOwner() );
    }
}
