package ge.framework.application.multi.menu.item;

import ge.framework.application.multi.MultiApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 16:24
 */
public class ClearRecentMenuItem extends ApplicationFrameMenuItem
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    public ClearRecentMenuItem( ApplicationFrame applicationFrame )
    {
        super(applicationFrame);
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
        MultiApplication application = ( MultiApplication ) applicationFrame.getApplication();
        application.clearRecent();
    }

    @Override
    public void update()
    {
    }
}