package ge.framework.frame.core.command.properties;

import com.jidesoft.action.DockableBarContext;
import ge.framework.application.core.command.ApplicationPropertiesCommandButton;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.command.ApplicationCommandBar;
import ge.utils.bundle.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 13:29
 */
public abstract class PropertiesCommandBar extends ApplicationCommandBar
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core" );

    protected ApplicationFrame applicationFrame;

    private ApplicationPropertiesCommandButton applicationPropertiesCommandButton;

    public PropertiesCommandBar( ApplicationFrame applicationFrame )
    {
        super( "propertiesCommandBar" );

        this.applicationFrame = applicationFrame;
    }

    public final void initialise()
    {
        setTitle( resources.getResourceString( PropertiesCommandBar.class, "label" ) );
        setInitSide( DockableBarContext.DOCK_SIDE_NORTH );
        setAllowedDockSides( DockableBarContext.DOCK_SIDE_NORTH );
        setFloatable( true );
        setHidable( true );
        setInitIndex( 1 );
        setChevronAlwaysVisible( false );
        setStretch( false );

        applicationPropertiesCommandButton = new ApplicationPropertiesCommandButton( applicationFrame );

        add( applicationPropertiesCommandButton );

        initialisePropertiesCommandBar();
    }

    protected abstract void initialisePropertiesCommandBar();
}
