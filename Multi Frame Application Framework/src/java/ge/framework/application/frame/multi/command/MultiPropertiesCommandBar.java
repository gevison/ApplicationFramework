package ge.framework.application.frame.multi.command;

import ge.framework.application.frame.multi.MultiFrameApplicationFrame;
import ge.framework.application.frame.multi.command.button.FramePropertiesCommandButton;
import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.command.properties.PropertiesCommandBar;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 15:35
 */
public class MultiPropertiesCommandBar extends PropertiesCommandBar
{
    private FramePropertiesCommandButton framePropertiesCommandButton;

    public MultiPropertiesCommandBar( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialisePropertiesCommandBar()
    {
        framePropertiesCommandButton =
                new FramePropertiesCommandButton( ( MultiFrameApplicationFrame ) applicationFrame );

        add( framePropertiesCommandButton );
    }
}
