package ge.framework.application.frame.multi.menu.item;

import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.application.frame.multi.dialog.RecentDialog;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.framework.frame.multi.MultiFrameApplicationFrame;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 16:19
 */
public class OtherRecentMenuItem extends ApplicationFrameMenuItem<MultiFrameApplicationFrame>
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.frame.multi" );

    public OtherRecentMenuItem( MultiFrameApplicationFrame applicationFrame )
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
        RecentDialog recentDialog = new RecentDialog( applicationFrame );

        if ( recentDialog.doModal() == true )
        {
            FrameInstanceDetailsObject frameInstanceDetailsObject = recentDialog.getFrameInstanceDetailsObject();

            MultiFrameApplication application = ( MultiFrameApplication ) applicationFrame.getApplication();

            application.openFrame( applicationFrame, frameInstanceDetailsObject, false );
        }
    }

    @Override
    public void update()
    {
        MultiFrameApplication application = ( MultiFrameApplication ) applicationFrame.getApplication();

        List<FrameInstanceDetailsObject> recentlyOpened = application.getRecentlyOpened();

        if ( recentlyOpened.size() < 15 )
        {
            setVisible( false );
        }
        else
        {
            setVisible( true );
        }
    }
}
