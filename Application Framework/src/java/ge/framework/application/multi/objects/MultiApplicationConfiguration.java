package ge.framework.application.multi.objects;

import ge.framework.application.core.objects.ApplicationConfiguration;
import ge.framework.application.multi.objects.enums.OpenLocationEnum;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/01/13
 * Time: 09:18
 */
@XmlAccessorType( XmlAccessType.FIELD )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
public abstract class MultiApplicationConfiguration extends ApplicationConfiguration
{
    private boolean reOpenLast = false;

    private boolean exitOnFinalWindowClose = false;

    private List<FrameInstanceDetailsObject> open = new ArrayList<FrameInstanceDetailsObject>();

    private List<FrameInstanceDetailsObject> recent = new ArrayList<FrameInstanceDetailsObject>();

    private OpenLocationEnum openLocation = OpenLocationEnum.ASK;

    private int allowedRecentlyOpenedCount = 10;

    public boolean isReOpenLast()
    {
        return reOpenLast;
    }

    public void setReOpenLast( boolean reOpenLast )
    {
        this.reOpenLast = reOpenLast;
    }

    public boolean isExitOnFinalWindowClose()
    {
        return exitOnFinalWindowClose;
    }

    public void setExitOnFinalWindowClose( boolean exitOnFinalWindowClose )
    {
        this.exitOnFinalWindowClose = exitOnFinalWindowClose;
    }

    public List<FrameInstanceDetailsObject> getRecent()
    {
        return recent;
    }

    public void setRecent( List<FrameInstanceDetailsObject> recent )
    {
        this.recent = recent;
    }

    public void addRecent( FrameInstanceDetailsObject projectDetailsObject )
    {
        if ( recent.contains( projectDetailsObject ) == true )
        {
            recent.remove( projectDetailsObject );
        }

        recent.add( 0, projectDetailsObject );

        if ( recent.size() > allowedRecentlyOpenedCount )
        {
            recent = recent.subList( 0, allowedRecentlyOpenedCount );
        }
    }

    public void clearRecent()
    {
        recent.clear();
    }

    public boolean addOpen( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        if ( open.contains( frameInstanceDetailsObject ) == false )
        {
            return open.add( frameInstanceDetailsObject );
        }

        return false;
    }

    public List<FrameInstanceDetailsObject> getOpen()
    {
        return open;
    }

    public boolean removeOpen(
            FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        return open.remove( frameInstanceDetailsObject );
    }

    public OpenLocationEnum getOpenLocation()
    {
        return openLocation;
    }

    public void setOpenLocation( OpenLocationEnum openLocation )
    {
        this.openLocation = openLocation;
        fireApplicationConfigurationChangedEvent( "openLocation" );
    }

    public int getAllowedRecentlyOpenedCount()
    {
        return allowedRecentlyOpenedCount;
    }

    public void setAllowedRecentlyOpenedCount( int allowedRecentlyOpenedCount )
    {
        this.allowedRecentlyOpenedCount = allowedRecentlyOpenedCount;
        fireApplicationConfigurationChangedEvent( "allowedRecentlyOpenedCount" );
    }
}
