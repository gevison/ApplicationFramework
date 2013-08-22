package ge.framework.frame.multi.objects;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/01/13
 * Time: 11:31
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class FrameInstanceDetailsObject
{
    private String name;

    private File location;

    private FrameDefinition frameDefinition;

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public File getLocation()
    {
        return location;
    }

    public void setLocation( File location )
    {
        this.location = location;
    }

    public void setFrameDefinition( FrameDefinition frameDefinition )
    {
        this.frameDefinition = frameDefinition;
    }

    public FrameDefinition getFrameDefinition()
    {
        return frameDefinition;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof FrameInstanceDetailsObject ) )
        {
            return false;
        }

        FrameInstanceDetailsObject that = ( FrameInstanceDetailsObject ) o;

        if ( frameDefinition != null ? !frameDefinition.equals( that.frameDefinition ) : that.frameDefinition != null )
        {
            return false;
        }
        if ( location != null ? !location.equals( that.location ) : that.location != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + ( frameDefinition != null ? frameDefinition.hashCode() : 0 );
        return result;
    }

    public boolean doesConfigurationFileExist()
    {
        return frameDefinition.doesConfigurationFileExist( location );
    }

    public boolean isConfigurationFileLocked()
    {
        return frameDefinition.isConfigurationFileLocked( location );
    }

    public boolean isDirectory()
    {
        return frameDefinition.isDirectory( location );
    }

    public File getConfigurationFile()
    {
        return frameDefinition.getConfigurationFile( location );
    }

    public File getMetadataDirectory()
    {
        return frameDefinition.getMetadataDirectory( location );
    }
}
