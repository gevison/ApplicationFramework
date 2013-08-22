package ge.framework.frame.core.status.utils.menu.item;

import ge.framework.frame.core.status.utils.StatusBarEnabled;
import ge.utils.menu.SpacerMenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 18:54
 */
public class StatusBarEnabledSpacerMenuItem extends SpacerMenuItem implements StatusBarEnabled
{
    private String statusBarText;

    public StatusBarEnabledSpacerMenuItem()
    {
        super();
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
