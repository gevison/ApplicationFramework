package ge.framework.frame.multi.menu.file.item;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.framework.frame.multi.objects.FrameDefinition;
import ge.utils.bundle.Resources;
import ge.utils.text.StringArgumentMessageFormat;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 17:20
 */
public class FramePropertiesMenuItem extends ApplicationFrameMenuItem
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.multi" );

    public FramePropertiesMenuItem( ApplicationFrame applicationFrame )
    {
        super(applicationFrame);
    }

    @Override
    protected void initialiseMenuItem()
    {
        String label = resources.getResourceString( FramePropertiesMenuItem.class, "label" );

        MultiApplicationFrame multiApplicationFrame = ( MultiApplicationFrame ) applicationFrame;

        FrameDefinition frameDefinition = multiApplicationFrame.getFrameDefinition();

        Map<String, Object> arguments = new HashMap();
        arguments.put( "frameDefinition", frameDefinition.getName() );

        label = StringArgumentMessageFormat.format( label, arguments );

        setText( label );
        setIcon( resources.getResourceIcon( FramePropertiesMenuItem.class, "icon" ) );
        setMnemonic( resources.getResourceCharacter( FramePropertiesMenuItem.class, "mnemonic" ) );
        setStatusBarText( resources.getResourceString( FramePropertiesMenuItem.class, "status" ) );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiApplicationFrame multiApplicationFrame = ( MultiApplicationFrame ) applicationFrame;

        multiApplicationFrame.processFrameProperties();
    }

    @Override
    public void update()
    {

    }
}
