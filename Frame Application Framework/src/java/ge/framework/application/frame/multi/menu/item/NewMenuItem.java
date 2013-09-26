package ge.framework.application.frame.multi.menu.item;

import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.framework.frame.multi.MultiFrameApplicationFrame;
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
public class NewMenuItem extends ApplicationFrameMenuItem<MultiFrameApplicationFrame>
{
    protected static final Resources resources =
            Resources.getInstance( "ge.framework.application.frame.multi" );

    private boolean topLevel;

    public NewMenuItem( MultiFrameApplicationFrame applicationFrame, boolean topLevel )
    {
        super( applicationFrame );
        this.topLevel = topLevel;
    }

    @Override
    protected void initialiseMenuItem()
    {
        MultiFrameApplication application = applicationFrame.getApplication();
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "frameDefinition", application.getFrameName() );

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
        setIcon( application.getSmallIcon() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiFrameApplication application = applicationFrame.getApplication();

        application.processNew( applicationFrame );
    }

    @Override
    public void update()
    {

    }
}
