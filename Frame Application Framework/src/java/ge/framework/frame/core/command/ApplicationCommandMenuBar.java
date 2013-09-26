package ge.framework.frame.core.command;

import com.jidesoft.action.CommandMenuBar;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 15:30
 */
public class ApplicationCommandMenuBar extends CommandMenuBar implements ApplicationCommandBarComponent
{
    public ApplicationCommandMenuBar()
    {
        super();
    }

    public ApplicationCommandMenuBar( int orientation )
    {
        super( orientation );
    }

    public ApplicationCommandMenuBar( String key )
    {
        super( key );
    }

    public ApplicationCommandMenuBar( String key, String title )
    {
        super( key, title );
    }

    public ApplicationCommandMenuBar( String key, String title, int orientation )
    {
        super( key, title, orientation );
    }
}
