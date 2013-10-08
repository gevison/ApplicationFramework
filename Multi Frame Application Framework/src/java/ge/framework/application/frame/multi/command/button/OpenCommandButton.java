package ge.framework.application.frame.multi.command.button;

import com.jidesoft.swing.JideMenu;
import com.jidesoft.swing.JideSplitButton;
import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.application.frame.multi.MultiFrameApplicationFrame;
import ge.framework.application.frame.multi.menu.item.ClearRecentMenuItem;
import ge.framework.application.frame.multi.menu.item.OpenMenuItem;
import ge.framework.application.frame.multi.menu.item.OpenRecentMenuItem;
import ge.framework.application.frame.multi.menu.item.OtherRecentMenuItem;
import ge.framework.application.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;

import javax.swing.JPopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/02/13
 * Time: 13:07
 */
public class OpenCommandButton extends JideSplitButton implements ActionListener, JideMenu.PopupMenuCustomizer
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.frame.multi" );

    private OtherRecentMenuItem otherRecentMenuItem = null;

    private ClearRecentMenuItem clearRecentMenuItem = null;

    private OpenMenuItem openMenuItem = null;

    private MultiFrameApplicationFrame applicationFrame;

    public OpenCommandButton( MultiFrameApplicationFrame applicationFrame )
    {
        this.applicationFrame = applicationFrame;
        setPopupMenuCustomizer( this );

        initialiseMenu();
    }

    private void initialiseMenu()
    {
        setIcon( resources.getResourceIcon( OpenCommandButton.class, "icon" ) );

        setFocusable( false );
        setOpaque( false );

        addActionListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiFrameApplication application = applicationFrame.getApplication();
        application.processOpen( applicationFrame );
    }

    @Override
    public void customize( JPopupMenu jPopupMenu )
    {
        if ( jPopupMenu == getPopupMenu() )
        {
            removeAll();

            MultiFrameApplication application = applicationFrame.getApplication();

            List<FrameInstanceDetailsObject> frameInstanceDetailsObjects =
                    application.getRecentlyOpened();

            if ( frameInstanceDetailsObjects.size() > 15 )
            {
                for ( int i = 0; i < 10; i++ )
                {
                    OpenRecentMenuItem menuItem =
                            new OpenRecentMenuItem( applicationFrame, frameInstanceDetailsObjects.get( i ) );

                    menuItem.initialise();

                    add( menuItem );
                }

                addSeparator();

                if ( otherRecentMenuItem == null )
                {
                    otherRecentMenuItem = new OtherRecentMenuItem( applicationFrame );
                    otherRecentMenuItem.initialise();
                }

                add( otherRecentMenuItem );
            }
            else
            {
                for ( FrameInstanceDetailsObject frameInstanceDetailsObject : frameInstanceDetailsObjects )
                {
                    OpenRecentMenuItem menuItem =
                            new OpenRecentMenuItem( applicationFrame, frameInstanceDetailsObject );
                    menuItem.initialise();
                    add( menuItem );
                }
            }

            if ( frameInstanceDetailsObjects.isEmpty() == false )
            {
                addSeparator();

                if ( clearRecentMenuItem == null )
                {
                    clearRecentMenuItem = new ClearRecentMenuItem( applicationFrame );
                    clearRecentMenuItem.initialise();
                }

                add( clearRecentMenuItem );
            }
            else
            {
                if ( openMenuItem == null )
                {
                    openMenuItem = new OpenMenuItem( applicationFrame );
                    openMenuItem.initialise();
                }

                add( openMenuItem );
            }
        }
    }
}
