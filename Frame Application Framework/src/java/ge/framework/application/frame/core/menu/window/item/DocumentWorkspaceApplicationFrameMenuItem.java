package ge.framework.application.frame.core.menu.window.item;

import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.DocumentWorkspaceApplicationFrame;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 09/09/13
 * Time: 09:49
 */
public abstract class DocumentWorkspaceApplicationFrameMenuItem extends ApplicationFrameMenuItem
{
    protected DocumentWorkspaceApplicationFrameMenuItem( DocumentWorkspaceApplicationFrame applicationFrame )
    {
        super( ( ApplicationFrame ) applicationFrame );
    }
}
