package ge.framework.application.core.utils;

import ge.utils.VMInstance;
import ge.utils.log.LoggerEx;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 19/03/13
 * Time: 13:37
 */
public final class ApplicationRestarter
{
    public static final String MAIN = "main";

    private String[] applicationArgs;

    private String mainClass;

    private ApplicationRestarterCommandDetails commandDetails;

    public ApplicationRestarter( ApplicationRestarterCommandDetails commandDetails )
    {
        this.commandDetails = commandDetails;
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
            LoggerEx.error( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }

        LoggerEx.error( "Failed to initialise the Restarter." );
        throw new IllegalStateException( "Failed to initialise the Restarter." );
    }

    public void restartApplication()
    {
        try
        {
            LoggerEx.info( "Initiating restart" );

            String[] command = commandDetails.getRestartCommand( mainClass, applicationArgs );

            if ( ( command != null ) && ( command.length != 0 ) )
            {
                ProcessBuilder processBuilder = new ProcessBuilder( command );

                LoggerEx.info( "Executing second process" );
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
            LoggerEx.error( e.getMessage(), e );
        }
    }

    public void holdStartup()
    {
        String pidString = System.getProperty( ApplicationRestarterCommandDetails.RESTART_PID );

        if ( ( pidString != null ) && ( pidString.isEmpty() == false ) )
        {
            int pid = Integer.parseInt( pidString );

            while ( VMInstance.isVmRunning( pid ) == true )
            {
                try
                {
                    Thread.sleep( 1000 );
                }
                catch ( InterruptedException e )
                {
                }
            }
        }
    }
}
