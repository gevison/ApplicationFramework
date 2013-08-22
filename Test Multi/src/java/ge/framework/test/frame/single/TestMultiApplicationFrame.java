package ge.framework.test.frame.single;

import com.jidesoft.action.DockableBarContext;
import ge.framework.application.core.Application;
import ge.framework.frame.core.command.ApplicationCommandMenuBar;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.framework.frame.multi.menu.file.MultiFileMenu;
import ge.framework.frame.multi.objects.FrameDefinition;
import ge.utils.bundle.Resources;
import ge.utils.controls.breadcrumb.BreadcrumbBar;
import ge.utils.properties.PropertiesDialogPage;

import java.awt.HeadlessException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 14:28
 */
public class TestMultiApplicationFrame extends MultiApplicationFrame
{
    private static final Resources resources = Resources.getInstance( "ge.framework.test.frame.multi" );

    private ApplicationCommandMenuBar commandMenuBar;

    public TestMultiApplicationFrame( Application application,
                                         FrameDefinition frameDefinition )
            throws HeadlessException
    {
        super( application, frameDefinition );
    }

    @Override
    protected void initialiseMultiApplicationFrame()
    {
        initialiseCommandMenuBar();
    }

    @Override
    protected boolean isFrameConfigurationDialogAllow()
    {
        return false;
    }

    @Override
    public List<PropertiesDialogPage> getFrameConfigurationPages()
    {
        return null;
    }

    private void initialiseCommandMenuBar()
    {
        commandMenuBar = new ApplicationCommandMenuBar( "menuBar" );
        commandMenuBar.setTitle( resources.getResourceString( TestMultiApplicationFrame.class, "menuBar", "title" ) );
        commandMenuBar.setInitSide( DockableBarContext.DOCK_SIDE_NORTH );
        commandMenuBar.setRearrangable( false );
        commandMenuBar.setFloatable( false );
        commandMenuBar.setHidable( false );
        commandMenuBar.setAllowedDockSides( DockableBarContext.DOCK_SIDE_NORTH );
        commandMenuBar.setInitIndex( 0 );
        commandMenuBar.setStretch( true );
        commandMenuBar.setPaintBackground( false );
        commandMenuBar.setChevronAlwaysVisible( false );

        MultiFileMenu fileMenu = ( MultiFileMenu ) getFileMenu();
        commandMenuBar.add( fileMenu );
        commandMenuBar.add( getViewMenu() );
        commandMenuBar.add( getWindowMenu() );

        addDockableBar( commandMenuBar );
        addDockableBar( getFileCommandBar() );
        addDockableBar( getPropertiesCommandBar() );
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
