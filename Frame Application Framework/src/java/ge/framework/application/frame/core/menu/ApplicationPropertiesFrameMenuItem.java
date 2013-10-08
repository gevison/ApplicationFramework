package ge.framework.application.frame.core.menu;

import ge.framework.application.frame.core.FrameApplication;
import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenuItem;
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
            Resources.getInstance( "ge.framework.application.frame.core" );

    public ApplicationPropertiesFrameMenuItem( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        FrameApplication application = applicationFrame.getApplication();
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
