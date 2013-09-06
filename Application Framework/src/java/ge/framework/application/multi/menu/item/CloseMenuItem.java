package ge.framework.application.multi.menu.item;

import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 16:11
 */
public class CloseMenuItem extends ApplicationFrameMenuItem
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    public CloseMenuItem( ApplicationFrame applicationFrame )
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
        MultiFrameApplication application = ( MultiFrameApplication ) applicationFrame.getApplication();

        application.processClose(applicationFrame);
    }

    @Override
    public void update()
    {

    }
}
