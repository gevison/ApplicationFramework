package ge.framework.application.frame.multi.menu.item;

import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.application.frame.multi.MultiFrameApplicationFrame;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.utils.bundle.Resources;

import javax.swing.KeyStroke;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 16:03
 */
public class OpenMenuItem extends ApplicationFrameMenuItem<MultiFrameApplicationFrame>
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.frame.multi" );

    public OpenMenuItem( MultiFrameApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( this.getClass(), "label" ) );
        setStatusBarText( resources.getResourceString( this.getClass(), "status" ) );
        setMnemonic( resources.getResourceCharacter( this.getClass(), "mnemonic" ) );
        setIcon( resources.getResourceIcon( this.getClass(), "icon" ) );

        setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_O, Event.CTRL_MASK ) );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiFrameApplication application = applicationFrame.getApplication();

        application.processOpen( applicationFrame );
    }

    @Override
    public void update()
    {

    }
}
