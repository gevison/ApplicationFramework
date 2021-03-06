package ge.framework.application.frame.core.menu.view.item;

import com.jidesoft.action.DockableBarManager;
import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.command.ApplicationCommandBar;
import ge.framework.application.frame.core.command.dialog.CommandBarDialog;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public class OtherCommandBarsMenuItem extends ApplicationFrameMenuItem
{
    private static Resources resources = Resources.getInstance( "ge.framework.frame.core" );

    public OtherCommandBarsMenuItem( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( OtherCommandBarsMenuItem.class, "label" ) );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        CommandBarDialog commandBarDialog = new CommandBarDialog( applicationFrame );

        if ( commandBarDialog.doModal() == true )
        {
            List<ApplicationCommandBar> selectedValues = commandBarDialog.getSelectedValues();

            List<ApplicationCommandBar> applicationCommandBarComponents = applicationFrame.getCommandBars();

            for ( ApplicationCommandBar applicationCommandBarComponent : applicationCommandBarComponents )
            {
                DockableBarManager dockableBarManager = applicationCommandBarComponent.getDockableBarManager();

                if ( selectedValues.contains( applicationCommandBarComponent ) == true )
                {
                    dockableBarManager.showDockableBar( applicationCommandBarComponent.getKey() );
                }
                else
                {
                    dockableBarManager.hideDockableBar( applicationCommandBarComponent.getKey() );
                }
            }
        }
    }

    @Override
    public void update()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
