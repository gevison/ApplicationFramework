package ge.framework.application.core.objects;

import ge.framework.application.core.Application;
import ge.utils.log.LoggerEx;
import ge.utils.spring.ApplicationContextAwareObject;
import org.apache.commons.lang3.ClassUtils;

import static org.springframework.util.Assert.hasLength;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 17/09/2013
 * Time: 08:41
 * To change this template use File | Settings | File Templates.
 */
public final class ApplicationBean extends ApplicationContextAwareObject
{
    private String name;

    private String description;

    private String applicationClassName;

    @Override
    protected void validateBeanObject()
    {
        hasLength( name );

        hasLength( applicationClassName );
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        testInitialised();
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        testInitialised();
        this.description = description;
    }

    public void setApplicationClassName( String applicationClassName )
    {
        testInitialised();
        this.applicationClassName = applicationClassName;
    }

    public Class<? extends Application> getApplicationClass()
    {
        try
        {
            return ( Class<? extends Application> ) ClassUtils.getClass( applicationClassName );
        }
        catch ( Exception e )
        {
            LoggerEx.fatal( "Failed to find Application class: " + applicationClassName, e );
            return null;
        }
    }
}
