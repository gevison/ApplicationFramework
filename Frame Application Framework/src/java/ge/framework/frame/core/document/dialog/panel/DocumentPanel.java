package ge.framework.frame.core.document.dialog.panel;

import com.jidesoft.list.StyledListCellRenderer;
import com.jidesoft.navigation.NavigationList;
import com.jidesoft.swing.JideScrollPane;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.StyledLabel;
import ge.framework.frame.core.DocumentWorkspaceApplicationFrame;
import ge.framework.frame.core.document.ApplicationDocumentComponent;
import ge.utils.bundle.Resources;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 12:06
 */
public class DocumentPanel extends JPanel
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.frame.core" );

    private NavigationList list;

    private DefaultListModel tableModel;

    private DocumentWorkspaceApplicationFrame applicationFrame;

    public DocumentPanel( DocumentWorkspaceApplicationFrame applicationFrame )
    {
        super( new BorderLayout() );
        this.applicationFrame = applicationFrame;
        initialise();
    }

    private void initialise()
    {
        tableModel = new DefaultListModel();

        List<ApplicationDocumentComponent> documents = applicationFrame.getDocumentComponents();

        for ( ApplicationDocumentComponent document : documents )
        {
            tableModel.addElement( document );
        }

        list = new NavigationList( tableModel );
        list.setCellRenderer( new DocumentListRenderer() );
        list.setSelectionMode( ListSelectionModel.SINGLE_INTERVAL_SELECTION );

        JideScrollPane scrollPane = new JideScrollPane( list );

        scrollPane.setBorder( new PartialLineBorder( Color.GRAY, 1 ) );

        StyledLabel titleLabel = new StyledLabel( resources.getResourceString( DocumentPanel.class, "title" ) );

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

    public ApplicationDocumentComponent getSelectedValue()
    {
        return ( ApplicationDocumentComponent ) list.getSelectedValue();
    }

    public NavigationList getList()
    {
        return list;
    }

    private class DocumentListRenderer extends StyledListCellRenderer
    {
        @Override
        protected void customizeStyledLabel( JList table, Object value, int index, boolean isSelected,
                                             boolean hasFocus )
        {
            if ( value instanceof ApplicationDocumentComponent )
            {
                ApplicationDocumentComponent document = ( ApplicationDocumentComponent ) value;

                String label = document.getMenuTitle();

                super.customizeStyledLabel( table, label, index, isSelected, hasFocus );

                setIcon( document.getMenuIcon() );
            }
            else
            {
                super.customizeStyledLabel( table, value, index, isSelected, hasFocus );
            }
        }
    }
}
