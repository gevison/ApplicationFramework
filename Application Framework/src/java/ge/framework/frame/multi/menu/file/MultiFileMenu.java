package ge.framework.frame.multi.menu.file;

import ge.framework.application.core.Application;
import ge.framework.application.multi.MultiApplication;
import ge.framework.application.multi.menu.RecentlyOpenedMenu;
import ge.framework.application.multi.menu.item.CloseMenuItem;
import ge.framework.application.multi.menu.item.NewMenuItem;
import ge.framework.application.multi.menu.item.OpenMenuItem;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.file.FileMenu;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuComponent;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.framework.frame.multi.menu.file.item.FramePropertiesMenuItem;
import ge.framework.frame.multi.objects.FrameDefinition;

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
    private List<NewMenuItem> newItems;

    private NewMenu newMenu;

    private List<ApplicationFrameMenuComponent> additionalNewItems = new ArrayList<ApplicationFrameMenuComponent>(  );

    private OpenMenuItem openMenuItem;

    private CloseMenuItem closeMenuItem;

    private RecentlyOpenedMenu recentlyOpenedMenu;

    private FramePropertiesMenuItem framePropertiesMenuItem;

    private List<ApplicationFrameMenuComponent> postPropertiesMenuItems = new ArrayList<ApplicationFrameMenuComponent>();

    public MultiFileMenu( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseFileMenu()
    {
        MultiApplication application = ( MultiApplication ) applicationFrame.getApplication();

        List<FrameDefinition> availableFrameConfigurationNames = application.getFrameDefinitions();

        if ( availableFrameConfigurationNames.size() <= 5 )
        {
            newItems = new ArrayList<NewMenuItem>(  );
            for ( FrameDefinition availableFrameConfigurationName : availableFrameConfigurationNames )
            {
                NewMenuItem newMenuItem = new NewMenuItem( applicationFrame, availableFrameConfigurationName, true );
                newMenuItem.initialise();
                newItems.add(newMenuItem);
            }
        }
        else
        {
            newMenu = new NewMenu(applicationFrame);
            newMenu.initialise();
        }

        openMenuItem = new OpenMenuItem(applicationFrame);
        openMenuItem.initialise();

        recentlyOpenedMenu = new RecentlyOpenedMenu( applicationFrame );
        recentlyOpenedMenu.initialise();

        closeMenuItem = new CloseMenuItem( applicationFrame );
        closeMenuItem.initialise();

        MultiApplicationFrame multiApplicationFrame = ( MultiApplicationFrame ) applicationFrame;

        if ( multiApplicationFrame.shouldCreateFrameConfigurationMenu() == true )
        {
            framePropertiesMenuItem = new FramePropertiesMenuItem( applicationFrame );
            framePropertiesMenuItem.initialise();
        }
    }

    @Override
    protected void customizePrePropertiesMenuItems()
    {
        if ( newItems != null )
        {
            for ( NewMenuItem newItem : newItems )
            {
                addMenuComponent( newItem );
            }
        }
        else if ( newMenu != null )
        {
            addMenuComponent( newMenu );
        }

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
