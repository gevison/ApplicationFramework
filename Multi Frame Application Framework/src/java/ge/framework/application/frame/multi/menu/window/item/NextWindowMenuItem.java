package ge.framework.application.frame.multi.menu.window.item;

import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 04/08/2013
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */
public class NextWindowMenuItem extends ApplicationFrameMenuItem
{
    private static Resources resources = Resources.getInstance( "ge.framework.application.frame.multi" );

    public NextWindowMenuItem( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( NextWindowMenuItem.class, "label" ) );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        MultiFrameApplication multiFrameApplication = ( MultiFrameApplication ) applicationFrame.getApplication();

        multiFrameApplication.nextFrame();
    }

    @Override
    public void update()
    {
        MultiFrameApplication multiFrameApplication = ( MultiFrameApplication ) applicationFrame.getApplication();

        if ( multiFrameApplication.getFrameCount() > 1 )
        {
            setEnabled( true );
        }
        else
        {
            setEnabled( false );
        }
    }
}
