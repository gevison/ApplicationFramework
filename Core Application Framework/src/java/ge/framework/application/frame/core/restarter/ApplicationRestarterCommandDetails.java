package ge.framework.application.core.restarter;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/09/13
 * Time: 14:44
 */
public interface ApplicationRestarterCommandDetails
{
    public static final String RESTART_PID = "ge.framework.application.restartPID";

    String[] getRestartCommand( String mainClass, String[] applicationArguments );
}
