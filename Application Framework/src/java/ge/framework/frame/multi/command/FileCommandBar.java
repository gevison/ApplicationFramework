package ge.framework.frame.multi.command;

import com.jidesoft.action.DockableBarContext;
import ge.framework.application.multi.command.NewCommandButton;
import ge.framework.application.multi.command.OpenCommandButton;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.command.ApplicationCommandBar;
import ge.utils.bundle.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 11:55
 */
public class FileCommandBar extends ApplicationCommandBar
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.multi" );

    private NewCommandButton newCommandButton;

    private OpenCommandButton openCommandButton;

    public FileCommandBar( ApplicationFrame applicationFrame )
    {
        super( "fileCommandBar" );

        newCommandButton = new NewCommandButton( applicationFrame );
        openCommandButton = new OpenCommandButton( applicationFrame );

        setTitle( resources.getResourceString( FileCommandBar.class, "label" ) );
        setInitSide( DockableBarContext.DOCK_SIDE_NORTH );
        setAllowedDockSides( DockableBarContext.DOCK_SIDE_NORTH );
        setFloatable( true );
        setHidable( true );
        setInitIndex( 1 );
        setChevronAlwaysVisible( false );
        setStretch( false );

        add( newCommandButton );
        add( openCommandButton );
    }
}
