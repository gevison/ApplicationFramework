package ge.framework.application.frame.multi.command.button;

import com.jidesoft.swing.JideButton;
import ge.framework.application.frame.multi.MultiFrameApplicationFrame;
import ge.framework.application.frame.multi.dialog.FramePropertiesDialog;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/02/13
 * Time: 14:36
 */
public class FramePropertiesCommandButton extends JideButton implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.frame.multi" );

    private final MultiFrameApplicationFrame applicationFrame;

    public FramePropertiesCommandButton( MultiFrameApplicationFrame applicationFrame )
    {
        this.applicationFrame = applicationFrame;

        addActionListener( this );

        setIcon( resources.getResourceIcon( FramePropertiesCommandButton.class, "icon" ) );

        setVisible( applicationFrame.shouldCreateFrameConfigurationMenu() );

        setFocusable( false );
        setOpaque( false );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        FramePropertiesDialog framePropertiesDialog = new FramePropertiesDialog( applicationFrame );

        framePropertiesDialog.doModal();

        applicationFrame.saveFrameConfiguration();
    }
}
