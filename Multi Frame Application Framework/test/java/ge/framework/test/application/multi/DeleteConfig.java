package ge.framework.test.application.multi;

import ge.framework.application.core.Application;
import ge.framework.application.core.objects.ApplicationBean;
import ge.utils.os.OS;
import ge.utils.spring.context.ClasspathApplicationContext;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static ge.utils.log.LoggerEx.fatal;
import static ge.utils.log.LoggerEx.info;
import static ge.utils.spring.context.ClasspathApplicationContext.getInstance;
import static org.apache.commons.lang3.reflect.ConstructorUtils.invokeConstructor;

/**
 * Created by evison_g on 15/10/2013.
 */
public class DeleteConfig
{
    @Test
    public void delete()
    {

        ClasspathApplicationContext classpathApplicationContext = getInstance( "application.xml" );

        ApplicationBean applicationBean;

        String applicationBeanName = null;

        if ( ( applicationBeanName != null ) && ( applicationBeanName.isEmpty() == false ) )
        {
            info( "Getting application bean: " + applicationBeanName );
            applicationBean = classpathApplicationContext.getBean( applicationBeanName, ApplicationBean.class );
        }
        else
        {
            String[] beanNamesForType = classpathApplicationContext.getBeanNamesForType( ApplicationBean.class );

            if ( ( beanNamesForType == null ) || ( beanNamesForType.length == 0 ) )
            {
                fatal( "Failed to find a bean of type Application" );
                throw new IllegalStateException( "Failed to find a bean of type Application" );
            }
            else if ( beanNamesForType.length != 1 )
            {
                fatal( "Found multiple beans of type Application: " + Arrays.toString( beanNamesForType ) );
                throw new IllegalStateException( "Found multiple beans of type Application" );
            }
            else
            {
                info( "Getting application bean: " + beanNamesForType[ 0 ] );
                applicationBean = classpathApplicationContext.getBean( beanNamesForType[ 0 ], ApplicationBean.class );
            }
        }

        if ( OS.isMac() == true )
        {
            System.setProperty( "com.apple.mrj.application.apple.menu.about.name", applicationBean.getDisplayName() );
            System.setProperty( "apple.laf.useScreenMenuBar", "true" );
        }

        Class<? extends Application> applicationClass = applicationBean.getApplicationClass();

        if ( applicationClass != null )
        {
            try
            {
                Application application = invokeConstructor( applicationClass, applicationBean );

                String name = "."+application.getName()+"App";

                File userDirectory = new File( System.getProperty( "user.home" ) );

                File metadataDirectory = new File( userDirectory, name );

                FileUtils.deleteDirectory( metadataDirectory );
            }
            catch ( Exception e )
            {
                fatal( "Failed to create Application object.", e );
            }
        }
    }
}
