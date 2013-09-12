package ge.framework.application;

import ge.framework.application.core.Application;
import ge.utils.log.LoggerEx;
import ge.utils.spring.context.ClasspathApplicationContext;

import java.util.Arrays;

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
        LoggerEx.entry( args );
        ApplicationFramework.start( args );
        LoggerEx.exit();
    }

    public static void start( String[] args )
    {
        LoggerEx.entry( args );
        start( null, args );
        LoggerEx.exit();
    }

    public static void start( String applicationBeanName, String[] args )
    {
        LoggerEx.entry( applicationBeanName, args );
        start( "application.xml", applicationBeanName, args );
        LoggerEx.exit();
    }

    public static void start( String applicationConfiguration,
                              String applicationBeanName, String[] args )
    {
        LoggerEx.entry( applicationConfiguration, applicationBeanName, args );
        ClasspathApplicationContext classpathApplicationContext = ClasspathApplicationContext.getInstance(
                applicationConfiguration );

        Application application;

        if ( ( applicationBeanName != null ) && ( applicationBeanName.isEmpty() == false ) )
        {
            LoggerEx.info( "Getting application bean: " + applicationBeanName );
            application = classpathApplicationContext.getBean( applicationBeanName, Application.class );
        }
        else
        {
            String[] beanNamesForType = classpathApplicationContext.getBeanNamesForType( Application.class );

            if ( ( beanNamesForType == null ) || ( beanNamesForType.length == 0 ) )
            {
                LoggerEx.fatal( "Failed to find a bean of type Application" );
                throw new IllegalStateException( "Failed to find a bean of type Application" );
            }
            else if ( beanNamesForType.length != 1 )
            {
                LoggerEx.fatal( "Found multiple beans of type Application: " + Arrays.toString( beanNamesForType ) );
                throw new IllegalStateException( "Found multiple beans of type Application" );
            }
            else
            {
                LoggerEx.info( "Getting application bean: " + beanNamesForType[ 0 ] );
                application = classpathApplicationContext.getBean( beanNamesForType[ 0 ], Application.class );
            }
        }

        application.startup( args );
        LoggerEx.exit();
    }
}
