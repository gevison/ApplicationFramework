package ge.framework.application;

import ge.framework.application.core.Application;
import ge.framework.application.core.objects.ApplicationBean;
import ge.utils.log.LoggerEx;
import ge.utils.os.OS;
import ge.utils.spring.context.ClasspathApplicationContext;
import org.apache.commons.lang3.reflect.ConstructorUtils;

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

        ApplicationBean applicationBean;

        if ( ( applicationBeanName != null ) && ( applicationBeanName.isEmpty() == false ) )
        {
            LoggerEx.info( "Getting application bean: " + applicationBeanName );
            applicationBean = classpathApplicationContext.getBean( applicationBeanName, ApplicationBean.class );
        }
        else
        {
            String[] beanNamesForType = classpathApplicationContext.getBeanNamesForType( ApplicationBean.class );

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
                applicationBean = classpathApplicationContext.getBean( beanNamesForType[ 0 ], ApplicationBean.class );
            }
        }

        if ( OS.isMac() == true )
        {
            System.setProperty( "com.apple.mrj.application.apple.menu.about.name", applicationBean.getName() );
        }

        Class<? extends Application> applicationClass = applicationBean.getApplicationClass();

        if ( applicationClass != null )
        {
            try
            {
                Application application = ConstructorUtils.invokeConstructor( applicationClass, applicationBean );
                application.startup( args );
            }
            catch ( Exception e )
            {
                LoggerEx.fatal( "Failed to creat Application object.", e );
            }
        }

        LoggerEx.exit();
    }
}
