package ge.framework.frame.core.menu.utils;


import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.status.utils.menu.item.StatusBarEnabledSpacerMenuItem;

import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 11:38
 */
public abstract class ApplicationFrameMenuItem extends StatusBarEnabledSpacerMenuItem implements
                                                                                      ApplicationFrameMenuComponent,
                                                                                      ActionListener
{
    protected ApplicationFrame applicationFrame;

    protected ApplicationFrameMenuItem( ApplicationFrame applicationFrame )
    {
        super();
        this.applicationFrame = applicationFrame;
    }

    @Override
    public final void initialise()
    {
        addActionListener( this );

        initialiseMenuItem();
    }

    protected abstract void initialiseMenuItem();
}
