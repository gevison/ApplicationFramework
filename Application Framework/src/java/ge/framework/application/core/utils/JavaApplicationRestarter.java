package ge.framework.application.core.utils;

import ge.utils.VMDetails;
import org.apache.log4j.Logger;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 19/03/13
 * Time: 13:37
 */
public class JavaApplicationRestarter extends ApplicationRestarter
{
    private static Logger logger = Logger.getLogger( JavaApplicationRestarter.class );

    public static final String RESTART_PID = "ge.framework.application.restartPID";

    private static File javaHome = new File( System.getProperty( "java.home" ) );

    private static File javaBin = new File( javaHome, "bin" );

    private static File javaExe = new File( javaBin, "java.exe" );

    private static File workingDir = new File( System.getProperty( "user.dir" ) );

    private static File outDir = new File( workingDir, "out" );

    private static File libDir = new File( outDir, "lib" );

    private static File applicationJar = new File( libDir, "Application.jar" );

    private static File applicationFrameworkJar = new File( libDir, "ApplicationFramework.jar" );

    private static File coreJar = new File( libDir, "Core.jar" );

    private static File loggerJar = new File( libDir, "Logger.jar" );

    private static File modelTreeJar = new File( libDir, "ModelTree.jar" );

    private static File utilsJar = new File( libDir, "Utils.jar" );

    private static File productionDir = new File( outDir, "production" );

    private static File applicationDir = new File( productionDir, "Application" );

    private static File applicationFrameworkDir = new File( productionDir, "Application Framework" );

    private static File coreDir = new File( productionDir, "Core" );

    private static File loggerDir = new File( productionDir, "Logger" );

    private static File modelTreeDir = new File( productionDir, "Model Tree" );

    private static File utilsDir = new File( productionDir, "Utils" );

    private static Map<File, File> dirToJarMap;

    public JavaApplicationRestarter()
    {
    }

    static
    {
        dirToJarMap = new HashMap<File, File>();
        dirToJarMap.put( applicationDir, applicationJar );
        dirToJarMap.put( applicationFrameworkDir, applicationFrameworkJar );
        dirToJarMap.put( coreDir, coreJar );
        dirToJarMap.put( loggerDir, loggerJar );
        dirToJarMap.put( modelTreeDir, modelTreeJar );
        dirToJarMap.put( utilsDir, utilsJar );
    }

    public String[] getRestartCommand( String mainClass, String[] applicationArguments )
    {
        VMDetails thisVmDetails = VMDetails.getThisVmDetails();

        List<String> command = new ArrayList<String>();

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        List<String> inputArguments = runtimeMXBean.getInputArguments();

        String thisClassPath = runtimeMXBean.getClassPath();

        logger.debug( "Java exe: " + javaExe.getPath() );

        command.add( "\"" + javaExe.getPath() + "\"" );

        for ( String inputArgument : inputArguments )
        {
            if ( ( inputArgument.startsWith( "-D" + RESTART_PID ) == false ) &&
                 ( inputArgument.startsWith( "-agentlib" ) == false ) )
            {
                logger.debug( "Added input argument: " + inputArgument );
                command.add( inputArgument );
            }
        }

        String argument = "-D" + RESTART_PID + "=" + thisVmDetails.getPid();
        logger.debug( "Added argument: " + argument );
        command.add( argument );

        if ( ( thisClassPath != null ) && ( thisClassPath.isEmpty() == false ) )
        {
            String classPath = "";
            String[] thisClassPathArray = thisClassPath.split( File.pathSeparator );

            for ( String thisClassPathArrayElement : thisClassPathArray )
            {
                File classPathElementFile = new File( thisClassPathArrayElement );

                if ( dirToJarMap.containsKey( classPathElementFile ) == true )
                {
                    logger.debug( "Dir substitution: " + classPathElementFile.getPath() );
                    classPathElementFile = dirToJarMap.get( classPathElementFile );
                    logger.debug( "Jar substituted: " + classPathElementFile.getPath() );
                }

                logger.debug( "Adding classpath value: " + classPathElementFile.getPath() );
                classPath += classPathElementFile.getPath() + File.pathSeparator;
            }

            logger.debug( "Setting classpath: " + classPath );
            command.add( "-cp" );
            command.add( classPath );
        }

        logger.debug( "Main Class: " + mainClass );
        command.add( mainClass );

        if ( ( applicationArguments != null ) && ( applicationArguments.length != 0 ) )
        {
            for ( String applicationArg : applicationArguments )
            {
                logger.debug( "Application Argument: " + applicationArg );
                command.add( applicationArg );
            }
        }

        return command.toArray( new String[ command.size() ] );
    }
}
