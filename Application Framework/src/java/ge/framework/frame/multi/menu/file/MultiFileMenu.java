package ge.framework.frame.multi.menu.file;

import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.application.multi.menu.RecentlyOpenedMenu;
import ge.framework.application.multi.menu.item.CloseMenuItem;
import ge.framework.application.multi.menu.item.NewMenuItem;
import ge.framework.application.multi.menu.item.OpenMenuItem;
import ge.framework.frame.core.menu.file.FileMenu;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuComponent;
import ge.framework.frame.multi.MultiFrameApplicationFrame;
import ge.framework.frame.multi.menu.file.item.FramePropertiesMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 15:31
 */
public class MultiFileMenu extends FileMenu
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
        MultiFrameApplication application = ( MultiFrameApplication ) applicationFrame.getApplication();

        newItem = new NewMenuItem( applicationFrame, true );
        newItem.initialise();

        openMenuItem = new OpenMenuItem( applicationFrame );
        openMenuItem.initialise();

        recentlyOpenedMenu = new RecentlyOpenedMenu( applicationFrame );
        recentlyOpenedMenu.initialise();

        closeMenuItem = new CloseMenuItem( applicationFrame );
        closeMenuItem.initialise();

        MultiFrameApplicationFrame multiFrameApplicationFrame = ( MultiFrameApplicationFrame ) applicationFrame;

        if ( multiFrameApplicationFrame.shouldCreateFrameConfigurationMenu() == true )
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
