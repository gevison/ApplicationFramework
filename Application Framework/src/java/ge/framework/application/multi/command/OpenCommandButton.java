package ge.framework.application.multi.command;

import com.jidesoft.swing.JideMenu;
import com.jidesoft.swing.JideSplitButton;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.application.multi.menu.item.ClearRecentMenuItem;
import ge.framework.application.multi.menu.item.OpenMenuItem;
import ge.framework.application.multi.menu.item.OpenRecentMenuItem;
import ge.framework.application.multi.menu.item.OtherRecentMenuItem;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
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
            Resources.getInstance( "ge.framework.application.multi" );

    private OtherRecentMenuItem otherRecentMenuItem = null;

    private ClearRecentMenuItem clearRecentMenuItem = null;

    private OpenMenuItem openMenuItem = null;

    private ApplicationFrame applicationFrame;

    public OpenCommandButton( ApplicationFrame applicationFrame )
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
        MultiFrameApplication application = ( MultiFrameApplication ) applicationFrame.getApplication();
        application.processOpen( applicationFrame );
    }

    @Override
    public void customize( JPopupMenu jPopupMenu )
    {
        if ( jPopupMenu == getPopupMenu() )
        {
            removeAll();

            MultiFrameApplication application = ( MultiFrameApplication ) applicationFrame.getApplication();

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
