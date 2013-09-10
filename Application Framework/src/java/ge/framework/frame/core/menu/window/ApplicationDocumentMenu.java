package ge.framework.frame.core.menu.window;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.DocumentWorkspaceApplicationFrame;
import ge.framework.frame.core.document.ApplicationDocumentComponent;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenu;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuSeparator;
import ge.framework.frame.core.menu.window.item.ApplicationDocumentMenuItem;
import ge.framework.frame.core.menu.window.item.OtherDocumentMenuItem;
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
public class ApplicationDocumentMenu extends ApplicationFrameMenu
{
    private static final Resources resources = Resources.getInstance( "ge.framework.frame.core" );

    private Map<ApplicationDocumentComponent,ApplicationDocumentMenuItem> documentMenuItems = null;

    private OtherDocumentMenuItem otherDocumentMenuItem;

    public ApplicationDocumentMenu( DocumentWorkspaceApplicationFrame applicationFrame )
    {
        super( ( ApplicationFrame ) applicationFrame );
    }

    @Override
    protected void initialiseMenu()
    {
        setText( resources.getResourceString( ApplicationDocumentMenu.class, "title" ) );

        documentMenuItems = new HashMap<ApplicationDocumentComponent, ApplicationDocumentMenuItem>(  );

        otherDocumentMenuItem = new OtherDocumentMenuItem( ( DocumentWorkspaceApplicationFrame ) applicationFrame );
        otherDocumentMenuItem.initialise();
    }

    @Override
    protected void customizeMenu()
    {
        Map<ApplicationDocumentComponent,ApplicationDocumentMenuItem> currentMenuItems;
        currentMenuItems = new HashMap<ApplicationDocumentComponent, ApplicationDocumentMenuItem>(  );

        DocumentWorkspaceApplicationFrame documentWorkspaceApplicationFrame =
                ( DocumentWorkspaceApplicationFrame ) applicationFrame;

        List<ApplicationDocumentComponent> documents = documentWorkspaceApplicationFrame.getDocumentComponents();

        for ( ApplicationDocumentComponent document : documents )
        {
            ApplicationDocumentMenuItem menuItem;
            if ( documentMenuItems.containsKey( document ) == true )
            {
                menuItem = documentMenuItems.get( document );
            }
            else
            {
                menuItem = new ApplicationDocumentMenuItem(applicationFrame, document );
                menuItem.initialise();
            }

            currentMenuItems.put( document, menuItem );
        }

        documentMenuItems.clear();
        documentMenuItems = currentMenuItems;

        List<ApplicationDocumentComponent> onMenu;

        if ( documents.size() < 15 )
        {
            onMenu = documents;
        }
        else
        {
            onMenu = documents.subList( 0, 10 );
        }

        for ( ApplicationDocumentComponent doccument : onMenu )
        {
            addMenuComponent( documentMenuItems.get( doccument ) );
        }

        if ( documents.size() != onMenu.size() )
        {
            addMenuComponent( new ApplicationFrameMenuSeparator());
            addMenuComponent( otherDocumentMenuItem );
        }
    }

    @Override
    public void update()
    {

        DocumentWorkspaceApplicationFrame documentWorkspaceApplicationFrame =
                ( DocumentWorkspaceApplicationFrame ) applicationFrame;

        List<ApplicationDocumentComponent> documents = documentWorkspaceApplicationFrame.getDocumentComponents();

        if (( documents == null ) || (documents.isEmpty() == true ))
        {
            setEnabled( false );
        }
        else
        {
            setEnabled( true );
        }
    }
}
