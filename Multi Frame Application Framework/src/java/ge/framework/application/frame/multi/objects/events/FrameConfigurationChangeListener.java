package ge.framework.application.frame.multi.objects.events;

import java.util.EventListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/02/13
 * Time: 17:51
 */
public interface FrameConfigurationChangeListener extends EventListener
{
    public void applicationConfigurationChanged( FrameConfigurationChangedEvent event );
}
