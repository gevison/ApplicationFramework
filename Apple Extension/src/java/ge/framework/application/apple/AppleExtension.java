package ge.framework.application.apple;

import com.apple.eawt.Application;
import ge.framework.application.visible.VisibleApplication;

/**
 * Created by evison_g on 29/10/2013.
 */
public class AppleExtension
{
    private static ge.framework.application.core.Application application;

    public static void initialiseAppleApplication(ge.framework.application.core.Application application)
    {
        if ( AppleExtension.application != null )
        {
            throw new IllegalStateException( "Initialisation of apple components has been done already" );
        }

        AppleExtension.application = application;

        Application appleApplication = Application.getApplication();

        appleApplication.setQuitHandler( new AppleQuitHandler(application) );
        appleApplication.setPreferencesHandler( new ApplePreferencesHandler(application) );

        if ( application instanceof VisibleApplication )
        {
            VisibleApplication visibleApplication = ( VisibleApplication ) application;
            appleApplication.setDockIconImage( visibleApplication.getMacImage() );
        }
    }
}
