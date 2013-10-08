package ge.framework.application.frame.core;


import com.jidesoft.status.StatusBarItem;
import com.jidesoft.swing.ContentContainer;
import ge.framework.application.core.Application;
import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.application.frame.core.command.ApplicationCommandBarComponent;
import ge.framework.application.frame.core.dockable.ApplicationDockableFrame;
import ge.framework.application.frame.core.manager.ApplicationDockableBarManager;
import ge.framework.application.frame.core.manager.ApplicationDockingManager;
import ge.framework.application.frame.core.persistence.ApplicationLayoutPersistenceManager;
import ge.framework.application.frame.core.status.ApplicationStatusBar;
import ge.framework.application.frame.core.status.enums.StatusBarConstraint;
import ge.utils.bundle.Resources;
import ge.utils.controls.breadcrumb.BreadcrumbBar;
import ge.utils.log.LoggerEx;
import ge.utils.os.OS;
import ge.utils.text.StringArgumentMessageFormat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ApplicationFrame<APPLICATION extends FrameApplication> extends JFrame
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core" );

    private static Logger logger = LogManager.getLogger( ApplicationFrame.class );

    private ApplicationFrameWindowAdapter applicationFrameWindowAdapter;

    private ApplicationDockableBarManager dockableBarManager;

    private ApplicationDockingManager dockingManager;

    private ApplicationLayoutPersistenceManager layoutPersistenceManager;

    private JScrollPane breadcrumbBarScrollPane;

    private ApplicationStatusBar statusBar;

    private boolean manualClose;

    protected APPLICATION application;

    public ApplicationFrame( APPLICATION application ) throws HeadlessException
    {
        this.application = application;
    }

    public void initialise()
    {
        initialiseFrame();

        setTitle( null );
        if ( OS.isMac() == true )
        {
            setIconImage( application.getMacImage() );
        }
        else
        {
            setIconImage( application.getSmallImage() );
        }

        initialiseApplicationFrame();
    }

    protected abstract void initialiseApplicationFrame();

    @Override
    public void setTitle( String title )
    {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "applicationName", getApplication().getDisplayName() );

        String resourceString;
        if ( title == null )
        {
            resourceString = resources.getResourceString( ApplicationFrame.class, "frame", "title" );
        }
        else
        {
            resourceString = resources.getResourceString( ApplicationFrame.class, "frame", "exTitle" );
            arguments.put( "title", title );
        }

        resourceString = StringArgumentMessageFormat.format( resourceString, arguments );

        super.setTitle( resourceString );
    }

    private void initialiseFrame()
    {
        LoggerEx.trace( "Initialising frame." );

        applicationFrameWindowAdapter = new ApplicationFrameWindowAdapter( this );

        addWindowListener( applicationFrameWindowAdapter );

        setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );

        ContentContainer localContentContainer = new ContentContainer();

        Container contentPane = getContentPane();

        contentPane.setLayout( new BorderLayout() );
        contentPane.add( localContentContainer, BorderLayout.CENTER );

        LoggerEx.trace( "Initialising dockable bar manager." );
        dockableBarManager = new ApplicationDockableBarManager( this, localContentContainer );
        dockableBarManager.setRearrangable( true );

        LoggerEx.trace( "Initialising docking manager." );
        dockingManager = new ApplicationDockingManager( this, dockableBarManager );

        LoggerEx.trace( "Initialising status bar." );
        statusBar = new ApplicationStatusBar();
        localContentContainer.add( statusBar, BorderLayout.AFTER_LAST_LINE );

        LoggerEx.trace( "Initialising layout persistence manager." );
        layoutPersistenceManager = new ApplicationLayoutPersistenceManager( application.getName() );
        layoutPersistenceManager.addLayoutPersistence( dockableBarManager );
        layoutPersistenceManager.addLayoutPersistence( dockingManager );
    }

    public void setBreadcrumbBar( BreadcrumbBar breadcrumbBar )
    {
        removeBreadcrumbBar();

        if ( breadcrumbBar != null )
        {
            Container contentContainer = dockingManager.getContentContainer();
            breadcrumbBarScrollPane = new JScrollPane( breadcrumbBar, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                                                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
            breadcrumbBarScrollPane.setBorder( null );
            contentContainer.add( breadcrumbBarScrollPane, BorderLayout.BEFORE_FIRST_LINE );
        }
    }

    public void removeBreadcrumbBar()
    {
        if ( breadcrumbBarScrollPane != null )
        {
            Container contentContainer = dockingManager.getContentContainer();

            contentContainer.remove( breadcrumbBarScrollPane );

            breadcrumbBarScrollPane = null;
        }
    }

    public void setWorkspaceComponent( JComponent workspaceComponent )
    {
        dockingManager.setWorkspaceComponent( workspaceComponent );
    }

    public void removeWorkspaceComponent()
    {
        dockingManager.removeWorkspaceComponent();
    }

    public void addFrame( ApplicationDockableFrame dockableFrame )
    {
        dockingManager.addFrame( dockableFrame );
    }

    public void removeFrame( ApplicationDockableFrame dockableFrame )
    {
        dockingManager.removeFrame( dockableFrame );
    }

    public List<ApplicationDockableFrame> getDockingFrames()
    {
        if ( dockingManager != null )
        {
            return dockingManager.getDockingFrames();
        }

        return null;
    }

    public final void addDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        if ( dockableBar != null )
        {
            LoggerEx.debug( "Adding dockable bar: " + dockableBar.getKey() );
            dockableBarManager.addDockableBar( dockableBar );
        }
    }

    public final void removeDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        if ( dockableBar != null )
        {
            LoggerEx.debug( "Removing dockable bar: " + dockableBar.getKey() );
            dockableBarManager.removeDockableBar( dockableBar );
        }
    }

    public List<ApplicationCommandBarComponent> getCommandBars()
    {
        if ( dockableBarManager != null )
        {
            return dockableBarManager.getCommandBars();
        }
        return null;
    }

    public void addStatusBarItem( StatusBarItem statusBarItem,
                                  StatusBarConstraint constraint )
    {
        statusBar.add( statusBarItem, constraint );
    }

    public void removeStatusBarItem( StatusBarItem statusBarItem )
    {
        statusBar.remove( statusBarItem );
    }

    public final void setAutoHideAreaVisible( boolean visible )
    {
        dockingManager.setAutoHideAreaVisible( visible );
    }

    public final boolean isAutoHideAreaVisible()
    {
        return dockingManager.isAutoHideAreaVisible();
    }

    public final void setStatusBarVisible( boolean visible )
    {
        statusBar.setVisible( visible );
    }

    public final boolean isStatusBarVisible()
    {
        return statusBar.isVisible();
    }

    public final void saveFrame()
    {
        saveFrameData();

        layoutPersistenceManager.saveLayoutData();

        setStatusBarConfiguredVisible( isStatusBarVisible() );
        setToolButtonsConfiguredVisible( isAutoHideAreaVisible() );

        saveFrameEx();
    }

    protected abstract void saveFrameData();

    protected abstract void saveFrameEx();

    public final void loadFrame()
    {
        loadFrameData();

        layoutPersistenceManager.setLayoutDirectory( getLayoutDirectory() );

        layoutPersistenceManager.loadLayoutData();

        setStatusBarVisible( isStatusBarConfiguredVisible() );
        setAutoHideAreaVisible( isToolButtonsConfiguredVisible() );
    }

    protected abstract void loadFrameData();

    public final void close()
    {
        manualClose = true;
        LoggerEx.info( "Firing close event." );
        WindowEvent windowEvent = new WindowEvent( this, WindowEvent.WINDOW_CLOSING );
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent( windowEvent );
    }

    private void closing()
    {
        if ( ( application.isRestarting() == true ) || ( confirmWindowClosing() == true ) )
        {
            LoggerEx.debug( "Closing window." );

            try
            {
                processWindowClose();
            }
            catch ( Exception e )
            {
                LoggerEx.warn( e.getMessage(), e );
            }

            if ( layoutPersistenceManager != null )
            {
                layoutPersistenceManager.clear();
                layoutPersistenceManager = null;
            }

            if ( dockableBarManager != null )
            {
                dockableBarManager.dispose();
                dockableBarManager = null;
            }

            if ( dockingManager != null )
            {
                dockingManager.dispose();
                dockingManager = null;
            }

            try
            {
                dispose();
            }
            catch ( Exception e )
            {
                LoggerEx.warn( e.getMessage(), e );
            }
        }
    }

    public abstract boolean confirmWindowClosing();

    protected final void processWindowClose()
    {
        saveFrame();

        processWindowCloseEx();
    }

    protected abstract void processWindowCloseEx();

    private void closed()
    {
        LoggerEx.debug( "Window closed." );

        removeWindowListener( applicationFrameWindowAdapter );
        applicationFrameWindowAdapter.dispose();
        applicationFrameWindowAdapter = null;
    }

    public APPLICATION getApplication()
    {
        return application;
    }

    public final boolean isManualClose()
    {
        return manualClose;
    }

    protected abstract boolean isStatusBarConfiguredVisible();

    protected abstract void setStatusBarConfiguredVisible( boolean statusBarVisible );

    protected abstract boolean isToolButtonsConfiguredVisible();

    protected abstract void setToolButtonsConfiguredVisible( boolean autoHideAreaVisible );

    protected abstract File getLayoutDirectory();

    public static class ApplicationFrameWindowAdapter extends WindowAdapter
    {
        private ApplicationFrame applicationFrame;

        public ApplicationFrameWindowAdapter( ApplicationFrame applicationFrame )
        {
            this.applicationFrame = applicationFrame;
        }

        @Override
        public void windowClosing( WindowEvent e )
        {
            Application application = applicationFrame.getApplication();

            if ( ( applicationFrame.isManualClose() == true ) ||
                    ( application.closeOrExit() == CloseOrExitEnum.CLOSE ) )
            {
                applicationFrame.closing();
            }
            else
            {
                application.processExit();
            }
        }

        @Override
        public void windowClosed( WindowEvent e )
        {
            applicationFrame.closed();
        }

        public void dispose()
        {
            applicationFrame = null;
        }
    }
}
