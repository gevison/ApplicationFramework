package ge.framework.application.multi.dialog.buttons;

import com.jidesoft.plaf.basic.ThemePainter;
import com.jidesoft.swing.JideButton;
import ge.utils.bundle.Resources;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/01/13
 * Time: 10:40
 */
public abstract class InitialDialogButton extends JideButton implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    public InitialDialogButton()
    {
        addActionListener( this );

        setText( resources.getResourceString( getClass(), "label" ) );
        setIcon( resources.getResourceIcon( getClass(), "icon" ) );

        Color selectionBackground = new Color( 175, 215, 255 );
        setMargin( new Insets( 5, 10, 5, 10 ) );
        setHorizontalAlignment( SwingConstants.LEADING );
        setIconTextGap( 30 );
        setBackgroundOfState( ThemePainter.STATE_ROLLOVER, selectionBackground );
        setBackgroundOfState( ThemePainter.STATE_PRESSED, selectionBackground );
    }
}
