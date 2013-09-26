package ge.framework.frame.core.menu.view.item;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationDockableFrameMenuItem extends ApplicationFrameMenuItem
{
    private ApplicationDockableFrame applicationDockableFrame;

    public ApplicationDockableFrameMenuItem( ApplicationFrame applicationFrame,
                                             ApplicationDockableFrame applicationDockableFrame )
    {
        super( applicationFrame );
        this.applicationDockableFrame = applicationDockableFrame;
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( applicationDockableFrame.getMenuTitle() );
        setIcon( applicationDockableFrame.getMenuIcon() );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        applicationDockableFrame.showFrame();
    }

    @Override
    public void update()
    {
    }
}
