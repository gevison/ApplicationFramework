package ge.framework.frame.core.manager;

import com.jidesoft.action.DefaultDockableBarManager;
import com.jidesoft.action.DockableBar;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.command.ApplicationCommandBarComponent;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 15:45
 */
public class ApplicationDockableBarManager extends DefaultDockableBarManager
{
    public ApplicationDockableBarManager( ApplicationFrame applicationFrame, Container container )
    {
        super( applicationFrame, container );
    }

    public void addDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        super.addDockableBar( ( DockableBar ) dockableBar );
    }

    public void removeDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        super.removeDockableBar( dockableBar.getKey() );
    }

    public List<ApplicationCommandBarComponent> getCommandBars()
    {
        Collection<DockableBar> dockableBars = getAllDockableBars();

        List<ApplicationCommandBarComponent> retVal = new ArrayList<ApplicationCommandBarComponent>(  );

        if (( dockableBars != null ) && ( dockableBars.isEmpty() == false ))
        {
            for ( DockableBar dockableBar : dockableBars )
            {
                retVal.add( ( ApplicationCommandBarComponent ) dockableBar );
            }
        }
        return retVal;
    }
}
