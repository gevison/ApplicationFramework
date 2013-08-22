package ge.framework.application.multi.menu.item;

import ge.framework.application.multi.MultiApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
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
 * Time: 15:49
 */
public class NewMenuItem extends ApplicationFrameMenuItem
{
    protected static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );
    private FrameDefinition frameDefinition;

    private boolean topLevel;

    public NewMenuItem( ApplicationFrame applicationFrame, FrameDefinition frameDefinition, boolean topLevel )
    {
        super( applicationFrame );
        this.frameDefinition = frameDefinition;
        this.topLevel = topLevel;
    }

    @Override
    protected void initialiseMenuItem()
    {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "frameDefinition", frameDefinition.getName() );

        String label;

        if ( topLevel == true )
        {
            label = resources.getResourceString( getClass(), "menuLabel" );
        }
        else
        {
            label = resources.getResourceString( getClass(), "subMenuLabel" );
        }

        label = StringArgumentMessageFormat.format( label, arguments );

        String statusLabel = resources.getResourceString( getClass(), "status" );
        statusLabel = StringArgumentMessageFormat.format( statusLabel, arguments );

        setText( label );
        setStatusBarText( statusLabel );
        setIcon( frameDefinition.getSmallIcon() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiApplication application = ( MultiApplication ) applicationFrame.getApplication();

        application.processNew( applicationFrame, frameDefinition );
    }

    @Override
    public void update()
    {

    }
}
