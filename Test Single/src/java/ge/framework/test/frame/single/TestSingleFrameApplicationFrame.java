package ge.framework.test.frame.single;

import com.jidesoft.action.DockableBarContext;
import ge.framework.application.frame.core.menu.RestartApplicationMenuItem;
import ge.framework.application.frame.single.SingleFrameApplication;
import ge.framework.frame.core.command.ApplicationCommandBar;
import ge.framework.frame.core.command.ApplicationCommandMenuBar;
import ge.framework.frame.core.dockable.logger.LoggerFrame;
import ge.framework.frame.core.menu.view.ViewMenu;
import ge.framework.frame.core.menu.window.DocumentWorkspaceWindowMenu;
import ge.framework.frame.single.DocumentSingleFrameApplicationFrame;
import ge.framework.frame.single.command.SinglePropertiesCommandBar;
import ge.framework.frame.single.menu.file.SingleFileMenu;
import ge.utils.bundle.Resources;

import java.awt.HeadlessException;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 17:26
 */
public class TestSingleFrameApplicationFrame extends DocumentSingleFrameApplicationFrame
{
    private static final Resources resources = Resources.getInstance( "ge.framework.test.frame.single" );
    private static int index = 0;

    private ApplicationCommandMenuBar commandMenuBar;

    public TestSingleFrameApplicationFrame( SingleFrameApplication application ) throws HeadlessException
    {
        super( application );
    }

    @Override
    protected void initialiseDocumentWorkspaceApplicationFrame()
    {
        initialiseCommandMenuBar();

        addFrame( new LoggerFrame() );

        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );

        SingleTestFrame singleTestFrame = new SingleTestFrame("Frame "+(index++));
        singleTestFrame.setVisible( false );
        addFrame( singleTestFrame );

        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame( "Frame " + ( index++ ) ) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );

        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
        openDocument( new SingleTestDocument( "Document " + ( index++ ) ) );
    }

    private void initialiseCommandMenuBar()
    {
        commandMenuBar = new ApplicationCommandMenuBar( "menuBar" );
        commandMenuBar.setTitle( resources.getResourceString( TestSingleFrameApplicationFrame.class, "menuBar", "title" ) );
        commandMenuBar.setInitSide( DockableBarContext.DOCK_SIDE_NORTH );
        commandMenuBar.setRearrangable( false );
        commandMenuBar.setFloatable( false );
        commandMenuBar.setHidable( false );
        commandMenuBar.setAllowedDockSides( DockableBarContext.DOCK_SIDE_NORTH );
        commandMenuBar.setInitIndex( 0 );
        commandMenuBar.setStretch( true );
        commandMenuBar.setPaintBackground( false );
        commandMenuBar.setChevronAlwaysVisible( false );

        commandMenuBar.setAutoscrolls( true );
        SingleFileMenu fileMenu = new SingleFileMenu( this );
        fileMenu.initialise();
        RestartApplicationMenuItem restartApplicationMenuItem = new RestartApplicationMenuItem( this );
        restartApplicationMenuItem.initialise();
        fileMenu.addPostPropertiesMenuComponent( restartApplicationMenuItem );
        commandMenuBar.add( fileMenu );
        ViewMenu viewMenu = new ViewMenu( this );
        viewMenu.initialise();
        commandMenuBar.add( viewMenu );
        DocumentWorkspaceWindowMenu windowMenu = new DocumentWorkspaceWindowMenu( this );
        windowMenu.initialise();
        commandMenuBar.add( windowMenu );

        addDockableBar( commandMenuBar );
        SinglePropertiesCommandBar dockableBar = new SinglePropertiesCommandBar( this );
        dockableBar.initialise();
        addDockableBar( dockableBar );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
        addDockableBar( new ApplicationCommandBar( "Command Bar " + index++ ) );
    }

    @Override
    protected void saveFrameData()
    {

    }

    @Override
    protected void loadFrameData()
    {

    }

    @Override
    public boolean confirmWindowClosing()
    {
        return true;
    }
}
