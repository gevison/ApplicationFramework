package ge.framework.frame.core.menu.view;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenu;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuSeparator;
import ge.framework.frame.core.menu.view.item.ApplicationDockableFrameMenuItem;
import ge.framework.frame.core.menu.view.item.OtherDockableFramesMenuItem;
import ge.utils.bundle.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationDockableFrameMenu extends ApplicationFrameMenu
{
    private static final Resources resources = Resources.getInstance( "ge.framework.frame.core" );

    private Map<ApplicationDockableFrame, ApplicationDockableFrameMenuItem> frameMenuItems = null;

    private OtherDockableFramesMenuItem otherDockableFramesMenuItem;

    public ApplicationDockableFrameMenu( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenu()
    {
        setText( resources.getResourceString( ApplicationDockableFrameMenu.class, "title" ) );

        frameMenuItems = new HashMap<ApplicationDockableFrame, ApplicationDockableFrameMenuItem>();

        otherDockableFramesMenuItem = new OtherDockableFramesMenuItem( applicationFrame );
        otherDockableFramesMenuItem.initialise();
    }

    @Override
    protected void customizeMenu()
    {
        Map<ApplicationDockableFrame, ApplicationDockableFrameMenuItem> currentMenuItems;
        currentMenuItems = new HashMap<ApplicationDockableFrame, ApplicationDockableFrameMenuItem>();

        List<ApplicationDockableFrame> frames = applicationFrame.getDockingFrames();

        for ( ApplicationDockableFrame frame : frames )
        {
            ApplicationDockableFrameMenuItem menuItem;
            if ( frameMenuItems.containsKey( frame ) == true )
            {
                menuItem = frameMenuItems.get( frame );
            }
            else
            {
                menuItem = new ApplicationDockableFrameMenuItem( applicationFrame, frame );
                menuItem.initialise();
            }

            currentMenuItems.put( frame, menuItem );
        }

        frameMenuItems.clear();
        frameMenuItems = currentMenuItems;

        List<ApplicationDockableFrame> onMenu;

        if ( frames.size() < 15 )
        {
            onMenu = frames;
        }
        else
        {
            onMenu = frames.subList( 0, 10 );
        }

        for ( ApplicationDockableFrame frame : onMenu )
        {
            addMenuComponent( frameMenuItems.get( frame ) );
        }

        if ( frames.size() != onMenu.size() )
        {
            addMenuComponent( new ApplicationFrameMenuSeparator() );
            addMenuComponent( otherDockableFramesMenuItem );
        }
    }

    @Override
    public void update()
    {
        List<ApplicationDockableFrame> frames = applicationFrame.getDockingFrames();

        if ( ( frames == null ) || ( frames.isEmpty() == true ) )
        {
            setEnabled( false );
        }
        else
        {
            setEnabled( true );
        }
    }
}
