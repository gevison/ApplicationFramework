package ge.framework.application.frame.core.manager;

import com.jidesoft.action.DefaultDockableBarManager;
import com.jidesoft.action.DockableBar;
import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.command.ApplicationCommandBar;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;
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

    public void addDockableBar( ApplicationCommandBar dockableBar )
    {
        super.addDockableBar( dockableBar );
    }

    public void removeDockableBar( ApplicationCommandBar dockableBar )
    {
        super.removeDockableBar( dockableBar.getKey() );
    }

    public List<ApplicationCommandBar> getCommandBars()
    {
        Collection<DockableBar> dockableBars = getAllDockableBars();

        List<ApplicationCommandBar> retVal = new ArrayList<ApplicationCommandBar>();

        if ( ( dockableBars != null ) && ( dockableBars.isEmpty() == false ) )
        {
            for ( DockableBar dockableBar : dockableBars )
            {
                if ( dockableBar instanceof ApplicationCommandBar )
                {
                    retVal.add( ( ApplicationCommandBar ) dockableBar );
                }
            }
        }
        return retVal;
    }
}
