package ge.framework.application.frame.multi.menu.file;

import ge.framework.application.frame.multi.MultiFrameApplicationFrame;
import ge.framework.application.frame.multi.menu.RecentlyOpenedMenu;
import ge.framework.application.frame.multi.menu.file.item.FramePropertiesMenuItem;
import ge.framework.application.frame.multi.menu.item.CloseMenuItem;
import ge.framework.application.frame.multi.menu.item.NewMenuItem;
import ge.framework.application.frame.multi.menu.item.OpenMenuItem;
import ge.framework.application.frame.core.menu.file.FileMenu;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenuComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 15:31
 */
public class MultiFileMenu extends FileMenu<MultiFrameApplicationFrame>
{
    private NewMenuItem newItem;

    private List<ApplicationFrameMenuComponent> additionalNewItems = new ArrayList<ApplicationFrameMenuComponent>();

    private OpenMenuItem openMenuItem;

    private CloseMenuItem closeMenuItem;

    private RecentlyOpenedMenu recentlyOpenedMenu;

    private FramePropertiesMenuItem framePropertiesMenuItem;

    private List<ApplicationFrameMenuComponent> postPropertiesMenuItems =
            new ArrayList<ApplicationFrameMenuComponent>();

    public MultiFileMenu( MultiFrameApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseFileMenu()
    {
        newItem = new NewMenuItem( applicationFrame, true );
        newItem.initialise();

        openMenuItem = new OpenMenuItem( applicationFrame );
        openMenuItem.initialise();

        recentlyOpenedMenu = new RecentlyOpenedMenu( applicationFrame );
        recentlyOpenedMenu.initialise();

        closeMenuItem = new CloseMenuItem( applicationFrame );
        closeMenuItem.initialise();

        if ( applicationFrame.shouldCreateFrameConfigurationMenu() == true )
        {
            framePropertiesMenuItem = new FramePropertiesMenuItem( applicationFrame );
            framePropertiesMenuItem.initialise();
        }
    }

    @Override
    protected void customizePrePropertiesMenuItems()
    {
        addMenuComponent( newItem );

        if ( additionalNewItems.isEmpty() == false )
        {
            for ( ApplicationFrameMenuComponent additionalNewItem : additionalNewItems )
            {
                addMenuComponent( additionalNewItem );
            }
        }

        addMenuComponent( openMenuItem );
        addMenuComponent( recentlyOpenedMenu );
        addMenuComponent( closeMenuItem );
    }

    @Override
    protected void customizeOtherPropertiesMenuItems()
    {
        if ( framePropertiesMenuItem != null )
        {
            addMenuComponent( framePropertiesMenuItem );
        }
    }

    @Override
    protected void customizePostPropertiesMenuItems()
    {
        if ( postPropertiesMenuItems.isEmpty() == false )
        {
            for ( ApplicationFrameMenuComponent postPropertiesMenuItem : postPropertiesMenuItems )
            {
                addMenuComponent( postPropertiesMenuItem );
            }
        }
    }

    public void addAdditionalNewMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        additionalNewItems.add( applicationComponent );
    }

    public void removeAdditionalNewMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        additionalNewItems.remove( applicationComponent );
    }

    public void addPostPropertiesMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        postPropertiesMenuItems.add( applicationComponent );
    }

    public void removePostPropertiesMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        postPropertiesMenuItems.remove( applicationComponent );
    }
}
