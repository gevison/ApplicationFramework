package ge.framework.frame.core.dockable;

import com.jidesoft.docking.DockableFrame;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.manager.ApplicationDockingManager;
import org.jdom2.Element;

import javax.swing.*;

public abstract class ApplicationDockableFrame extends DockableFrame
{
    protected ApplicationDockableFrame()
    {
        this( null );
    }

    protected ApplicationDockableFrame( String key )
    {
        super( key );
    }

    public Icon getMenuIcon()
    {
        return getFrameIcon();
    }

    public String getMenuTitle()
    {
        return getTitle();
    }

    public void showFrame()
    {
        ApplicationDockingManager dockingManager = ( ApplicationDockingManager ) getDockingManager();

        if ( dockingManager != null )
        {
            dockingManager.activateFrame( getKey() );
        }
    }

    public final ApplicationFrame getApplicationFrame()
    {
        ApplicationDockingManager dockingManager = ( ApplicationDockingManager ) getDockingManager();

        if ( dockingManager != null )
        {
            return dockingManager.getApplicationFrame();
        }
        else
        {
            return null;
        }
    }

    public abstract Element saveLayoutData();

    public abstract void loadLayoutData( Element element );
}
