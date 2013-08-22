package ge.framework.application;

import com.jidesoft.plaf.LookAndFeelFactory;
import ge.framework.application.core.Application;
import ge.framework.application.core.utils.ApplicationRestarter;
import ge.utils.VMInstance;
import ge.utils.os.OS;
import ge.utils.spring.context.ClasspathApplicationContext;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/01/13
 * Time: 08:23
 */
public class ApplicationFramework
{
    private static Logger logger = Logger.getLogger( ApplicationFramework.class );

    public static void main( String[] args )
    {
        ApplicationFramework.start( args );
    }

    public static void start( String[] args )
    {
        start( null, args );
    }

    public static void start( String applicationBeanName, String[] args )
    {
        start( "application.xml", applicationBeanName, args );
    }

    public static void start( String applicationConfiguration,
                              String applicationBeanName, String[] args )
    {
        ClasspathApplicationContext classpathApplicationContext = ClasspathApplicationContext.getInstance(
                applicationConfiguration );

        Application application;

        if ( ( applicationBeanName != null ) && ( applicationBeanName.isEmpty() == false ) )
        {
            logger.info("Getting application bean: "+applicationBeanName);
            application = classpathApplicationContext.getBean( applicationBeanName, Application.class );
        }
        else
        {
            String[] beanNamesForType = classpathApplicationContext.getBeanNamesForType( Application.class );

            if ( ( beanNamesForType == null ) || ( beanNamesForType.length == 0 ) )
            {
                logger.fatal( "Failed to find a bean of type Application" );
                throw new IllegalStateException( "Failed to find a bean of type Application" );
            }
            else if ( beanNamesForType.length != 1 )
            {
                logger.fatal( "Found multiple beans of type Application: " + Arrays.toString( beanNamesForType ) );
                throw new IllegalStateException( "Found multiple beans of type Application" );
            }
            else
            {
                logger.info( "Getting application bean: " + beanNamesForType[ 0 ] );
                application = classpathApplicationContext.getBean( beanNamesForType[ 0 ], Application.class );
            }
        }

        application.startup( args );
    }
}
