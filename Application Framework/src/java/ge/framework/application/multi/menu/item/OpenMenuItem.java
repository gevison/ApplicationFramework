package ge.framework.application.multi.menu.item;

import ge.framework.application.multi.MultiApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
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
public class OpenMenuItem extends ApplicationFrameMenuItem
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    public OpenMenuItem( ApplicationFrame applicationFrame )
    {
        super(applicationFrame);
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
        MultiApplication application = ( MultiApplication ) applicationFrame.getApplication();

        application.processOpen( applicationFrame );
    }

    @Override
    public void update()
    {

    }
}
