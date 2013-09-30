package ge.framework.application.frame.core.objects;

import ge.framework.application.core.configuration.ApplicationConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/09/13
 * Time: 10:50
 */
public class FrameApplicationConfiguration extends ApplicationConfiguration
{
    public static final String ASK_BEFORE_EXIT = "askBeforeExit";

    private boolean askBeforeExit = true;

    public boolean isAskBeforeExit()
    {
        return askBeforeExit;
    }

    public void setAskBeforeExit( boolean askBeforeExit )
    {
        this.askBeforeExit = askBeforeExit;
        fireApplicationConfigurationChangedEvent( ASK_BEFORE_EXIT );
    }
}
