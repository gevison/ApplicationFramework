package ge.framework.frame.core.status;

import com.jidesoft.status.LabelStatusBarItem;
import com.jidesoft.status.StatusBar;
import com.jidesoft.status.StatusBarItem;
import com.jidesoft.swing.JideBoxLayout;
import ge.framework.frame.core.status.enums.StatusBarConstraint;
import ge.framework.frame.core.status.utils.StatusBarEnabled;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Arrays;
import java.util.Collections;

public class ApplicationStatusBar extends StatusBar implements ChangeListener
{
    private LabelStatusBarItem defaultStatusBarItem;

    public ApplicationStatusBar()
    {
        super();

        initialiseStatusBar();
    }

    private void initialiseStatusBar()
    {
        defaultStatusBarItem = new LabelStatusBarItem();

        super.add( defaultStatusBarItem, JideBoxLayout.FLEXIBLE );

        MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
        menuSelectionManager.addChangeListener( this );
    }

    public void add( StatusBarItem statusBarItem, StatusBarConstraint constraint )
    {
        super.add( statusBarItem, constraint.getConstraint() );
    }

    public void remove( StatusBarItem statusBarItem )
    {
        super.remove( statusBarItem );
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        MenuSelectionManager menuSelectionManager = ( MenuSelectionManager ) event.getSource();

        MenuElement[] selectedPath = menuSelectionManager.getSelectedPath();

        if ( ( selectedPath != null ) && ( selectedPath.length != 0 ) )
        {
            Collections.reverse( Arrays.asList( selectedPath ) );

            boolean changed = false;

            for ( MenuElement menuElement : selectedPath )
            {
                if ( menuElement instanceof StatusBarEnabled )
                {
                    StatusBarEnabled statusBarEnabled = ( StatusBarEnabled ) menuElement;

                    if ( statusBarEnabled.getStatusBarText() != null )
                    {
                        defaultStatusBarItem.setText( statusBarEnabled.getStatusBarText() );
                        changed = true;
                    }

                    break;
                }
            }

            if ( changed == false )
            {
                defaultStatusBarItem.setText( "" );
            }
        }
        else
        {
            defaultStatusBarItem.setText( "" );
        }
    }
}
