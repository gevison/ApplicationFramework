package ge.framework.frame.multi.menu.window.item;

import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 04/08/2013
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */
public class PreviousWindowMenuItem extends ApplicationFrameMenuItem
{
    private static Resources resources = Resources.getInstance( "ge.framework.frame.multi" );

    public PreviousWindowMenuItem( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( PreviousWindowMenuItem.class, "label" ) );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        MultiFrameApplication multiFrameApplication = ( MultiFrameApplication ) applicationFrame.getApplication();

        multiFrameApplication.previousFrame();
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
