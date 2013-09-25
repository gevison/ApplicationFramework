package ge.framework.application.core.utils;


import ge.utils.VMDetails;
import ge.utils.log.LoggerEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 19/03/13
 * Time: 13:37
 */
public class Install4jApplicationRestarterCommandDetails implements ApplicationRestarterCommandDetails
{
    @Override
    public String[] getRestartCommand( String mainClass, String[] applicationArguments )
    {
        try
        {
            LoggerEx.info( "Initiating restart" );

            VMDetails thisVmDetails = VMDetails.getThisVmDetails();
            String vmName = thisVmDetails.getVmName();

            List<String> command = new ArrayList<String>();

            command.add( vmName );

            String argument = "-J-D" + RESTART_PID + "=" + thisVmDetails.getPid();
            command.add( argument );

            return command.toArray( new String[ command.size() ] );
        }
        catch ( Exception e )
        {
            LoggerEx.error( e.getMessage(), e );
            return null;
        }
    }
}
