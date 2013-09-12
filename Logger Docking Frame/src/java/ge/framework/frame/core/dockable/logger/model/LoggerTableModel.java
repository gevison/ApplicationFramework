package ge.framework.frame.core.dockable.logger.model;

import ge.utils.bundle.Resources;
import ge.utils.log.LoggerAppender;
import ge.utils.log.LoggerListener;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: evison_g
 * Date: 07/07/11
 * Time: 15:04
 */
public class LoggerTableModel extends DefaultTableModel implements TableModel,
                                                                   LoggerListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core.dockable.logger.resources" );

    private EventListenerList eventListenerList = new EventListenerList();

    public LoggerTableModel()
    {
        LoggerAppender.addLoggerListener( this );
    }

    @Override
    public void newLoggingEvent()
    {
        fireTableRowsInserted( LoggerAppender.size() - 1, LoggerAppender.size() - 1 );
    }

    @Override
    public int getRowCount()
    {
        return LoggerAppender.size();
    }

    @Override
    public int getColumnCount()
    {
        return 6;
    }

    @Override
    public String getColumnName( int columnIndex )
    {
        switch ( columnIndex )
        {
            case 0:
            {
                return resources.getResourceString( LoggerTableModel.class, "message" );
            }
            case 1:
            {
                return resources.getResourceString( LoggerTableModel.class, "level" );
            }
            case 2:
            {
                return resources.getResourceString( LoggerTableModel.class, "time" );
            }
            case 3:
            {
                return resources.getResourceString( LoggerTableModel.class, "class" );
            }
            case 4:
            {
                return resources.getResourceString( LoggerTableModel.class, "method" );
            }
            case 5:
            {
                return resources.getResourceString( LoggerTableModel.class, "line" );
            }
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass( int columnIndex )
    {
        switch ( columnIndex )
        {
            case 0:
            {
                return String.class;
            }
            case 1:
            {
                return Level.class;
            }
            case 2:
            {
                return Date.class;
            }
            case 3:
            {
                return String.class;
            }
            case 4:
            {
                return String.class;
            }
            case 5:
            {
                return String.class;
            }
        }
        return null;
    }

    @Override
    public boolean isCellEditable( int rowIndex, int columnIndex )
    {
        return false;
    }

    @Override
    public Object getValueAt( int rowIndex, int columnIndex )
    {
        LoggingEvent loggingEvent = LoggerAppender.get( rowIndex );
        LocationInfo locationInformation = loggingEvent.getLocationInformation();

        switch ( columnIndex )
        {
            case 0:
            {
                return loggingEvent.getRenderedMessage();
            }
            case 1:
            {
                return loggingEvent.getLevel();
            }
            case 2:
            {
                return new Date(loggingEvent.getTimeStamp());
            }
            case 3:
            {
                return locationInformation.getClassName();
            }
            case 4:
            {
                return locationInformation.getMethodName();
            }
            case 5:
            {
                return locationInformation.getLineNumber();
            }
        }
        return null;
    }

    @Override
    public void setValueAt( Object aValue, int rowIndex, int columnIndex )
    {
    }

    @Override
    public void addTableModelListener( TableModelListener l )
    {
        eventListenerList.add( TableModelListener.class, l );
    }

    @Override
    public void removeTableModelListener( TableModelListener l )
    {
        eventListenerList.remove( TableModelListener.class, l );
    }

    public void fireTableRowsInserted( int firstRow, int lastRow )
    {
        fireTableChanged( new TableModelEvent( this,
                                               firstRow,
                                               lastRow,
                                               TableModelEvent.ALL_COLUMNS,
                                               TableModelEvent.INSERT ) );
    }
}
