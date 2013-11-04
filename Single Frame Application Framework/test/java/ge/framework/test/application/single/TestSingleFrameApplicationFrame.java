package ge.framework.test.application.single;

import ge.framework.application.frame.core.command.ApplicationCommandBar;
import ge.framework.application.frame.core.menu.RestartApplicationMenuItem;
import ge.framework.application.frame.core.menu.view.ViewMenu;
import ge.framework.application.frame.core.menu.window.DocumentWorkspaceWindowMenu;
import ge.framework.application.frame.single.DocumentSingleFrameApplicationFrame;
import ge.framework.application.frame.single.SingleFrameApplication;
import ge.framework.application.frame.single.command.SinglePropertiesCommandBar;
import ge.framework.application.frame.single.menu.file.SingleFileMenu;
import ge.framework.frame.core.dockable.logger.LoggerFrame;
import ge.framework.test.application.single.docking.SingleTestFrame;
import ge.framework.test.application.single.document.SingleTestDocument;
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
    private static final Resources resources = Resources.getInstance( "ge.framework.test.application.single" );
    private static int index = 0;

    public TestSingleFrameApplicationFrame( SingleFrameApplication application ) throws HeadlessException
    {
        super( application );
    }

    @Override
    protected void initialiseDocumentWorkspaceApplicationFrame()
    {
        initialiseCommandMenuBar();

//        addFrame( new LoggerFrame() );

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
        SingleFileMenu fileMenu = new SingleFileMenu( this );
        fileMenu.initialise();
        RestartApplicationMenuItem restartApplicationMenuItem = new RestartApplicationMenuItem( this );
        restartApplicationMenuItem.initialise();
        fileMenu.addPostPropertiesMenuComponent( restartApplicationMenuItem );
        addMenu( fileMenu );

        ViewMenu viewMenu = new ViewMenu( this );
        viewMenu.initialise();
        addMenu( viewMenu );

        DocumentWorkspaceWindowMenu windowMenu = new DocumentWorkspaceWindowMenu( this );
        windowMenu.initialise();
        addMenu( windowMenu );

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
