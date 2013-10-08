package ge.framework.application.frame.multi.dialog.properties;

import ge.framework.application.frame.multi.objects.FrameConfiguration;
import ge.utils.properties.PropertiesDialogPage;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/02/13
 * Time: 18:50
 */
public abstract class AbstractFramePropertiesPage extends PropertiesDialogPage<FrameConfiguration>
{
    protected AbstractFramePropertiesPage( String id )
    {
        super( id );
    }
}
