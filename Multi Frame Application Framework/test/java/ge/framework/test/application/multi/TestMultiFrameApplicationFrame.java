package ge.framework.test.application.multi;

import ge.framework.application.frame.core.menu.view.ViewMenu;
import ge.framework.application.frame.multi.DocumentMultiFrameApplicationFrame;
import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.application.frame.multi.command.FileCommandBar;
import ge.framework.application.frame.multi.command.MultiPropertiesCommandBar;
import ge.framework.application.frame.multi.menu.file.MultiFileMenu;
import ge.framework.application.frame.multi.menu.window.MultiFrameDocumentWindowMenu;
import ge.framework.frame.core.dockable.logger.LoggerFrame;
import ge.utils.bundle.Resources;
import ge.utils.properties.PropertiesDialogPage;

import java.awt.HeadlessException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 14:28
 */
public class TestMultiFrameApplicationFrame extends DocumentMultiFrameApplicationFrame
{
    private static final Resources resources = Resources.getInstance( "ge.framework.test.application.multi" );

    public TestMultiFrameApplicationFrame( MultiFrameApplication application )
            throws HeadlessException
    {
        super( application );
    }

    @Override
    protected void initialiseDocumentWorkspaceApplicationFrame()
    {
        initialiseCommandMenuBar();

//        addFrame(new LoggerFrame() );
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
        MultiFileMenu fileMenu = new MultiFileMenu( this );
        fileMenu.initialise();
        addMenu( fileMenu );

        ViewMenu viewMenu = new ViewMenu( this );
        viewMenu.initialise();
        addMenu( viewMenu );

        MultiFrameDocumentWindowMenu windowMenu = new MultiFrameDocumentWindowMenu( this );
        windowMenu.initialise();
        addMenu( windowMenu );

        FileCommandBar fileCommandBar = new FileCommandBar( this );
        addDockableBar( fileCommandBar );
        MultiPropertiesCommandBar propertiesCommandBar = new MultiPropertiesCommandBar( this );
        propertiesCommandBar.initialise();
        addDockableBar( propertiesCommandBar );
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
