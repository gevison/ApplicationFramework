package ge.framework.test;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 05/09/13
 * Time: 11:11
 */
public class Test extends JFrame implements ActionListener, WindowListener
{
    private JCheckBoxMenuItem toggleFullScreenMenuItem;

    private int prevState;

    private int prevX;

    private int prevY;

    private int prevWidth;

    private int prevHeight;

    public Test() throws HeadlessException
    {
        super();

        addWindowListener( this );

        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar( menuBar );

        JMenu fileMenu = new JMenu( "File" );
        menuBar.add( fileMenu );

        toggleFullScreenMenuItem = new JCheckBoxMenuItem( "Toggle Full Screen" );
        toggleFullScreenMenuItem.addActionListener( this );
        toggleFullScreenMenuItem.setSelected( false );

        fileMenu.add( toggleFullScreenMenuItem );
    }

    public static void main( String[] args )
    {
        Test test = new Test();

        test.setVisible( true );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        WindowListener[] windowListeners = getWindowListeners();

        for ( WindowListener windowListener : windowListeners )
        {
            removeWindowListener(windowListener);
        }

        if ( toggleFullScreenMenuItem.isSelected() == true )
        {
            System.out.println( "fullscreen" );
            prevX = getX();
            prevY = getY();
            prevWidth = getWidth();
            prevHeight = getHeight();

            GraphicsConfiguration graphicsConfiguration = getGraphicsConfiguration();
            GraphicsDevice device = graphicsConfiguration.getDevice();
            DisplayMode displayMode = device.getDisplayMode();

            setBounds(0,0,displayMode.getWidth(),displayMode.getHeight());

            setVisible( false );
            setUndecorated( true );
            setVisible( true );
            System.out.println( "fullscreen complete" );
        }
        else
        {
            System.out.println("restoring");
            setVisible( false );
            setBounds( prevX, prevY, prevWidth, prevHeight );
            setUndecorated( false );
            setVisible( true );
            System.out.println( "restoring complete" );
        }

        for ( WindowListener windowListener : windowListeners )
        {
            addWindowListener( windowListener );
        }
    }

    @Override
    public void windowOpened( WindowEvent e )
    {
        System.out.println("windowOpened");
    }

    @Override
    public void windowClosing( WindowEvent e )
    {
        System.out.println( "windowClosing" );
    }

    @Override
    public void windowClosed( WindowEvent e )
    {
        System.out.println( "windowClosed" );
    }

    @Override
    public void windowIconified( WindowEvent e )
    {
        System.out.println( "windowIconified" );
    }

    @Override
    public void windowDeiconified( WindowEvent e )
    {
        System.out.println( "windowDeiconified" );
    }

    @Override
    public void windowActivated( WindowEvent e )
    {
        System.out.println( "windowActivated" );
    }

    @Override
    public void windowDeactivated( WindowEvent e )
    {
        System.out.println( "windowDeactivated" );
    }
}
