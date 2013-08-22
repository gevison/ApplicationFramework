package ge.framework.application.multi.command;

import com.jidesoft.swing.JideSplitButton;
import ge.framework.application.multi.MultiApplication;
import ge.framework.application.multi.menu.item.NewMenuItem;
import ge.framework.application.multi.menu.item.OtherNewMenuItem;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.multi.objects.FrameDefinition;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/02/13
 * Time: 13:07
 */
public class NewCommandButton extends JideSplitButton implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    private ApplicationFrame applicationFrame;

    public NewCommandButton(ApplicationFrame applicationFrame)
    {
        this.applicationFrame = applicationFrame;
        initialiseMenu();
    }

    private void initialiseMenu()
    {
        setIcon( resources.getResourceIcon( NewCommandButton.class, "icon" ) );

        setFocusable( false );
        setOpaque( false );

        MultiApplication application = ( MultiApplication ) applicationFrame.getApplication();

        List<FrameDefinition> availableFrameConfigurationNames =
                application.getFrameDefinitions();

        if ( availableFrameConfigurationNames.size() > 15 )
        {
            for ( int i = 0; i < 10; i++ )
            {
                NewMenuItem menuItem = new NewMenuItem( applicationFrame, availableFrameConfigurationNames.get( i ),
                                                        false );
                menuItem.initialise();
                add( menuItem );
            }

            addSeparator();

            OtherNewMenuItem menuItem = new OtherNewMenuItem( applicationFrame );
            menuItem.initialise();
            add( menuItem );
        }
        else
        {
            for ( FrameDefinition typeName : availableFrameConfigurationNames )
            {
                NewMenuItem menuItem = new NewMenuItem( applicationFrame, typeName, false );
                menuItem.initialise();
                add( menuItem );
            }
        }

        addActionListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiApplication application = ( MultiApplication ) applicationFrame.getApplication();
        application.processNew(applicationFrame,null);
    }
}
