package ge.framework.application.frame.multi.menu.item;

import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.application.frame.multi.MultiFrameApplicationFrame;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 16:11
 */
public class CloseMenuItem extends ApplicationFrameMenuItem<MultiFrameApplicationFrame>
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.frame.multi" );

    public CloseMenuItem( MultiFrameApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( this.getClass(), "label" ) );
        setStatusBarText( resources.getResourceString( this.getClass(), "status" ) );
        setMnemonic( resources.getResourceCharacter( this.getClass(), "mnemonic" ) );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiFrameApplication application = applicationFrame.getApplication();

        application.processClose( applicationFrame );
    }

    @Override
    public void update()
    {

    }
}
