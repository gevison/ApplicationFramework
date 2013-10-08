package ge.framework.application.frame.core.menu.window;

import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.DocumentWorkspaceApplicationFrame;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenu;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenuComponent;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenuSeparator;
import ge.framework.application.frame.core.menu.window.item.CloseAllDocumentMenuItem;
import ge.framework.application.frame.core.menu.window.item.CloseDocumentMenuItem;
import ge.framework.application.frame.core.menu.window.item.CloseOthersDocumentMenuItem;
import ge.framework.application.frame.core.menu.window.item.NextDocumentMenuItem;
import ge.framework.application.frame.core.menu.window.item.PreviousDocumentMenuItem;
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
public class DocumentWorkspaceWindowMenu extends ApplicationFrameMenu
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core" );

    private ApplicationDocumentMenu applicationDocumentMenu;

    private NextDocumentMenuItem nextDocumentMenuItem;

    private PreviousDocumentMenuItem previousDocumentMenuItem;

    private CloseDocumentMenuItem closeDocumentMenuItem;

    private CloseOthersDocumentMenuItem closeOthersDocumentMenuItem;

    private CloseAllDocumentMenuItem closeAllDocumentMenuItem;

    private List<ApplicationFrameMenuComponent> additionalDocumentItems =
            new ArrayList<ApplicationFrameMenuComponent>();

    public DocumentWorkspaceWindowMenu( DocumentWorkspaceApplicationFrame applicationFrame )
    {
        super( ( ApplicationFrame ) applicationFrame, false );
    }

    @Override
    protected final void initialiseMenu()
    {
        setText( resources.getResourceString( DocumentWorkspaceWindowMenu.class, "label" ) );
        setStatusBarText( resources.getResourceString( DocumentWorkspaceWindowMenu.class, "status" ) );
        setMnemonic( resources.getResourceCharacter( DocumentWorkspaceWindowMenu.class, "mnemonic" ) );

        DocumentWorkspaceApplicationFrame documentWorkspaceApplicationFrame =
                ( DocumentWorkspaceApplicationFrame ) applicationFrame;

        applicationDocumentMenu = new ApplicationDocumentMenu( documentWorkspaceApplicationFrame );
        applicationDocumentMenu.initialise();

        nextDocumentMenuItem = new NextDocumentMenuItem( documentWorkspaceApplicationFrame );
        nextDocumentMenuItem.initialise();

        previousDocumentMenuItem = new PreviousDocumentMenuItem( documentWorkspaceApplicationFrame );
        previousDocumentMenuItem.initialise();

        closeDocumentMenuItem = new CloseDocumentMenuItem( documentWorkspaceApplicationFrame );
        closeDocumentMenuItem.initialise();

        closeOthersDocumentMenuItem = new CloseOthersDocumentMenuItem( documentWorkspaceApplicationFrame );
        closeOthersDocumentMenuItem.initialise();

        closeAllDocumentMenuItem = new CloseAllDocumentMenuItem( documentWorkspaceApplicationFrame );
        closeAllDocumentMenuItem.initialise();

        initialiseWindowMenu();
    }

    protected void initialiseWindowMenu()
    {
    }

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

    protected void customizeWindowMenu()
    {
    }

    @Override
    public void update()
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
