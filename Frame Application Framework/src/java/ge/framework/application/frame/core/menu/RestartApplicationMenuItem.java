package ge.framework.application.frame.core.menu;

import ge.framework.application.core.Application;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.utils.bundle.Resources;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/01/13
 * Time: 11:23
 */
public class RestartApplicationMenuItem extends ApplicationFrameMenuItem
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.core" );

    public RestartApplicationMenuItem( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( RestartApplicationMenuItem.class, "label" ) );
        setStatusBarText( resources.getResourceString( RestartApplicationMenuItem.class, "status" ) );

        GraphicsConfiguration graphicsConfiguration = applicationFrame.getGraphicsConfiguration();

        GraphicsDevice device = graphicsConfiguration.getDevice();

        setEnabled( device.isFullScreenSupported() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Application application = applicationFrame.getApplication();
        application.processRestart();

//        applicationFrame.setUndecorated( true );
//        applicationFrame.setExtendedState( JFrame.MAXIMIZED_BOTH );
//        applicationFrame.toFront();


//        GraphicsConfiguration graphicsConfiguration = applicationFrame.getGraphicsConfiguration();
//
//        GraphicsDevice device = graphicsConfiguration.getDevice();
//
//        try
//        {
//            device.setFullScreenWindow( applicationFrame );
//        }
//        finally
//        {
//            device.setFullScreenWindow( null );
//        }
    }

    @Override
    public void update()
    {
    }
}
