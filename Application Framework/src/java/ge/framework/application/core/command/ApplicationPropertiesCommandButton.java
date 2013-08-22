package ge.framework.application.core.command;

import com.jidesoft.swing.JideButton;
import ge.framework.application.core.Application;
import ge.framework.frame.core.ApplicationFrame;
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
            Resources.getInstance( "ge.framework.application.core" );

    private ApplicationFrame applicationFrame;

    public ApplicationPropertiesCommandButton(ApplicationFrame applicationFrame)
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
        Application application = applicationFrame.getApplication();
        application.processApplicationProperties();
    }
}
