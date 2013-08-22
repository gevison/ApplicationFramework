package ge.framework.frame.multi.menu.file;

import ge.framework.application.core.Application;
import ge.framework.application.multi.MultiApplication;
import ge.framework.application.multi.menu.item.NewMenuItem;
import ge.framework.application.multi.menu.item.OtherNewMenuItem;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenu;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuSeparator;
import ge.framework.frame.multi.objects.FrameDefinition;
import ge.utils.bundle.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 15:54
 */
public class NewMenu extends ApplicationFrameMenu
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    private List<NewMenuItem> newMenuItems = new ArrayList<NewMenuItem>(  );

    private OtherNewMenuItem otherNewMenuItem;

    public NewMenu( ApplicationFrame applicationFrame )
    {
        super(applicationFrame);
    }

    @Override
    protected void initialiseMenu()
    {
        setText( resources.getResourceString( NewMenu.class, "label" ) );
        setMnemonic( resources.getResourceCharacter( NewMenu.class, "mnemonic" ) );

        MultiApplication application = ( MultiApplication ) applicationFrame.getApplication();

        List<FrameDefinition> availableFrameConfigurationNames = application.getFrameDefinitions();

        if ( availableFrameConfigurationNames.size() > 15 )
        {
            for ( int i = 0; i < 10; i++ )
            {
                NewMenuItem newMenuItem = new NewMenuItem( applicationFrame, availableFrameConfigurationNames.get( i ),
                                                 false );

                newMenuItem.initialise();

                newMenuItems.add( newMenuItem );
            }

            addSeparator();

            otherNewMenuItem = new OtherNewMenuItem(applicationFrame);
        }
        else
        {
            for ( FrameDefinition typeName : availableFrameConfigurationNames )
            {
                NewMenuItem newMenuItem = new NewMenuItem( applicationFrame, typeName, false );

                newMenuItem.initialise();

                newMenuItems.add( newMenuItem );
            }
        }
    }

    @Override
    protected void customizeMenu()
    {
        for ( NewMenuItem newMenuItem : newMenuItems )
        {
            addMenuComponent( newMenuItem );
        }

        if ( otherNewMenuItem != null )
        {
            addMenuComponent( new ApplicationFrameMenuSeparator() );
            addMenuComponent( otherNewMenuItem );
        }
    }

    @Override
    public void update()
    {

    }
}
