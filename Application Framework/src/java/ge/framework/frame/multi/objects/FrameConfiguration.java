package ge.framework.frame.multi.objects;

import ge.framework.frame.multi.objects.events.FrameConfigurationChangeListener;
import ge.framework.frame.multi.objects.events.FrameConfigurationChangedEvent;
import ge.utils.properties.object.PropertyDialogObject;

import javax.swing.event.EventListenerList;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/02/13
 * Time: 10:36
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class FrameConfiguration extends PropertyDialogObject
{
    private transient EventListenerList eventListenerList = new EventListenerList();

    private String name;

    private boolean statusBarVisible;

    private boolean toolButtonsVisible;

    public FrameConfiguration()
    {
    }

    public FrameConfiguration( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public boolean isStatusBarVisible()
    {
        return statusBarVisible;
    }

    public void setStatusBarVisible( boolean statusBarVisible )
    {
        this.statusBarVisible = statusBarVisible;
    }

    public boolean isToolButtonsVisible()
    {
        return toolButtonsVisible;
    }

    public void setToolButtonsVisible( boolean toolButtonsVisible )
    {
        this.toolButtonsVisible = toolButtonsVisible;
    }

    public void addFrameConfigurationChangeListener( FrameConfigurationChangeListener listener )
    {
        eventListenerList.add( FrameConfigurationChangeListener.class, listener );
    }

    public void removeFrameConfigurationChangeListener( FrameConfigurationChangeListener listener )
    {
        eventListenerList.add( FrameConfigurationChangeListener.class, listener );
    }

    public void fireFrameConfigurationChangedEvent( String propertyName )
    {
        FrameConfigurationChangeListener[] listeners =
                eventListenerList.getListeners( FrameConfigurationChangeListener.class );

        for ( FrameConfigurationChangeListener listener : listeners )
        {
            FrameConfigurationChangedEvent event = new FrameConfigurationChangedEvent( this, propertyName );
            listener.applicationConfigurationChanged( event );
        }
    }
}
