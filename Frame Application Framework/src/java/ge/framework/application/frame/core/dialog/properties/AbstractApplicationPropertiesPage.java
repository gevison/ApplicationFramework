package ge.framework.application.frame.core.dialog.properties;

import ge.framework.application.core.configuration.ApplicationConfiguration;
import ge.utils.properties.PropertiesDialogPage;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/02/13
 * Time: 18:50
 */
public abstract class AbstractApplicationPropertiesPage extends PropertiesDialogPage<ApplicationConfiguration>
{
    protected AbstractApplicationPropertiesPage( String id )
    {
        super( id );
    }
}
