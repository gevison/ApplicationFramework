package ge.framework.frame.core.manager;

import com.jidesoft.action.DefaultDockableBarManager;
import com.jidesoft.docking.AutoHideContainer;
import com.jidesoft.docking.DefaultDockingManager;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.Workspace;
import com.jidesoft.document.DocumentComponent;
import com.jidesoft.swing.JideTabbedPane;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.framework.frame.core.document.ApplicationDocumentComponent;
import ge.framework.frame.core.document.ApplicationDocumentPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/02/13
 * Time: 18:26
 */
public class ApplicationDockingManager extends DefaultDockingManager
{
    private static final Logger logger = LogManager.getLogger( ApplicationDockingManager.class );

    private final ApplicationDocumentPane documentPane;

    public ApplicationDockingManager( ApplicationFrame applicationFrame,
                                      DefaultDockableBarManager dockableBarManager )
    {
        super( applicationFrame, dockableBarManager.getMainContainer() );

        setTabbedPaneCustomizer( new ApplicationTabbedPaneCustomizer() );

        logger.trace( "Initialising workspace." );
        Workspace dockingManagerWorkspace = getWorkspace();
        dockingManagerWorkspace.setAdjustOpacityOnFly( true );

        logger.trace( "Initialising document pane." );
        documentPane = new ApplicationDocumentPane();
        dockingManagerWorkspace.add( documentPane, BorderLayout.CENTER );
    }

    public final void addFrame( ApplicationDockableFrame dockableFrame )
    {
        if ( dockableFrame != null )
        {
            logger.debug( "Adding dockable frame: " + dockableFrame.getKey() );
            super.addFrame( dockableFrame );
        }
    }

    public final void removeFrame( ApplicationDockableFrame dockableFrame )
    {
        if ( dockableFrame != null )
        {
            logger.debug( "Removing dockable frame: " + dockableFrame.getKey() );
            super.removeFrame( dockableFrame.getKey() );
        }
    }

    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        openDocument( applicationDocumentComponent, false );
    }

    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent, boolean floating )
    {
        documentPane.openDocument( applicationDocumentComponent, floating );
    }

    public void closeDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        documentPane.closeDocument( applicationDocumentComponent );
    }

    public void closeAll()
    {
        documentPane.closeAll();
    }

    public void closeAllButThis( ApplicationDocumentComponent applicationDocumentComponent )
    {
        documentPane.closeAllButThis( applicationDocumentComponent );
    }

    public void closeCurrentDocument()
    {
        ApplicationDocumentComponent activeDocument = ( ApplicationDocumentComponent ) documentPane.getActiveDocument();

        if ( activeDocument != null )
        {
            closeDocument( activeDocument );
        }
    }

    public void closeAllDocumentExceptCurrent()
    {
        ApplicationDocumentComponent activeDocument = ( ApplicationDocumentComponent ) documentPane.getActiveDocument();

        if ( activeDocument != null )
        {
            closeAllButThis( activeDocument );
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
        List<ApplicationDockableFrame> retVal = new ArrayList<ApplicationDockableFrame>(  );

        List<String> allFrameNames = getAllFrameNames();

        for ( String allFrameName : allFrameNames )
        {
            retVal.add( ( ApplicationDockableFrame ) getFrame( allFrameName ) );
        }

        return retVal;
    }

    public List<ApplicationDocumentComponent> getDocumentComponents()
    {
        List<ApplicationDocumentComponent> retVal = new ArrayList<ApplicationDocumentComponent>(  );

        for ( int i = 0; i < documentPane.getDocumentCount(); i++ )
        {
            retVal.add( ( ApplicationDocumentComponent ) documentPane.getDocumentAt( i ) );
        }

        return retVal;
    }

    public void gotoNextDocument()
    {
        documentPane.nextDocument();
    }

    public void gotoPreviousDocument()
    {
        documentPane.prevDocument();
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
