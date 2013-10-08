package ge.framework.application.frame.core.menu.file;

import ge.framework.application.frame.core.menu.ApplicationPropertiesFrameMenuItem;
import ge.framework.application.frame.core.menu.ExitApplicationMenuItem;
import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenu;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenuSeparator;
import ge.utils.bundle.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class FileMenu<FRAME extends ApplicationFrame> extends ApplicationFrameMenu<FRAME>
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core" );

    private ApplicationPropertiesFrameMenuItem applicationPropertiesFrameMenuItem;

    private ExitApplicationMenuItem exitApplicationMenuItem;

    public FileMenu( FRAME applicationFrame )
    {
        super( applicationFrame, false );
    }

    @Override
    protected void initialiseMenu()
    {
        setText( resources.getResourceString( FileMenu.class, "label" ) );
        setStatusBarText( resources.getResourceString( FileMenu.class, "status" ) );
        setMnemonic( resources.getResourceCharacter( FileMenu.class, "mnemonic" ) );

        applicationPropertiesFrameMenuItem = new ApplicationPropertiesFrameMenuItem( applicationFrame );
        applicationPropertiesFrameMenuItem.initialise();

        exitApplicationMenuItem = new ExitApplicationMenuItem( applicationFrame );
        exitApplicationMenuItem.initialise();

        initialiseFileMenu();
    }

    protected abstract void initialiseFileMenu();

    @Override
    protected void customizeMenu()
    {
        customizePrePropertiesMenuItems();
        addMenuComponent( new ApplicationFrameMenuSeparator() );
        addMenuComponent( applicationPropertiesFrameMenuItem );
        customizeOtherPropertiesMenuItems();
        addMenuComponent( new ApplicationFrameMenuSeparator() );
        customizePostPropertiesMenuItems();
        addMenuComponent( new ApplicationFrameMenuSeparator() );
        addMenuComponent( exitApplicationMenuItem );
    }

    protected abstract void customizePrePropertiesMenuItems();

    protected abstract void customizeOtherPropertiesMenuItems();

    protected abstract void customizePostPropertiesMenuItems();

    @Override
    public void update()
    {
    }
}
