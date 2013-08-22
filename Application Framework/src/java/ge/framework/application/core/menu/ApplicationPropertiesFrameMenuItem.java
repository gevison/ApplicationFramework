package ge.framework.application.core.menu;

import ge.framework.application.core.Application;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/02/13
 * Time: 17:53
 */
public class ApplicationPropertiesFrameMenuItem extends ApplicationFrameMenuItem implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.core" );

    public ApplicationPropertiesFrameMenuItem( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Application application = applicationFrame.getApplication();
        application.processApplicationProperties();
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( ApplicationPropertiesFrameMenuItem.class, "label" ) );
        setStatusBarText( resources.getResourceString( ApplicationPropertiesFrameMenuItem.class, "status" ) );
        setMnemonic( resources.getResourceCharacter( ApplicationPropertiesFrameMenuItem.class, "mnemonic" ) );
        setIcon( resources.getResourceIcon( ApplicationPropertiesFrameMenuItem.class, "icon" ) );
    }

    @Override
    public void update()
    {
    }
}
