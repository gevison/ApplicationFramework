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
public class ToggleStatusBarMenuItem extends ApplicationFrameCheckboxMenuItem
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core" );

    public ToggleStatusBarMenuItem( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( ToggleStatusBarMenuItem.class, "label" ) );
        setStatusBarText( resources.getResourceString( ToggleStatusBarMenuItem.class, "status" ) );
        setMnemonic( resources.getResourceCharacter( ToggleStatusBarMenuItem.class, "mnemonic" ) );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        applicationFrame.setStatusBarVisible( !applicationFrame.isStatusBarVisible() );
    }

    @Override
    public void update()
    {
        setSelected( applicationFrame.isStatusBarVisible() );
    }

    @Override
    public boolean isVisible()
    {
        return true;
    }
}
