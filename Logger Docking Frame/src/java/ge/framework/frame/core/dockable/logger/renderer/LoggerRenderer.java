package ge.framework.frame.core.dockable.logger.renderer;

import ge.utils.bundle.Resources;
import org.apache.log4j.Level;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerRenderer extends DefaultTableCellRenderer
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core.dockable.logger.resources" );

    private static final DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss,SSS" );

    @Override
    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                    int row, int column )
    {
        if ( ( column >= 0 ) && ( column <= 5 ) )
        {
            Level level = ( Level ) table.getValueAt( row, 1 );

            if ( isSelected )
            {
                setBackground( table.getSelectionBackground() );
                setForeground( table.getSelectionForeground() );
            }
            else
            {
                setBackground( table.getBackground() );
                if ( level.isGreaterOrEqual( Level.ERROR ) == true )
                {
                    setForeground( Color.RED );
                }
                else if ( level.isGreaterOrEqual( Level.WARN ) == true )
                {
                    setForeground( Color.YELLOW );
                }
                else
                {
                    setForeground( table.getForeground() );
                }
            }

            setFont( table.getFont() );

            if ( column == 0 )
            {
                if ( level.isGreaterOrEqual( Level.ERROR ) == true )
                {
                    setIcon( resources.getResourceIcon( LoggerRenderer.class, "icon", "error" ) );
                }
                else if ( level.isGreaterOrEqual( Level.WARN ) == true )
                {
                    setIcon( resources.getResourceIcon( LoggerRenderer.class, "icon", "warn" ) );
                }
                else if ( level.isGreaterOrEqual( Level.INFO ) == true )
                {
                    setIcon( resources.getResourceIcon( LoggerRenderer.class, "icon", "info" ) );
                }
                else
                {
                    setIcon( resources.getResourceIcon( LoggerRenderer.class, "icon", "other" ) );
                }

                setText( ( String ) value );
            }
            else if ( column == 1 )
            {
                setIcon( null );

                setText( resources.getResourceString( LoggerRenderer.class, level.toString() ) );
            }
            else if ( column == 2 )
            {
                Date date = ( Date ) value;
                setIcon( null );
                setText( dateFormat.format( date ) );
            }
            else if ( column >= 3 )
            {
                setIcon( null );
                setText( ( String ) value );
            }
            return this;
        }
        else
        {
            return super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
        }
    }
}
