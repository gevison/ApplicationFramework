package ge.framework.application.frame.core.menu.item;

import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameCheckboxMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 13:34
 */
public class ToggleToolButtonsMenuItem extends ApplicationFrameCheckboxMenuItem
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core" );

    public ToggleToolButtonsMenuItem( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );

        setText( "Tool Buttons" );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( ToggleToolButtonsMenuItem.class, "label" ) );
        setStatusBarText( resources.getResourceString( ToggleToolButtonsMenuItem.class, "status" ) );
        setMnemonic( resources.getResourceCharacter( ToggleToolButtonsMenuItem.class, "mnemonic" ) );
    }

    public void update()
    {
        setSelected( applicationFrame.isAutoHideAreaVisible() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        applicationFrame.setAutoHideAreaVisible( !applicationFrame.isAutoHideAreaVisible() );
    }
}
