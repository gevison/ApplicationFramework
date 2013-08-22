package ge.framework.application.single.objects;

import ge.framework.application.core.objects.ApplicationConfiguration;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 17:05
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class SingleApplicationConfiguration extends ApplicationConfiguration
{
    private boolean statusBarVisible = true;

    private boolean toolButtonsVisible = true;

    public boolean isStatusBarVisible()
    {
        return statusBarVisible;
    }

    public void setStatusBarVisible( boolean statusBarVisible )
    {
        this.statusBarVisible = statusBarVisible;
        fireApplicationConfigurationChangedEvent( "statusBarVisible" );
    }

    public boolean isToolButtonsVisible()
    {
        return toolButtonsVisible;
    }

    public void setToolButtonsVisible( boolean toolButtonsVisible )
    {
        this.toolButtonsVisible = toolButtonsVisible;
        fireApplicationConfigurationChangedEvent( "toolButtonsVisible" );
    }
}
