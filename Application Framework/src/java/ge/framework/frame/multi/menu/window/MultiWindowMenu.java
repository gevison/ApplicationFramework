package ge.framework.frame.multi.menu.window;

import ge.framework.application.multi.MultiApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuSeparator;
import ge.framework.frame.core.menu.window.WindowMenu;
import ge.framework.frame.core.menu.window.item.ApplicationDocumentMenuItem;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.framework.frame.multi.menu.window.item.ApplicationWindowMenuItem;
import ge.framework.frame.multi.menu.window.item.NextWindowMenuItem;
import ge.framework.frame.multi.menu.window.item.PreviousWindowMenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 15:33
 */
public class MultiWindowMenu extends WindowMenu
{
    private NextWindowMenuItem nextWindowMenuItem;

    private PreviousWindowMenuItem previousWindowMenuItem;

    private Map<MultiApplicationFrame,ApplicationWindowMenuItem> applicationFrameMenuItems = null;

    public MultiWindowMenu( ApplicationFrame applicationFrame )
    {
        super(applicationFrame);
    }

    @Override
    protected void initialiseWindowMenu()
    {
        applicationFrameMenuItems = new HashMap<MultiApplicationFrame, ApplicationWindowMenuItem>(  );

        nextWindowMenuItem = new NextWindowMenuItem(applicationFrame);
        nextWindowMenuItem.initialise();

        previousWindowMenuItem = new PreviousWindowMenuItem( applicationFrame );
        previousWindowMenuItem.initialise();
    }

    @Override
    protected void customizeWindowMenu()
    {
        addMenuComponent( new ApplicationFrameMenuSeparator() );
        addMenuComponent( nextWindowMenuItem );
        addMenuComponent( previousWindowMenuItem );
        addMenuComponent( new ApplicationFrameMenuSeparator() );

        Map<MultiApplicationFrame,ApplicationWindowMenuItem> currentMenuItems;
        currentMenuItems = new HashMap<MultiApplicationFrame, ApplicationWindowMenuItem>(  );

        MultiApplication application = ( MultiApplication ) applicationFrame.getApplication();

        List<MultiApplicationFrame> frames = application.getFrames();

        for ( MultiApplicationFrame frame : frames )
        {
            ApplicationWindowMenuItem menuItem;
            if ( applicationFrameMenuItems.containsKey( frame ) == true )
            {
                menuItem = applicationFrameMenuItems.get( frame );
            }
            else
            {
                menuItem = new ApplicationWindowMenuItem(applicationFrame, frame );
                menuItem.initialise();
            }

            currentMenuItems.put( frame, menuItem );
        }

        applicationFrameMenuItems.clear();
        applicationFrameMenuItems = currentMenuItems;

        for ( ApplicationFrame frame : frames )
        {
            addMenuComponent( applicationFrameMenuItems.get( frame ) );
        }
    }
}
