package ge.framework.frame.core.status.utils.menu;

import ge.framework.frame.core.status.utils.StatusBarEnabled;
import ge.utils.menu.SpacerMenu;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 18:54
 */
public class StatusBarEnabledSpacerMenu extends SpacerMenu implements StatusBarEnabled
{
    private String statusBarText;

    public StatusBarEnabledSpacerMenu()
    {
        this( true );
    }

    public StatusBarEnabledSpacerMenu( boolean useSpacer )
    {
        super( useSpacer );
    }

    public String getStatusBarText()
    {
        return statusBarText;
    }

    public void setStatusBarText( String statusBarText )
    {
        this.statusBarText = statusBarText;
    }
}
