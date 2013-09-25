package ge.framework.frame.multi.menu.window;

import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenu;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuSeparator;
import ge.framework.frame.multi.MultiFrameApplicationFrame;
import ge.framework.frame.multi.menu.window.item.ApplicationWindowMenuItem;
import ge.framework.frame.multi.menu.window.item.NextWindowMenuItem;
import ge.framework.frame.multi.menu.window.item.PreviousWindowMenuItem;
import ge.utils.bundle.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 15:33
 */
public class MultiFrameWindowMenu extends ApplicationFrameMenu
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.multi" );

    private NextWindowMenuItem nextWindowMenuItem;

    private PreviousWindowMenuItem previousWindowMenuItem;

    private Map<MultiFrameApplicationFrame, ApplicationWindowMenuItem> applicationFrameMenuItems = null;

    public MultiFrameWindowMenu( MultiFrameApplicationFrame applicationFrame )
    {
        super( applicationFrame, false );
    }

    @Override
    protected void initialiseMenu()
    {
        setText( resources.getResourceString( MultiFrameWindowMenu.class, "label" ) );
        setStatusBarText( resources.getResourceString( MultiFrameWindowMenu.class, "status" ) );
        setMnemonic( resources.getResourceCharacter( MultiFrameWindowMenu.class, "mnemonic" ) );

        applicationFrameMenuItems = new HashMap<MultiFrameApplicationFrame, ApplicationWindowMenuItem>();

        nextWindowMenuItem = new NextWindowMenuItem( applicationFrame );
        nextWindowMenuItem.initialise();

        previousWindowMenuItem = new PreviousWindowMenuItem( applicationFrame );
        previousWindowMenuItem.initialise();
    }

    @Override
    protected void customizeMenu()
    {
        addMenuComponent( new ApplicationFrameMenuSeparator() );
        addMenuComponent( nextWindowMenuItem );
        addMenuComponent( previousWindowMenuItem );
        addMenuComponent( new ApplicationFrameMenuSeparator() );

        Map<MultiFrameApplicationFrame, ApplicationWindowMenuItem> currentMenuItems;
        currentMenuItems = new HashMap<MultiFrameApplicationFrame, ApplicationWindowMenuItem>();

        MultiFrameApplication application = ( MultiFrameApplication ) applicationFrame.getApplication();

        List<MultiFrameApplicationFrame> frames = application.getFrames();

        for ( MultiFrameApplicationFrame frame : frames )
        {
            ApplicationWindowMenuItem menuItem;
            if ( applicationFrameMenuItems.containsKey( frame ) == true )
            {
                menuItem = applicationFrameMenuItems.get( frame );
            }
            else
            {
                menuItem = new ApplicationWindowMenuItem( applicationFrame, frame );
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

    @Override
    public void update()
    {

    }
}
