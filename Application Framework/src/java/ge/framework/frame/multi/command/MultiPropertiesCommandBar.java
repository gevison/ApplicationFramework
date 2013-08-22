package ge.framework.frame.multi.command;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.command.properties.PropertiesCommandBar;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.framework.frame.multi.command.button.FramePropertiesCommandButton;

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
        super(applicationFrame);
    }

    @Override
    protected void initialisePropertiesCommandBar()
    {
        framePropertiesCommandButton = new FramePropertiesCommandButton( ( MultiApplicationFrame ) applicationFrame );

        add( framePropertiesCommandButton );
    }
}
