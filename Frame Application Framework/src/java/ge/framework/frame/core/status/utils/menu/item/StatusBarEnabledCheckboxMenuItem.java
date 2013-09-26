package ge.framework.frame.core.status.utils.menu.item;


import ge.framework.frame.core.status.utils.StatusBarEnabled;

import javax.swing.JCheckBoxMenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/06/2012
 * Time: 07:34
 */
public class StatusBarEnabledCheckboxMenuItem extends JCheckBoxMenuItem implements StatusBarEnabled
{
    private String statusBarText;

    public StatusBarEnabledCheckboxMenuItem()
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
