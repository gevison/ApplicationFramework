package ge.framework.test.frame.multi;

import com.jidesoft.swing.JideButton;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import org.jdom2.Element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */
public class SingleTestFrame extends ApplicationDockableFrame implements ActionListener
{
    private final JideButton button;

    public SingleTestFrame( String key )
    {
        super(key);

        button = new JideButton( "Button" );
        button.addActionListener( this );

        getContentPane().add(button);
    }

    @Override
    public Element saveLayoutData()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void loadLayoutData( Element element )
    {


    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource() == button )
        {
            setEnabled( false );
            setVisible( false );
            revalidate();
            repaint();
        }
    }
}
