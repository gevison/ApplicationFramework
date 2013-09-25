package ge.framework.application.frame.multi.menu.item;

import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.framework.frame.multi.MultiFrameApplicationFrame;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 14/02/13
 * Time: 18:05
 */
public class OtherNewMenuItem extends ApplicationFrameMenuItem<MultiFrameApplicationFrame>
{
    protected static final Resources resources =
            Resources.getInstance( "ge.framework.application.resources" );

    public OtherNewMenuItem( MultiFrameApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( OtherNewMenuItem.class, "label" ) );
        setStatusBarText( resources.getResourceString( OtherNewMenuItem.class, "status" ) );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiFrameApplication application = applicationFrame.getApplication();
        application.processNew( applicationFrame );
    }

    @Override
    public void update()
    {

    }
}
