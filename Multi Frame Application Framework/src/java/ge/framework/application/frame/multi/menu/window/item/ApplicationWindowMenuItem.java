package ge.framework.application.frame.multi.menu.window.item;

import ge.framework.application.frame.multi.MultiFrameApplicationFrame;
import ge.framework.application.frame.multi.objects.FrameInstanceDetailsObject;
import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameCheckboxMenuItem;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 04/08/2013
 * Time: 15:39
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationWindowMenuItem extends ApplicationFrameCheckboxMenuItem
{
    private MultiFrameApplicationFrame target;

    public ApplicationWindowMenuItem( ApplicationFrame applicationFrame, MultiFrameApplicationFrame target )
    {
        super( applicationFrame );
        this.target = target;
    }

    @Override
    protected void initialiseMenuItem()
    {
        FrameInstanceDetailsObject frameInstanceDetailsObject = target.getFrameInstanceDetailsObject();
        setText( frameInstanceDetailsObject.getName() );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        target.setVisible( true );
    }

    @Override
    public void update()
    {
        if ( applicationFrame == target )
        {
            setSelected( true );
        }
        else
        {
            setSelected( false );
        }
    }
}
