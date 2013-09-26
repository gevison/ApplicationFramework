package ge.framework.application.frame.multi.menu.item;

import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.framework.frame.multi.MultiFrameApplicationFrame;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;
import ge.utils.text.StringArgumentMessageFormat;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 16:42
 */
public class OpenRecentMenuItem extends ApplicationFrameMenuItem<MultiFrameApplicationFrame>
{
    protected static final Resources resources =
            Resources.getInstance( "ge.framework.application.frame.multi" );

    private FrameInstanceDetailsObject detailsObject;

    public OpenRecentMenuItem( MultiFrameApplicationFrame applicationFrame, FrameInstanceDetailsObject detailsObject )
    {
        super( applicationFrame );
        this.detailsObject = detailsObject;
    }

    @Override
    protected void initialiseMenuItem()
    {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "instanceName", detailsObject.getName() );

        String label;

        MultiFrameApplication application = ( MultiFrameApplication ) applicationFrame.getApplication();

        if ( application.doesFrameConfigurationFileExist( detailsObject ) == false )
        {
            label = resources.getResourceString( getClass(), "missingLabel" );
        }
        else if ( application.isFrameConfigurationFileLocked( detailsObject ) == true )
        {
            label = resources.getResourceString( getClass(), "lockedLabel" );
            setEnabled( false );
        }
        else
        {
            label = resources.getResourceString( getClass(), "label" );
        }

        String statusLabel = resources.getResourceString( getClass(), "status" );

        label = StringArgumentMessageFormat.format( label, arguments );
        statusLabel = StringArgumentMessageFormat.format( statusLabel, arguments );

        setText( label );
        setStatusBarText( statusLabel );

        setIcon( application.getSmallIcon() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiFrameApplication application = applicationFrame.getApplication();
        application.openFrame( applicationFrame, detailsObject, true );
    }

    @Override
    public void update()
    {

    }
}
