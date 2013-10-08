package ge.framework.application.frame.core.command;

import com.jidesoft.swing.JideButton;
import ge.framework.application.frame.core.FrameApplication;
import ge.framework.application.frame.core.ApplicationFrame;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/02/13
 * Time: 14:36
 */
public class ApplicationPropertiesCommandButton extends JideButton implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.frame.core" );

    private ApplicationFrame applicationFrame;

    public ApplicationPropertiesCommandButton( ApplicationFrame applicationFrame )
    {
        this.applicationFrame = applicationFrame;
        addActionListener( this );

        setIcon( resources.getResourceIcon( ApplicationPropertiesCommandButton.class, "icon" ) );
        setToolTipText( resources.getResourceString( ApplicationPropertiesCommandButton.class, "tooltip" ) );

        setFocusable( false );
        setOpaque( false );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        FrameApplication application = applicationFrame.getApplication();
        application.processApplicationProperties();
    }
}
