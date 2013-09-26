package ge.framework.frame.single.menu.file;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.file.FileMenu;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
public class SingleFileMenu extends FileMenu
{
    private List<ApplicationFrameMenuComponent> prePropertiesMenuItems = new ArrayList<ApplicationFrameMenuComponent>();

    private List<ApplicationFrameMenuComponent> otherPropertiesMenuItems =
            new ArrayList<ApplicationFrameMenuComponent>();

    private List<ApplicationFrameMenuComponent> postPropertiesMenuItems =
            new ArrayList<ApplicationFrameMenuComponent>();

    public SingleFileMenu( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseFileMenu()
    {
    }

    public void addPrePropertiesMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        prePropertiesMenuItems.add( applicationComponent );
    }

    public void removePrePropertiesMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        prePropertiesMenuItems.remove( applicationComponent );
    }

    public void addOtherPropertiesMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        otherPropertiesMenuItems.add( applicationComponent );
    }

    public void removeOtherPropertiesMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        otherPropertiesMenuItems.remove( applicationComponent );
    }

    public void addPostPropertiesMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        postPropertiesMenuItems.add( applicationComponent );
    }

    public void removePostPropertiesMenuComponent( ApplicationFrameMenuComponent applicationComponent )
    {
        postPropertiesMenuItems.remove( applicationComponent );
    }

    @Override
    protected void customizePrePropertiesMenuItems()
    {
        for ( ApplicationFrameMenuComponent prePropertiesMenuItem : prePropertiesMenuItems )
        {
            addMenuComponent( prePropertiesMenuItem );
        }
    }

    @Override
    protected void customizeOtherPropertiesMenuItems()
    {
        for ( ApplicationFrameMenuComponent otherPropertiesMenuItem : otherPropertiesMenuItems )
        {
            addMenuComponent( otherPropertiesMenuItem );
        }
    }

    @Override
    protected void customizePostPropertiesMenuItems()
    {
        for ( ApplicationFrameMenuComponent postPropertiesMenuItem : postPropertiesMenuItems )
        {
            addMenuComponent( postPropertiesMenuItem );
        }
    }
}
