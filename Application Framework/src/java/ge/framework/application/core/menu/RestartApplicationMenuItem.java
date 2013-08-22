package ge.framework.application.core.menu;

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
public class RestartApplicationMenuItem extends ApplicationFrameMenuItem
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.core" );

    public RestartApplicationMenuItem( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( RestartApplicationMenuItem.class, "label" ) );
        setStatusBarText( resources.getResourceString( RestartApplicationMenuItem.class, "status" ) );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Application application = applicationFrame.getApplication();
        application.processRestart();
    }

    @Override
    public void update()
    {
    }
}
