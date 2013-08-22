package ge.framework.application.core.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 19/03/13
 * Time: 13:37
 */
public abstract class ApplicationRestarter
{
    public static final String MAIN = "main";

    private static Logger logger = Logger.getLogger( ApplicationRestarter.class );

    public static final String RESTART_PID = "ge.framework.application.restartPID";

    private String[] applicationArgs;

    private String mainClass;

    protected ApplicationRestarter()
    {
    }

    public void initialiseRestarter( String[] applicationArgs )
    {
        this.applicationArgs = applicationArgs.clone();

        try
        {
            Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();

            for ( Map.Entry<Thread, StackTraceElement[]> threadEntry : allStackTraces.entrySet() )
            {
                Thread thread = threadEntry.getKey();

                if ( "main".equals( thread.getName() ) == true )
                {
                    StackTraceElement[] stackTraceElements = threadEntry.getValue();

                    StackTraceElement stackTraceElement = stackTraceElements[ stackTraceElements.length - 1 ];

                    String methodName = stackTraceElement.getMethodName();

                    if ( MAIN.equals( methodName ) == true )
                    {
                        mainClass = stackTraceElement.getClassName();
                        return;
                    }
                }
            }
        }
        catch ( Exception e )
        {
            logger.error( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }

        logger.error( "Failed to initialise the Restarter." );
        throw new IllegalStateException( "Failed to initialise the Restarter." );
    }

    public void restartApplication(  )
    {
        try
        {
            logger.info( "Initiating restart" );

            String[] command = getRestartCommand( mainClass, applicationArgs );

            if ( ( command != null ) && ( command.length != 0 ) )
            {
                ProcessBuilder processBuilder = new ProcessBuilder( command );

                logger.info( "Executing second process" );
                processBuilder.start();
                try
                {
                    Thread.sleep( 1000 );
                }
                catch ( InterruptedException e )
                {
                }
            }
        }
        catch ( Exception e )
        {
            logger.error( e.getMessage(), e );
        }
    }

    public abstract String[] getRestartCommand( String mainClass, String[] applicationArguments );
}
