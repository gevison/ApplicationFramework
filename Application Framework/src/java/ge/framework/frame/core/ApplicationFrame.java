package ge.framework.frame.core;


import com.jidesoft.status.StatusBarItem;
import com.jidesoft.swing.ContentContainer;
import ge.framework.application.core.Application;
import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.frame.core.command.ApplicationCommandBarComponent;
import ge.framework.frame.core.command.properties.PropertiesCommandBar;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.framework.frame.core.document.ApplicationDocumentComponent;
import ge.framework.frame.core.manager.ApplicationDockableBarManager;
import ge.framework.frame.core.manager.ApplicationDockingManager;
import ge.framework.frame.core.menu.file.FileMenu;
import ge.framework.frame.core.menu.view.ViewMenu;
import ge.framework.frame.core.menu.window.WindowMenu;
import ge.framework.frame.core.persistence.ApplicationLayoutPersistenceManager;
import ge.framework.frame.core.status.ApplicationStatusBar;
import ge.framework.frame.core.status.enums.StatusBarConstraint;
import ge.utils.controls.breadcrumb.BreadcrumbBar;
import ge.utils.os.OS;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

public abstract class ApplicationFrame extends JFrame
{
    private static Logger logger = Logger.getLogger( ApplicationFrame.class );

    private ApplicationFrameWindowAdapter applicationFrameWindowAdapter;

    private ApplicationDockableBarManager dockableBarManager;

    private ApplicationDockingManager dockingManager;

    private ApplicationLayoutPersistenceManager layoutPersistenceManager;

    private BreadcrumbBar breadcrumbBar;

    private ApplicationStatusBar statusBar;

    private boolean manualClose;

    private Application application;

    private ViewMenu viewMenu;

    public ApplicationFrame( Application application ) throws HeadlessException
    {
        this.application = application;
    }

    public void initialise()
    {
        initialiseFrame();

        setTitle( null );
        if ( OS.isMac() == true )
        {
            setIconImage( getMacImage() );
        }
        else
        {
            setIconImage( getSmallImage() );
        }

        viewMenu = new ViewMenu( this );
        viewMenu.initialise();

        // TODO: Menus

        initialiseApplicationFrame();
    }

    protected abstract void initialiseApplicationFrame();

    private void initialiseFrame()
    {
        logger.trace( "Initialising frame." );

        applicationFrameWindowAdapter = new ApplicationFrameWindowAdapter( this );

        addWindowListener( applicationFrameWindowAdapter );

        setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );

        ContentContainer localContentContainer = new ContentContainer();

        Container contentPane = getContentPane();

        contentPane.setLayout( new BorderLayout() );
        contentPane.add( localContentContainer, BorderLayout.CENTER );

        logger.trace( "Initialising dockable bar manager." );
        dockableBarManager = new ApplicationDockableBarManager( this, localContentContainer );
        dockableBarManager.setRearrangable( true );

        logger.trace( "Initialising docking manager." );
        dockingManager = new ApplicationDockingManager( this, dockableBarManager );

        breadcrumbBar = createBreadcrumbBar();

        if ( breadcrumbBar != null )
        {
            Container contentContainer = dockingManager.getContentContainer();
            JScrollPane scrollPane = new JScrollPane( breadcrumbBar, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                                                      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
            scrollPane.setBorder( null );
            contentContainer.add( scrollPane, BorderLayout.BEFORE_FIRST_LINE );
        }

        logger.trace( "Initialising status bar." );
        statusBar = new ApplicationStatusBar();
        localContentContainer.add( statusBar, BorderLayout.AFTER_LAST_LINE );

        logger.trace( "Initialising layout persistence manager." );
        layoutPersistenceManager = new ApplicationLayoutPersistenceManager( getProfileKey() );
        layoutPersistenceManager.addLayoutPersistence( dockableBarManager );
        layoutPersistenceManager.addLayoutPersistence( dockingManager );
    }

    protected abstract BreadcrumbBar createBreadcrumbBar();

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

    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        dockingManager.openDocument( applicationDocumentComponent );
    }

    public void closeDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        dockingManager.closeDocument( applicationDocumentComponent );
    }

    public void closeAllDocuments()
    {
        dockingManager.closeAll();
    }

    public void closeAllButThis(
            ApplicationDocumentComponent applicationDocumentComponent )
    {
        dockingManager.closeAllButThis( applicationDocumentComponent );
    }

    public void closeCurrentDocument()
    {
        dockingManager.closeCurrentDocument();
    }

    public void closeAllDocumentExceptCurrent()
    {
        dockingManager.closeAllDocumentExceptCurrent();
    }

    public List<ApplicationDocumentComponent> getDocumentComponents()
    {
        if ( dockingManager != null )
        {
            return dockingManager.getDocumentComponents();
        }

        return null;
    }

    public final void gotoNextDocument()
    {
        dockingManager.gotoNextDocument();
    }

    public final void gotoPreviousDocument()
    {
        dockingManager.gotoPreviousDocument();
    }

    public final void addDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        if ( dockableBar != null )
        {
            logger.debug( "Adding dockable bar: " + dockableBar.getKey() );
            dockableBarManager.addDockableBar( dockableBar );
        }
    }

    public final void removeDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        if ( dockableBar != null )
        {
            logger.debug( "Removing dockable bar: " + dockableBar.getKey() );
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
        logger.info( "Firing close event." );
        WindowEvent windowEvent = new WindowEvent( this, WindowEvent.WINDOW_CLOSING );
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent( windowEvent );
    }

    private void closing()
    {
        if ( ( application.isRestarting() == true ) || ( confirmWindowClosing() == true ) )
        {
            logger.debug( "Closing window." );

            try
            {
                processWindowClose();
            }
            catch ( Exception e )
            {
                logger.warn( e.getMessage(), e );
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
                logger.warn( e.getMessage(), e );
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
        logger.debug( "Window closed." );

        removeWindowListener( applicationFrameWindowAdapter );
        applicationFrameWindowAdapter.dispose();
        applicationFrameWindowAdapter = null;
    }

    public Application getApplication()
    {
        return application;
    }

    public abstract FileMenu getFileMenu();

    public ViewMenu getViewMenu()
    {
        return viewMenu;
    }

    public abstract WindowMenu getWindowMenu();

    public abstract PropertiesCommandBar getPropertiesCommandBar();

    public final boolean isManualClose()
    {
        return manualClose;
    }

    protected abstract String getProfileKey();

    public abstract Icon getSmallIcon();

    public abstract Image getSmallImage();

    public abstract Icon getLargeIcon();

    public abstract Image getLargeImage();

    public abstract Icon getMacIcon();

    public abstract Image getMacImage();

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
