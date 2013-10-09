package ge.framework.application.frame.core.command.dialog.panel;

import com.jidesoft.action.DockableBarManager;
import com.jidesoft.list.StyledListCellRenderer;
import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.JideScrollPane;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.StyledLabel;
import ge.framework.application.frame.core.command.ApplicationCommandBar;
import ge.utils.bundle.Resources;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Position;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 17:21
 */
public class CommandBarPanel extends JPanel
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.frame.core" );

    private DefaultListModel tableModel;

    private CheckBoxList list;

    public CommandBarPanel()
    {
        super( new BorderLayout() );
        initialise();
    }

    private void initialise()
    {
        tableModel = new DefaultListModel();

        list = new CheckBoxList( tableModel )
        {
            @Override
            public int getNextMatch( String prefix, int startIndex, Position.Bias bias )
            {
                return -1;
            }

            @Override
            public boolean isCheckBoxEnabled( int index )
            {
                ApplicationCommandBar applicationCommandBarComponent =
                        ( ApplicationCommandBar ) tableModel.elementAt( index );

                DockableBarManager dockableBarManager = applicationCommandBarComponent.getDockableBarManager();

                if ( ( dockableBarManager.isHidable() == true ) &&
                        ( applicationCommandBarComponent.isHidable() == true ) )
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        };
        list.setClickInCheckBoxOnly( false );
        list.getCheckBoxListSelectionModel().setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
        list.setCellRenderer( new CommandBarListRenderer() );

        JideScrollPane scrollPane = new JideScrollPane( list );

        scrollPane.setBorder( new PartialLineBorder( Color.GRAY, 1 ) );

        StyledLabel titleLabel = new StyledLabel( resources.getResourceString( CommandBarPanel.class, "title" ) );

        titleLabel.setHorizontalAlignment( SwingConstants.CENTER );
        titleLabel.setVerticalAlignment( SwingConstants.CENTER );
        Font font = new Font( titleLabel.getFont().getFontName(), Font.BOLD, titleLabel.getFont().getSize() + 4 );
        titleLabel.setFont( font );
        titleLabel.setBackground( Color.LIGHT_GRAY );
        titleLabel.setOpaque( true );
        titleLabel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.VERTICAL | PartialSide.NORTH ) );

        add( BorderLayout.NORTH, titleLabel );
        add( BorderLayout.CENTER, scrollPane );

        setSize( new Dimension( 300, 200 ) );
        setMinimumSize( new Dimension( 300, 200 ) );
        setPreferredSize( new Dimension( 300, 200 ) );
        setBorder( new EmptyBorder( 10, 10, 10, 5 ) );
    }

    public void setCommandBarComponents( List<ApplicationCommandBar> commandBarComponents )
    {
        tableModel.removeAllElements();
        List<Integer> selected = new ArrayList<Integer>();

        for ( int i = 0; i < commandBarComponents.size(); i++ )
        {
            ApplicationCommandBar applicationCommandBarComponent = commandBarComponents.get( i );
            tableModel.addElement( applicationCommandBarComponent );

            if ( applicationCommandBarComponent.isHidden() == false )
            {
                selected.add( i );
            }
        }

        int[] indexs = new int[ selected.size() ];

        for ( int i = 0; i < selected.size(); i++ )
        {
            indexs[ i ] = selected.get( i );
        }

        list.setCheckBoxListSelectedIndices( indexs );
    }

    public List<ApplicationCommandBar> getSelectedValues()
    {
        List<ApplicationCommandBar> retVal = new ArrayList<ApplicationCommandBar>();

        Object[] checkBoxListSelectedValues =
                list.getCheckBoxListSelectedValues();

        if ( ( checkBoxListSelectedValues != null ) && ( checkBoxListSelectedValues.length != 0 ) )
        {
            for ( Object checkBoxListSelectedValue : checkBoxListSelectedValues )
            {
                retVal.add( ( ApplicationCommandBar ) checkBoxListSelectedValue );
            }
        }

        return retVal;
    }

    private class CommandBarListRenderer extends StyledListCellRenderer
    {
        @Override
        protected void customizeStyledLabel( JList table, Object value, int index, boolean isSelected,
                                             boolean hasFocus )
        {
            if ( value instanceof ApplicationCommandBar )
            {
                ApplicationCommandBar component = ( ApplicationCommandBar ) value;

                String label = component.getTitle();

                super.customizeStyledLabel( table, label, index, isSelected, hasFocus );
            }
            else
            {
                super.customizeStyledLabel( table, value, index, isSelected, hasFocus );
            }
        }
    }
}
