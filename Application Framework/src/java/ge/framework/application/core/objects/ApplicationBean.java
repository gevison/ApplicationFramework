package ge.framework.application.core.objects;

import ge.utils.os.OS;
import ge.utils.spring.ApplicationContextAwareObject;

import static org.springframework.util.Assert.hasLength;
import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 17/09/2013
 * Time: 08:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class ApplicationBean extends ApplicationContextAwareObject
{
    private Boolean allowMultipleApplications;

    private String resourceBundleName;

    private String nameResource;

    private String descriptionResource;

    private String smallIconResourceName;

    private String smallImageResourceName;

    private String largeIconResourceName;

    private String largeImageResourceName;

    private String macIconResourceName;

    private String macImageResourceName;

    private String applicationClassName;

    private String applicationRestarterClassName;

    private String configurationClassName;

    private String configurationName;

    private String metaDataName;

    private String frameClassName;

    @Override
    protected void validateBeanObject()
    {
        notNull( allowMultipleApplications );

        hasLength( resourceBundleName );

        hasLength( nameResource );
        hasLength( smallImageResourceName );
        hasLength( smallIconResourceName );
        hasLength( largeImageResourceName );
        hasLength( largeIconResourceName );

        if ( OS.isMac() == true )
        {
            hasLength( macImageResourceName );
            hasLength( smallIconResourceName );
        }

        hasLength( metaDataName );
        hasLength( configurationName );
        hasLength( configurationClassName );

        hasLength( frameClassName );

        validateApplicationObject();
    }

    protected abstract void validateApplicationObject();

    public Boolean getAllowMultipleApplications()
    {
        return allowMultipleApplications;
    }

    public void setAllowMultipleApplications( Boolean allowMultipleApplications )
    {
        testInitialised();
        this.allowMultipleApplications = allowMultipleApplications;
    }

    public String getApplicationClassName()
    {
        return applicationClassName;
    }

    public void setApplicationClassName( String applicationClassName )
    {
        testInitialised();
        this.applicationClassName = applicationClassName;
    }

    public String getApplicationRestarterClassName()
    {
        return applicationRestarterClassName;
    }

    public void setApplicationRestarterClassName( String applicationRestarterClassName )
    {
        testInitialised();
        this.applicationRestarterClassName = applicationRestarterClassName;
    }

    public String getConfigurationClassName()
    {
        return configurationClassName;
    }

    public void setConfigurationClassName( String configurationClassName )
    {
        testInitialised();
        this.configurationClassName = configurationClassName;
    }

    public String getConfigurationName()
    {
        return configurationName;
    }

    public void setConfigurationName( String configurationName )
    {
        testInitialised();
        this.configurationName = configurationName;
    }

    public String getDescriptionResource()
    {
        return descriptionResource;
    }

    public void setDescriptionResource( String descriptionResource )
    {
        testInitialised();
        this.descriptionResource = descriptionResource;
    }

    public String getFrameClassName()
    {
        return frameClassName;
    }

    public void setFrameClassName( String frameClassName )
    {
        testInitialised();
        this.frameClassName = frameClassName;
    }

    public String getLargeIconResourceName()
    {
        return largeIconResourceName;
    }

    public void setLargeIconResourceName( String largeIconResourceName )
    {
        testInitialised();
        this.largeIconResourceName = largeIconResourceName;
    }

    public String getLargeImageResourceName()
    {
        return largeImageResourceName;
    }

    public void setLargeImageResourceName( String largeImageResourceName )
    {
        testInitialised();
        this.largeImageResourceName = largeImageResourceName;
    }

    public String getMacIconResourceName()
    {
        return macIconResourceName;
    }

    public void setMacIconResourceName( String macIconResourceName )
    {
        testInitialised();
        this.macIconResourceName = macIconResourceName;
    }

    public String getMacImageResourceName()
    {
        return macImageResourceName;
    }

    public void setMacImageResourceName( String macImageResourceName )
    {
        testInitialised();
        this.macImageResourceName = macImageResourceName;
    }

    public String getMetaDataName()
    {
        return metaDataName;
    }

    public void setMetaDataName( String metaDataName )
    {
        testInitialised();
        this.metaDataName = metaDataName;
    }

    public String getNameResource()
    {
        return nameResource;
    }

    public void setNameResource( String nameResource )
    {
        testInitialised();
        this.nameResource = nameResource;
    }

    public String getResourceBundleName()
    {
        return resourceBundleName;
    }

    public void setResourceBundleName( String resourceBundleName )
    {
        testInitialised();
        this.resourceBundleName = resourceBundleName;
    }

    public String getSmallIconResourceName()
    {
        return smallIconResourceName;
    }

    public void setSmallIconResourceName( String smallIconResourceName )
    {
        testInitialised();
        this.smallIconResourceName = smallIconResourceName;
    }

    public String getSmallImageResourceName()
    {
        return smallImageResourceName;
    }

    public void setSmallImageResourceName( String smallImageResourceName )
    {
        testInitialised();
        this.smallImageResourceName = smallImageResourceName;
    }
}
