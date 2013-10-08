package ge.framework.application.frame.core.manager;

import com.jidesoft.action.DefaultDockableBarManager;
import com.jidesoft.docking.AutoHideContainer;
import com.jidesoft.docking.DefaultDockingManager;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.Workspace;
import com.jidesoft.swing.JideTabbedPane;
import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.dockable.ApplicationDockableFrame;
import ge.utils.log.LoggerEx;

import javax.swing.JComponent;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/02/13
 * Time: 18:26
 */
public final class ApplicationDockingManager extends DefaultDockingManager
{
    private JComponent workspaceComponent;

    public ApplicationDockingManager( ApplicationFrame applicationFrame,
                                      DefaultDockableBarManager dockableBarManager )
    {
        super( applicationFrame, dockableBarManager.getMainContainer() );

        setTabbedPaneCustomizer( new ApplicationTabbedPaneCustomizer() );

        LoggerEx.trace( "Initialising workspace." );
        Workspace dockingManagerWorkspace = getWorkspace();
        dockingManagerWorkspace.setAdjustOpacityOnFly( true );
    }

    public final void setWorkspaceComponent( JComponent workspaceComponent )
    {
        removeWorkspaceComponent();

        this.workspaceComponent = workspaceComponent;

        if ( this.workspaceComponent != null )
        {
            Workspace workspace = getWorkspace();

            workspace.add( workspaceComponent, BorderLayout.CENTER );
        }
    }

    public final void removeWorkspaceComponent()
    {
        if ( workspaceComponent != null )
        {
            Workspace workspace = getWorkspace();

            workspace.remove( workspaceComponent );

            workspaceComponent = null;
        }
    }

    public final void addFrame( ApplicationDockableFrame dockableFrame )
    {
        if ( dockableFrame != null )
        {
            LoggerEx.debug( "Adding dockable frame: " + dockableFrame.getKey() );
            super.addFrame( dockableFrame );
        }
    }

    public final void removeFrame( ApplicationDockableFrame dockableFrame )
    {
        if ( dockableFrame != null )
        {
            LoggerEx.debug( "Removing dockable frame: " + dockableFrame.getKey() );
            super.removeFrame( dockableFrame.getKey() );
        }
    }

    public void setAutoHideAreaVisible( boolean visible )
    {
        setAutoHideAreaVisible( DockContext.DOCK_SIDE_NORTH, visible );
        setAutoHideAreaVisible( DockContext.DOCK_SIDE_SOUTH, visible );
        setAutoHideAreaVisible( DockContext.DOCK_SIDE_EAST, visible );
        setAutoHideAreaVisible( DockContext.DOCK_SIDE_WEST, visible );
    }

    private void setAutoHideAreaVisible( int side, boolean visible )
    {
        AutoHideContainer autoHideContainer = getAutoHideContainer( side );

        Container viewPort = autoHideContainer.getParent();
        Container simpleScrollPanel = viewPort.getParent();
        simpleScrollPanel.setVisible( visible );
        Container parent = simpleScrollPanel.getParent();
        parent.validate();
    }

    public boolean isAutoHideAreaVisible()
    {
        if ( isAutoHideAreaVisible( DockContext.DOCK_SIDE_NORTH ) == true )
        {
            return true;
        }

        if ( isAutoHideAreaVisible( DockContext.DOCK_SIDE_SOUTH ) == true )
        {
            return true;
        }

        if ( isAutoHideAreaVisible( DockContext.DOCK_SIDE_EAST ) == true )
        {
            return true;
        }

        if ( isAutoHideAreaVisible( DockContext.DOCK_SIDE_WEST ) == true )
        {
            return true;
        }

        return false;
    }

    private boolean isAutoHideAreaVisible( int side )
    {
        AutoHideContainer autoHideContainer = getAutoHideContainer( side );

        if ( autoHideContainer != null )
        {
            Container viewPort = autoHideContainer.getParent();
            Container simpleScrollPanel = viewPort.getParent();
            return simpleScrollPanel.isVisible();
        }
        else
        {
            return false;
        }
    }

    public ApplicationFrame getApplicationFrame()
    {
        return ( ApplicationFrame ) getRootPaneContainer();
    }

    public List<ApplicationDockableFrame> getDockingFrames()
    {
        List<ApplicationDockableFrame> retVal = new ArrayList<ApplicationDockableFrame>();

        List<String> allFrameNames = getAllFrameNames();

        for ( String allFrameName : allFrameNames )
        {
            retVal.add( ( ApplicationDockableFrame ) getFrame( allFrameName ) );
        }

        return retVal;
    }

    private class ApplicationTabbedPaneCustomizer implements TabbedPaneCustomizer
    {
        @Override
        public void customize( JideTabbedPane jideTabbedPane )
        {
            jideTabbedPane.setTabPlacement( SwingConstants.TOP );
        }
    }
}
