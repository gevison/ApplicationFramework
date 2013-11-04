package ge.framework.application.apple;

import com.apple.eawt.AppEvent;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;
import ge.framework.application.core.Application;
import ge.utils.log.LoggerEx;

/**
 * Created by evison_g on 29/10/2013.
 */
public class AppleQuitHandler implements QuitHandler
{
    private Application application;

    public AppleQuitHandler( Application application )
    {
        this.application = application;
    }

    @Override
    public void handleQuitRequestWith( AppEvent.QuitEvent quitEvent, QuitResponse quitResponse )
    {
        LoggerEx.info( "Apple Quit Handler fired." );
        application.processExit();

        quitResponse.cancelQuit();
    }
}
