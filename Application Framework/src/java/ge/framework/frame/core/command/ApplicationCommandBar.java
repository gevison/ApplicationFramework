package ge.framework.frame.core.command;

import com.jidesoft.action.CommandBar;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 15:30
 */
public class ApplicationCommandBar extends CommandBar implements ApplicationCommandBarComponent
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
}
