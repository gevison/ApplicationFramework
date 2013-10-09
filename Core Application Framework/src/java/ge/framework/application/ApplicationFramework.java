package ge.framework.application;

import ge.framework.application.core.Application;
import ge.framework.application.core.objects.ApplicationBean;
import ge.utils.os.OS;
import ge.utils.spring.context.ClasspathApplicationContext;

import java.util.Arrays;

import static ge.utils.log.LoggerEx.entry;
import static ge.utils.log.LoggerEx.exit;
import static ge.utils.log.LoggerEx.fatal;
import static ge.utils.log.LoggerEx.info;
import static ge.utils.spring.context.ClasspathApplicationContext.getInstance;
import static org.apache.commons.lang3.reflect.ConstructorUtils.invokeConstructor;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/01/13
 * Time: 08:23
 */
public class ApplicationFramework
{
    public static void main( String[] args )
    {
        entry( ( Object ) args );
        ApplicationFramework.start( args );
        exit();
    }

    public static void start( String[] args )
    {
        entry( ( Object ) args );
        start( null, args );
        exit();
    }

    public static void start( String applicationBeanName, String[] args )
    {
        entry( applicationBeanName, args );
        start( "application.xml", applicationBeanName, args );
        exit();
    }

    public static void start( String applicationConfiguration,
                              String applicationBeanName, String[] args )
    {
        entry( applicationConfiguration, applicationBeanName, args );
        ClasspathApplicationContext classpathApplicationContext = getInstance( applicationConfiguration );

        ApplicationBean applicationBean;

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
                application.startup( args );
            }
            catch ( Exception e )
            {
                fatal( "Failed to create Application object.", e );
            }
        }

        exit();
    }
}
