package ge.framework.application.apple;

import com.apple.eawt.AppEvent;
import com.apple.eawt.PreferencesHandler;
import ge.framework.application.core.Application;

/**
 * Created by evison_g on 29/10/2013.
 */
public class ApplePreferencesHandler implements PreferencesHandler
{
    private Application application;

    public ApplePreferencesHandler( Application application )
    {
        this.application = application;
    }

    @Override
    public void handlePreferences( AppEvent.PreferencesEvent preferencesEvent )
    {
        application.processApplicationProperties();
    }
}
