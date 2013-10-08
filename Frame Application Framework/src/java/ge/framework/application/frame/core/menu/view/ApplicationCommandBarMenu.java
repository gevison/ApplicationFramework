package ge.framework.application.frame.core.menu.view;

import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.command.ApplicationCommandBarComponent;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenu;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenuSeparator;
import ge.framework.application.frame.core.menu.view.item.ApplicationCommandBarMenuItem;
import ge.framework.application.frame.core.menu.view.item.OtherCommandBarsMenuItem;
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
public class ApplicationCommandBarMenu extends ApplicationFrameMenu
{
    private static final Resources resources = Resources.getInstance( "ge.framework.frame.core" );

    private Map<ApplicationCommandBarComponent, ApplicationCommandBarMenuItem> frameMenuItems = null;

    private OtherCommandBarsMenuItem otherMenuItem;

    public ApplicationCommandBarMenu( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenu()
    {
        setText( resources.getResourceString( ApplicationCommandBarMenu.class, "title" ) );

        frameMenuItems = new HashMap<ApplicationCommandBarComponent, ApplicationCommandBarMenuItem>();

        otherMenuItem = new OtherCommandBarsMenuItem( applicationFrame );
        otherMenuItem.initialise();
    }

    @Override
    protected void customizeMenu()
    {
        Map<ApplicationCommandBarComponent, ApplicationCommandBarMenuItem> currentMenuItems;
        currentMenuItems = new HashMap<ApplicationCommandBarComponent, ApplicationCommandBarMenuItem>();

        List<ApplicationCommandBarComponent> commandBars = applicationFrame.getCommandBars();

        for ( ApplicationCommandBarComponent commandBar : commandBars )
        {
            ApplicationCommandBarMenuItem menuItem;

            if ( frameMenuItems.containsKey( commandBar ) == true )
            {
                menuItem = frameMenuItems.get( commandBar );
            }
            else
            {
                menuItem = new ApplicationCommandBarMenuItem( applicationFrame, commandBar );
                menuItem.initialise();
            }

            currentMenuItems.put( commandBar, menuItem );
        }

        frameMenuItems.clear();
        frameMenuItems = currentMenuItems;

        List<ApplicationCommandBarComponent> onMenu;

        if ( commandBars.size() < 15 )
        {
            onMenu = commandBars;
        }
        else
        {
            onMenu = commandBars.subList( 0, 10 );
        }

        for ( ApplicationCommandBarComponent commandBar : onMenu )
        {
            addMenuComponent( frameMenuItems.get( commandBar ) );
        }

        if ( commandBars.size() != onMenu.size() )
        {
            addMenuComponent( new ApplicationFrameMenuSeparator() );
            addMenuComponent( otherMenuItem );
        }
    }

    @Override
    public void update()
    {
        List<ApplicationCommandBarComponent> commandBars = applicationFrame.getCommandBars();

        if ( ( commandBars == null ) || ( commandBars.isEmpty() == true ) )
        {
            setEnabled( false );
        }
        else
        {
            setEnabled( true );
        }
    }
}
