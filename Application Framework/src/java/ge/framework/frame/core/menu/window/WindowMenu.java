package ge.framework.frame.core.menu.window;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.item.ToggleStatusBarMenuItem;
import ge.framework.frame.core.menu.item.ToggleToolButtonsMenuItem;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenu;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuComponent;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuSeparator;
import ge.framework.frame.core.menu.view.ApplicationCommandBarMenu;
import ge.framework.frame.core.menu.view.ApplicationDockableFrameMenu;
import ge.framework.frame.core.menu.window.item.CloseAllDocumentMenuItem;
import ge.framework.frame.core.menu.window.item.CloseDocumentMenuItem;
import ge.framework.frame.core.menu.window.item.CloseOthersDocumentMenuItem;
import ge.framework.frame.core.menu.window.item.NextDocumentMenuItem;
import ge.framework.frame.core.menu.window.item.PreviousDocumentMenuItem;
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
public abstract class WindowMenu extends ApplicationFrameMenu
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core" );

    private ApplicationDocumentMenu applicationDocumentMenu;

    private NextDocumentMenuItem nextDocumentMenuItem;

    private PreviousDocumentMenuItem previousDocumentMenuItem;

    private CloseDocumentMenuItem closeDocumentMenuItem;

    private CloseOthersDocumentMenuItem closeOthersDocumentMenuItem;

    private CloseAllDocumentMenuItem closeAllDocumentMenuItem;

    private List<ApplicationFrameMenuComponent> additionalDocumentItems = new ArrayList<ApplicationFrameMenuComponent>(  );

    public WindowMenu( ApplicationFrame applicationFrame )
    {
        super( applicationFrame, false );
    }

    @Override
    protected final void initialiseMenu()
    {
        setText( resources.getResourceString( WindowMenu.class, "label" ) );
        setStatusBarText( resources.getResourceString( WindowMenu.class, "status" ) );
        setMnemonic( resources.getResourceCharacter( WindowMenu.class, "mnemonic" ) );

        applicationDocumentMenu = new ApplicationDocumentMenu( applicationFrame );
        applicationDocumentMenu.initialise();

        nextDocumentMenuItem = new NextDocumentMenuItem( applicationFrame );
        nextDocumentMenuItem.initialise();

        previousDocumentMenuItem = new PreviousDocumentMenuItem( applicationFrame );
        previousDocumentMenuItem.initialise();

        closeDocumentMenuItem = new CloseDocumentMenuItem( applicationFrame );
        closeDocumentMenuItem.initialise();

        closeOthersDocumentMenuItem = new CloseOthersDocumentMenuItem( applicationFrame );
        closeOthersDocumentMenuItem.initialise();

        closeAllDocumentMenuItem = new CloseAllDocumentMenuItem( applicationFrame );
        closeAllDocumentMenuItem.initialise();

        initialiseWindowMenu();
    }

    protected abstract void initialiseWindowMenu();

    @Override
    protected final void customizeMenu()
    {
        addMenuComponent( applicationDocumentMenu );
        addMenuComponent( new ApplicationFrameMenuSeparator() );
        addMenuComponent( nextDocumentMenuItem );
        addMenuComponent( previousDocumentMenuItem );
        addMenuComponent( new ApplicationFrameMenuSeparator() );
        addMenuComponent( closeDocumentMenuItem );
        addMenuComponent( closeOthersDocumentMenuItem );
        addMenuComponent( closeAllDocumentMenuItem );
        addMenuComponent( new ApplicationFrameMenuSeparator() );

        if ( additionalDocumentItems.isEmpty() == false )
        {
            for ( ApplicationFrameMenuComponent additionalDocumentItem : additionalDocumentItems )
            {
                addMenuComponent( additionalDocumentItem );
            }

            addMenuComponent( new ApplicationFrameMenuSeparator() );
        }

        customizeWindowMenu();
    }

    protected abstract void customizeWindowMenu();

    @Override
    public final void update()
    {
    }

    public void addAdditionalDocumentMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        additionalDocumentItems.add( applicationComponent );
    }

    public void removeAdditionalDocumentMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        additionalDocumentItems.remove( applicationComponent );
    }
}
