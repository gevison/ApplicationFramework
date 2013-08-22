package ge.framework.frame.multi.menu.window.item;

import ge.framework.application.multi.MultiApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameCheckboxMenuItem;
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
public class NextWindowMenuItem extends ApplicationFrameMenuItem
{
    private static Resources resources = Resources.getInstance( "ge.framework.frame.multi" );
    public NextWindowMenuItem( ApplicationFrame applicationFrame )
    {
        super(applicationFrame);
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( NextWindowMenuItem.class, "label" ) );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        MultiApplication multiApplication = ( MultiApplication ) applicationFrame.getApplication();

        multiApplication.nextFrame();
    }

    @Override
    public void update()
    {
        MultiApplication multiApplication = ( MultiApplication ) applicationFrame.getApplication();

        if ( multiApplication.getFrameCount() > 1 )
            setEnabled( true );
        else
            setEnabled( false );
    }
}
