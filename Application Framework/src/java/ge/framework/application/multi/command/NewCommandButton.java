package ge.framework.application.multi.command;

import com.jidesoft.swing.JideButton;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/02/13
 * Time: 13:07
 */
public class NewCommandButton extends JideButton implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    private ApplicationFrame applicationFrame;

    public NewCommandButton( ApplicationFrame applicationFrame )
    {
        this.applicationFrame = applicationFrame;
        initialiseMenu();
    }

    private void initialiseMenu()
    {
        setIcon( resources.getResourceIcon( NewCommandButton.class, "icon" ) );

        setFocusable( false );
        setOpaque( false );

        addActionListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiFrameApplication application = ( MultiFrameApplication ) applicationFrame.getApplication();
        application.processNew( applicationFrame );
    }
}
