package ge.framework.frame.single.menu.window;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuComponent;
import ge.framework.frame.core.menu.window.WindowMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/07/2013
 * Time: 01:47
 * To change this template use File | Settings | File Templates.
 */
public class SingleWindowMenu extends WindowMenu
{
    private List<ApplicationFrameMenuComponent> additionalMenuItems =
            new ArrayList<ApplicationFrameMenuComponent>();

    public SingleWindowMenu( ApplicationFrame applicationFrame )
    {
        super(applicationFrame);
    }

    @Override
    protected void initialiseWindowMenu()
    {
    }

    @Override
    protected void customizeWindowMenu()
    {
        for ( ApplicationFrameMenuComponent additionalMenuItem : additionalMenuItems )
        {
            addMenuComponent( additionalMenuItem );
        }
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
