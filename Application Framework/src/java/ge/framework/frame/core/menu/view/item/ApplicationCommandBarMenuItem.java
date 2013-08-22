package ge.framework.frame.core.menu.view.item;

import com.jidesoft.action.DockableBarManager;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.command.ApplicationCommandBarComponent;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.framework.frame.core.menu.utils.ApplicationFrameCheckboxMenuItem;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationCommandBarMenuItem extends ApplicationFrameCheckboxMenuItem
{
    private ApplicationCommandBarComponent applicationCommandBarComponent;

    public ApplicationCommandBarMenuItem( ApplicationFrame applicationFrame,
                                          ApplicationCommandBarComponent applicationCommandBarComponent )
    {
        super( applicationFrame );
        this.applicationCommandBarComponent = applicationCommandBarComponent;
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( applicationCommandBarComponent.getTitle() );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        DockableBarManager dockableBarManager = applicationCommandBarComponent.getDockableBarManager();

        if ( applicationCommandBarComponent.isHidden() == true )
        {
            dockableBarManager.showDockableBar( applicationCommandBarComponent.getKey() );
        }
        else
        {
            dockableBarManager.hideDockableBar( applicationCommandBarComponent.getKey() );
        }
    }

    @Override
    public void update()
    {
        setSelected( !applicationCommandBarComponent.isHidden() );

        DockableBarManager dockableBarManager = applicationCommandBarComponent.getDockableBarManager();

        if ( ( dockableBarManager.isHidable() == true ) && ( applicationCommandBarComponent.isHidable() == true ) )
        {
            setEnabled( true );
        }
        else
        {
            setEnabled( false );
        }
    }
}
