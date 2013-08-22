package ge.framework.test.frame.single;

import com.jidesoft.action.DockableBarContext;
import ge.framework.application.core.Application;
import ge.framework.application.core.menu.RestartApplicationMenuItem;
import ge.framework.frame.core.command.ApplicationCommandBar;
import ge.framework.frame.core.command.ApplicationCommandMenuBar;
import ge.framework.frame.single.SingleApplicationFrame;
import ge.framework.frame.single.menu.file.SingleFileMenu;
import ge.utils.bundle.Resources;
import ge.utils.controls.breadcrumb.BreadcrumbBar;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 17:26
 */
public class TestSingleApplicationFrame extends SingleApplicationFrame
{
    private static final Resources resources = Resources.getInstance( "ge.framework.test.frame.single" );
    private static int index = 0;

    private ApplicationCommandMenuBar commandMenuBar;

    public TestSingleApplicationFrame( Application application ) throws HeadlessException
    {
        super( application );
    }

    @Override
    protected void initialiseSingleApplicationFrame()
    {
        initialiseCommandMenuBar();

        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );

        SingleTestFrame singleTestFrame = new SingleTestFrame("Frame "+(index++));
        singleTestFrame.setVisible( false );
        addFrame( singleTestFrame );

        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
        addFrame( new SingleTestFrame("Frame "+(index++)) );
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
        commandMenuBar.setTitle( resources.getResourceString( TestSingleApplicationFrame.class, "menuBar", "title" ) );
        commandMenuBar.setInitSide( DockableBarContext.DOCK_SIDE_NORTH );
        commandMenuBar.setRearrangable( false );
        commandMenuBar.setFloatable( false );
        commandMenuBar.setHidable( false );
        commandMenuBar.setAllowedDockSides( DockableBarContext.DOCK_SIDE_NORTH );
        commandMenuBar.setInitIndex( 0 );
        commandMenuBar.setStretch( true );
        commandMenuBar.setPaintBackground( false );
        commandMenuBar.setChevronAlwaysVisible( false );

        SingleFileMenu fileMenu = ( SingleFileMenu ) getFileMenu();
        RestartApplicationMenuItem applicationComponent = new RestartApplicationMenuItem( this );
        applicationComponent.initialise();
        fileMenu.addPostPropertiesMenuComponent( applicationComponent );
        commandMenuBar.add( fileMenu );
        commandMenuBar.add( getViewMenu() );
        commandMenuBar.add( getWindowMenu() );

        addDockableBar( commandMenuBar );
        addDockableBar( getPropertiesCommandBar() );
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
    protected BreadcrumbBar createBreadcrumbBar()
    {
        return null;
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
