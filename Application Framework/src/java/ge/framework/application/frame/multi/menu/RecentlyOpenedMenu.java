package ge.framework.application.frame.multi.menu;

import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.application.frame.multi.menu.item.ClearRecentMenuItem;
import ge.framework.application.frame.multi.menu.item.OpenRecentMenuItem;
import ge.framework.application.frame.multi.menu.item.OtherRecentMenuItem;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenu;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuSeparator;
import ge.framework.frame.multi.MultiFrameApplicationFrame;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 16:16
 */
public class RecentlyOpenedMenu extends ApplicationFrameMenu<MultiFrameApplicationFrame>
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.frame.multi" );

    private Map<FrameInstanceDetailsObject, OpenRecentMenuItem> recentMenuItems = null;

    private OtherRecentMenuItem otherRecentMenuItem;

    private ClearRecentMenuItem clearRecentMenuItem;

    public RecentlyOpenedMenu( MultiFrameApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenu()
    {
        setText( resources.getResourceString( RecentlyOpenedMenu.class, "label" ) );
        setStatusBarText( resources.getResourceString( RecentlyOpenedMenu.class, "status" ) );

        recentMenuItems = new HashMap<FrameInstanceDetailsObject, OpenRecentMenuItem>();

        otherRecentMenuItem = new OtherRecentMenuItem( applicationFrame );
        otherRecentMenuItem.initialise();

        clearRecentMenuItem = new ClearRecentMenuItem( applicationFrame );
        clearRecentMenuItem.initialise();
    }

    @Override
    protected void customizeMenu()
    {
        Map<FrameInstanceDetailsObject, OpenRecentMenuItem> currentMenuItems;
        currentMenuItems = new HashMap<FrameInstanceDetailsObject, OpenRecentMenuItem>();

        MultiFrameApplication application = applicationFrame.getApplication();

        List<FrameInstanceDetailsObject> detailsObjects = application.getRecentlyOpened();

        for ( FrameInstanceDetailsObject detailsObject : detailsObjects )
        {
            OpenRecentMenuItem menuItem;
            if ( recentMenuItems.containsKey( detailsObject ) == true )
            {
                menuItem = recentMenuItems.get( detailsObject );
            }
            else
            {
                menuItem = new OpenRecentMenuItem( applicationFrame, detailsObject );
                menuItem.initialise();
            }

            currentMenuItems.put( detailsObject, menuItem );
        }

        recentMenuItems.clear();
        recentMenuItems = currentMenuItems;

        List<FrameInstanceDetailsObject> onMenu;

        if ( detailsObjects.size() < 15 )
        {
            onMenu = detailsObjects;
        }
        else
        {
            onMenu = detailsObjects.subList( 0, 10 );
        }

        for ( FrameInstanceDetailsObject detailsObject : onMenu )
        {
            addMenuComponent( recentMenuItems.get( detailsObject ) );
        }

        addMenuComponent( new ApplicationFrameMenuSeparator() );
        addMenuComponent( otherRecentMenuItem );
        addMenuComponent( new ApplicationFrameMenuSeparator() );
        addMenuComponent( clearRecentMenuItem );
    }

    @Override
    public void update()
    {

    }
}
