package ge.framework.application.frame.core.command;

import com.jidesoft.action.CommandBar;
import ge.utils.os.OS;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 15:30
 */
public class ApplicationCommandBar extends CommandBar
{
    public ApplicationCommandBar()
    {
        super();
    }

    public ApplicationCommandBar( int orientation )
    {
        super( orientation );
    }

    public ApplicationCommandBar( String key )
    {
        super( key );
    }

    public ApplicationCommandBar( String key, String title )
    {
        super( key, title );
    }

    public ApplicationCommandBar( String key, String title, int orientation )
    {
        super( key, title, orientation );
    }

    @Override
    public void setInitIndex( int i )
    {
        if (( OS.isMac() == false ) && ( i <= 0 ))
        {
            super.setInitIndex( 1 );
        }
        else
        {
            super.setInitIndex( i );
        }
    }
}
