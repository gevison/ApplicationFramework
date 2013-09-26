package ge.framework.application.frame.core.menu;

import ge.framework.application.core.Application;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.utils.bundle.Resources;
import ge.utils.os.OS;

import javax.swing.KeyStroke;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/01/13
 * Time: 11:23
 */
public class ExitApplicationMenuItem extends ApplicationFrameMenuItem
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.core" );

    public ExitApplicationMenuItem( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( this.getClass(), "label" ) );
        setStatusBarText( resources.getResourceString( this.getClass(), "status" ) );
        setMnemonic( resources.getResourceCharacter( this.getClass(), "mnemonic" ) );

        if ( OS.isMac() == true )
        {
            setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Q, Event.META_MASK ) );
        }
        else
        {
            setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_F4,
                                                    Event.ALT_MASK ) );
        }
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Application application = applicationFrame.getApplication();
        application.processExit();
    }

    @Override
    public void update()
    {
    }
}
