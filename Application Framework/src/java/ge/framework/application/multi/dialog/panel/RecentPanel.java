package ge.framework.application.multi.dialog.panel;

import com.jidesoft.list.StyledListCellRenderer;
import com.jidesoft.navigation.NavigationList;
import com.jidesoft.plaf.basic.ThemePainter;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideScrollPane;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.StyleRange;
import com.jidesoft.swing.StyledLabel;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;
import ge.utils.text.StringArgumentMessageFormat;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/02/13
 * Time: 13:17
 */
public class RecentPanel extends JPanel implements ActionListener
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.application.multi" );

    private DefaultListModel tableModel;

    private NavigationList list;

    private JideButton clearButton;

    private MultiFrameApplication application;

    public RecentPanel( MultiFrameApplication application )
    {
        super( new BorderLayout() );
        this.application = application;
        initialise();
    }

    public void initialise()
    {
        Color selectionBackground = new Color( 175, 215, 255 );

        tableModel = new DefaultListModel();

        reloadRecentList();

        list = new NavigationList( tableModel );
        list.setCellRenderer( new RecentListRenderer() );
        list.setFixedCellHeight( 50 );
        list.setSelectionMode( ListSelectionModel.SINGLE_INTERVAL_SELECTION );

        JideScrollPane scrollPane = new JideScrollPane( list );

        scrollPane.setBorder( new PartialLineBorder( Color.GRAY, 1 ) );

        StyledLabel titleLabel = new StyledLabel(
                resources.getResourceString( RecentPanel.class, "recent", "label" ) );
        titleLabel.setHorizontalAlignment( SwingConstants.CENTER );
        titleLabel.setVerticalAlignment( SwingConstants.CENTER );
        Font font = new Font( titleLabel.getFont().getFontName(), Font.BOLD, titleLabel.getFont().getSize() + 4 );
        titleLabel.setFont( font );
        titleLabel.setBackground( Color.LIGHT_GRAY );
        titleLabel.setOpaque( true );
        titleLabel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.VERTICAL | PartialSide.NORTH ) );

        clearButton = new JideButton(
                resources.getResourceString( RecentPanel.class, "clear", "label" ) );
        clearButton.addActionListener( this );
        clearButton.setHorizontalAlignment( SwingConstants.CENTER );
        clearButton.setBackgroundOfState( ThemePainter.STATE_ROLLOVER, selectionBackground );
        clearButton.setBackgroundOfState( ThemePainter.STATE_PRESSED, selectionBackground );

        add( BorderLayout.NORTH, titleLabel );
        add( BorderLayout.CENTER, scrollPane );
        add( BorderLayout.SOUTH, clearButton );

        setSize( new Dimension( 300, 200 ) );
        setMinimumSize( new Dimension( 300, 200 ) );
        setPreferredSize( new Dimension( 300, 200 ) );
        setBorder( new EmptyBorder( 10, 10, 10, 5 ) );
    }

    public void reloadRecentList()
    {
        tableModel.removeAllElements();

        List<FrameInstanceDetailsObject> recentlyOpened = application.getRecentlyOpened();

        for ( FrameInstanceDetailsObject frameInstanceDetailsObject : recentlyOpened )
        {
            tableModel.addElement( frameInstanceDetailsObject );
        }
    }

    public void addListSelectionListener( ListSelectionListener listener )
    {
        list.addListSelectionListener( listener );
    }

    public void removeListSelectionListener( ListSelectionListener listener )
    {
        list.removeListSelectionListener( listener );
    }

    public void clearSelection()
    {
        list.clearSelection();
    }

    public FrameInstanceDetailsObject getSelectedValue()
    {
        return ( FrameInstanceDetailsObject ) list.getSelectedValue();
    }

    public NavigationList getList()
    {
        return list;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object source = e.getSource();

        if ( source == clearButton )
        {
            tableModel.clear();
            application.clearRecent();
        }
    }

    private class RecentListRenderer extends StyledListCellRenderer
    {
        @Override
        protected void customizeStyledLabel( JList table, Object value, int index, boolean isSelected,
                                             boolean hasFocus )
        {
            if ( value instanceof FrameInstanceDetailsObject )
            {
                FrameInstanceDetailsObject frameInstanceDetailsObject = ( FrameInstanceDetailsObject ) value;

                String label = resources.getResourceString( RecentPanel.class, "list", "label" );

                String problemLocked = resources.getResourceString( RecentPanel.class, "list", "label", "locked" );
                String problemMissing = resources.getResourceString( RecentPanel.class, "list", "label", "missing" );

                String problem = "";

                if ( application.doesFrameConfigurationFileExist( frameInstanceDetailsObject ) == false )
                {
                    problem = problemMissing;

                    Color red = new Color( 255, 244, 99 );

                    setBackground( red );
                }
                else if ( application.isFrameConfigurationFileLocked( frameInstanceDetailsObject ) == true )
                {
                    problem = problemLocked;

                    Color red = new Color( 255, 117, 117 );

                    setBackground( red );
                }

                String name = frameInstanceDetailsObject.getName();
                File location = frameInstanceDetailsObject.getLocation();

                Map<String, Object> arguments = new HashMap<String, Object>();
                arguments.put( "name", name );
                arguments.put( "location", location.getPath() );
                arguments.put( "problem", problem );

                label = StringArgumentMessageFormat.format( label, arguments );

                super.customizeStyledLabel( table, label, index, isSelected, hasFocus );

                setBorder( new EmptyBorder( 5, 5, 5, 5 ) );

                setMinimumSize( new Dimension( 200, 60 ) );

                int carriageReturn = label.indexOf( "\n" );

                addStyleRange( new StyleRange( 0, carriageReturn, Font.BOLD ) );
                addStyleRange( new StyleRange( carriageReturn + 1, -1, Color.DARK_GRAY ) );

                setIcon( application.getLargeIcon() );
            }
            else
            {
                super.customizeStyledLabel( table, value, index, isSelected, hasFocus );
            }
        }
    }
}
