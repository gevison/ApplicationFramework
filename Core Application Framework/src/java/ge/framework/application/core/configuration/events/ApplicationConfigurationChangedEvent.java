package ge.framework.application.core.configuration.events;

import ge.framework.application.core.configuration.ApplicationConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/02/13
 * Time: 17:56
 */
public class ApplicationConfigurationChangedEvent
{
    private final ApplicationConfiguration applicationConfiguration;

    private final String propertyName;

    public ApplicationConfigurationChangedEvent( ApplicationConfiguration applicationConfiguration,
                                                 String propertyName )
    {
        this.applicationConfiguration = applicationConfiguration;
        this.propertyName = propertyName;
    }

    public ApplicationConfiguration getApplicationConfiguration()
    {
        return applicationConfiguration;
    }

    public String getPropertyName()
    {
        return propertyName;
    }
}
