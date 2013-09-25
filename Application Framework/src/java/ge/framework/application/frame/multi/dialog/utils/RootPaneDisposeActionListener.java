package ge.framework.application.frame.multi.dialog.utils;

import javax.swing.JDialog;
import javax.swing.JRootPane;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 07/03/13
 * Time: 17:39
 */
public class RootPaneDisposeActionListener implements ActionListener
{
    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object source = e.getSource();

        if ( source instanceof JRootPane )
        {
            JRootPane rootPane = ( JRootPane ) source;

            Container parent = rootPane.getParent();

            if ( parent instanceof JDialog )
            {
                JDialog dialog = ( JDialog ) parent;

                dialog.dispose();
            }
        }
    }
}
