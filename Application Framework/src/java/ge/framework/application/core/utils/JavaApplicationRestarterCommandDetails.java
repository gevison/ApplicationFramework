package ge.framework.application.core.utils;

import ge.utils.VMDetails;
import ge.utils.log.LoggerEx;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 19/03/13
 * Time: 13:37
 */
public class JavaApplicationRestarterCommandDetails implements ApplicationRestarterCommandDetails
{
    public static final String RESTART_PID = "ge.framework.application.restartPID";

    private static File javaHome = new File( System.getProperty( "java.home" ) );

    private static File javaBin = new File( javaHome, "bin" );

    private static File javaExe = new File( javaBin, "java.exe" );

    @Override
    public String[] getRestartCommand( String mainClass, String[] applicationArguments )
    {
        VMDetails thisVmDetails = VMDetails.getThisVmDetails();

        List<String> command = new ArrayList<String>();

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        List<String> inputArguments = runtimeMXBean.getInputArguments();

        LoggerEx.debug( "Java exe: " + javaExe.getPath() );

        command.add( "\"" + javaExe.getPath() + "\"" );

        for ( String inputArgument : inputArguments )
        {
            if ( ( inputArgument.startsWith( "-D" + RESTART_PID ) == false ) &&
                    ( inputArgument.startsWith( "-agentlib" ) == false ) )
            {
                LoggerEx.debug( "Added input argument: " + inputArgument );
                command.add( inputArgument );
            }
        }

        String argument = "-D" + RESTART_PID + "=" + thisVmDetails.getPid();
        LoggerEx.debug( "Added argument: " + argument );
        command.add( argument );

        String classPath = runtimeMXBean.getClassPath();

        if ( ( classPath != null ) && ( classPath.isEmpty() == false ) )
        {
            LoggerEx.debug( "Setting classpath: " + classPath );
            command.add( "-cp" );
            command.add( classPath );
        }

        LoggerEx.debug( "Main Class: " + mainClass );
        command.add( mainClass );

        if ( ( applicationArguments != null ) && ( applicationArguments.length != 0 ) )
        {
            for ( String applicationArg : applicationArguments )
            {
                LoggerEx.debug( "Application Argument: " + applicationArg );
                command.add( applicationArg );
            }
        }

        return command.toArray( new String[ command.size() ] );
    }
}
