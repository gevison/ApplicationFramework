package ge.framework.frame.core.menu.view;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.item.ToggleStatusBarMenuItem;
import ge.framework.frame.core.menu.item.ToggleToolButtonsMenuItem;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenu;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuComponent;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuSeparator;
import ge.utils.bundle.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 12:49
 * To change this template use File | Settings | File Templates.
 */
public class ViewMenu extends ApplicationFrameMenu
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core" );

    private List<ApplicationFrameMenuComponent> additionalMenuItems =
            new ArrayList<ApplicationFrameMenuComponent>();

    private ToggleStatusBarMenuItem toggleStatusBarMenuItem;

    private ToggleToolButtonsMenuItem toggleToolButtonsMenuItem;

    private ApplicationDockableFrameMenu applicationDockableFrameMenu;

    private ApplicationCommandBarMenu applicationCommandBarMenu;

    public ViewMenu( ApplicationFrame applicationFrame )
    {
        super( applicationFrame, false );
    }

    @Override
    protected void initialiseMenu()
    {
        setText( resources.getResourceString( ViewMenu.class, "label" ) );
        setStatusBarText( resources.getResourceString( ViewMenu.class, "status" ) );
        setMnemonic( resources.getResourceCharacter( ViewMenu.class, "mnemonic" ) );

        applicationDockableFrameMenu = new ApplicationDockableFrameMenu( applicationFrame );
        applicationDockableFrameMenu.initialise();

        applicationCommandBarMenu = new ApplicationCommandBarMenu( applicationFrame );
        applicationCommandBarMenu.initialise();

        toggleStatusBarMenuItem = new ToggleStatusBarMenuItem( applicationFrame );
        toggleStatusBarMenuItem.initialise();

        toggleToolButtonsMenuItem = new ToggleToolButtonsMenuItem( applicationFrame );
        toggleToolButtonsMenuItem.initialise();
    }

    @Override
    protected void customizeMenu()
    {
        addMenuComponent( applicationDockableFrameMenu );
        addMenuComponent( applicationCommandBarMenu );
        addMenuComponent( new ApplicationFrameMenuSeparator() );
        addMenuComponent( toggleStatusBarMenuItem );
        addMenuComponent( toggleToolButtonsMenuItem );

        if ( additionalMenuItems.isEmpty() == false )
        {
            addMenuComponent( new ApplicationFrameMenuSeparator() );

            for ( ApplicationFrameMenuComponent additionalMenuItem : additionalMenuItems )
            {
                addMenuComponent( additionalMenuItem );
            }
        }
    }

    @Override
    public void update()
    {
    }

    public void addAdditionalMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        additionalMenuItems.add( applicationComponent );
    }

    public void removeAdditionalMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        additionalMenuItems.remove( applicationComponent );
    }
}
