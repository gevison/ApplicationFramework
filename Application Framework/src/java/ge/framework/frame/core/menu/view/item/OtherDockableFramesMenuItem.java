package ge.framework.frame.core.menu.view.item;

import com.jidesoft.docking.DockingManager;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.framework.frame.core.dockable.dialog.FramesDialog;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public class OtherDockableFramesMenuItem extends ApplicationFrameMenuItem
{
    private static Resources resources = Resources.getInstance( "ge.framework.frame.core" );

    public OtherDockableFramesMenuItem( ApplicationFrame applicationFrame )
    {
        super(applicationFrame);
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( OtherDockableFramesMenuItem.class, "label" ) );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        FramesDialog framesDialog =
                new FramesDialog( applicationFrame );

        if ( framesDialog.doModal() == true )
        {
            ApplicationDockableFrame frame = framesDialog.getFrame();

            if ( frame.isAutohide() == true )
            {
                DockingManager dockingManager = frame.getDockingManager();

                dockingManager.toggleAutohideState( frame.getKey() );
            }

            frame.showFrame();
        }
    }

    @Override
    public void update()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
