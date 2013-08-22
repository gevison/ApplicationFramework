package ge.framework.frame.multi.objects;

import ge.framework.frame.core.ApplicationFrame;
import ge.utils.file.LockFile;
import ge.utils.os.OS;
import ge.utils.spring.ApplicationContextAwareObject;

import javax.swing.*;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 07/03/13
 * Time: 11:18
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class FrameDefinition extends ApplicationContextAwareObject
{
    private transient Class<? extends ApplicationFrame> frameClass;

    private transient Class<? extends FrameConfiguration> configurationClass;

    private transient String metaDataName;

    private transient String configurationName;

    private transient String name;

    private transient Image smallImage;

    private transient Icon smallIcon;

    private transient Image largeImage;

    private transient Icon largeIcon;

    private transient Image macImage;

    private transient Icon macIcon;

    @Override
    protected void validateBeanObject()
    {
        if ( configurationClass == null )
        {
            throw new IllegalStateException( "configurationClass cannot be null" );
        }

        if ( ( metaDataName == null ) || ( metaDataName.isEmpty() == true ) )
        {
            throw new IllegalStateException( "metaDataName cannot be null or empty." );
        }

        if ( ( configurationName == null ) || ( configurationName.isEmpty() == true ) )
        {
            throw new IllegalStateException( "configurationName cannot be null or empty." );
        }

        if ( ( name == null ) || ( name.isEmpty() == true ) )
        {
            throw new IllegalStateException( "name cannot be null or empty." );
        }

        if ( smallImage == null )
        {
            throw new IllegalStateException( "smallImage cannot be null" );
        }

        if ( smallIcon == null )
        {
            throw new IllegalStateException( "smallIcon cannot be null" );
        }

        if ( largeImage == null )
        {
            throw new IllegalStateException( "largeImage cannot be null" );
        }

        if ( largeIcon == null )
        {
            throw new IllegalStateException( "largeIcon cannot be null" );
        }

        if ( OS.isMac() == true )
        {
            if ( macImage == null )
            {
                throw new IllegalStateException( "macImage cannot be null" );
            }

            if ( macIcon == null )
            {
                throw new IllegalStateException( "macIcon cannot be null" );
            }
        }
    }

    public Class<? extends FrameConfiguration> getConfigurationClass()
    {
        return configurationClass;
    }

    public void setConfigurationClass( Class<? extends FrameConfiguration> configurationClass )
            throws IllegalAccessException
    {
        testInitialised();
        this.configurationClass = configurationClass;
    }

    public String getConfigurationName()
    {
        return configurationName;
    }

    public void setConfigurationName( String configurationName ) throws IllegalAccessException
    {
        testInitialised();
        this.configurationName = configurationName;
    }

    public Class<? extends ApplicationFrame> getFrameClass()
    {
        return frameClass;
    }

    public void setFrameClass( Class<? extends ApplicationFrame> frameClass ) throws IllegalAccessException
    {
        testInitialised();
        this.frameClass = frameClass;
    }

    public Icon getLargeIcon()
    {
        return largeIcon;
    }

    public void setLargeIcon( Icon largeIcon ) throws IllegalAccessException
    {
        testInitialised();
        this.largeIcon = largeIcon;
    }

    public Image getLargeImage()
    {
        return largeImage;
    }

    public void setLargeImage( Image largeImage ) throws IllegalAccessException
    {
        testInitialised();
        this.largeImage = largeImage;
    }

    public Icon getMacIcon()
    {
        return macIcon;
    }

    public void setMacIcon( Icon macIcon ) throws IllegalAccessException
    {
        testInitialised();
        this.macIcon = macIcon;
    }

    public Image getMacImage()
    {
        return macImage;
    }

    public void setMacImage( Image macImage ) throws IllegalAccessException
    {
        testInitialised();
        this.macImage = macImage;
    }

    public String getMetaDataName()
    {
        return metaDataName;
    }

    public void setMetaDataName( String metaDataName ) throws IllegalAccessException
    {
        testInitialised();
        this.metaDataName = metaDataName;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name ) throws IllegalAccessException
    {
        testInitialised();
        this.name = name;
    }

    public Icon getSmallIcon()
    {
        return smallIcon;
    }

    public void setSmallIcon( Icon smallIcon ) throws IllegalAccessException
    {
        testInitialised();
        this.smallIcon = smallIcon;
    }

    public Image getSmallImage()
    {
        return smallImage;
    }

    public void setSmallImage( Image smallImage ) throws IllegalAccessException
    {
        testInitialised();
        this.smallImage = smallImage;
    }

    public File getMetadataDirectory( File location )
    {
        return new File( location, metaDataName );
    }

    public File getConfigurationFile( File location )
    {
        return new File( getMetadataDirectory( location ), configurationName );
    }

    public boolean isDirectory( File location )
    {
        File typeConfigurationFile = getConfigurationFile( location );

        return typeConfigurationFile.exists();
    }

    public boolean doesConfigurationFileExist( File location )
    {
        File typeConfigurationFile = getConfigurationFile( location );
        return typeConfigurationFile.exists();
    }

    public boolean isConfigurationFileLocked( File location )
    {
        try
        {
            File typeConfigurationFile = getConfigurationFile( location );
            return LockFile.isFileLocked( typeConfigurationFile );
        }
        catch ( IOException e )
        {
            return false;
        }
    }
}
