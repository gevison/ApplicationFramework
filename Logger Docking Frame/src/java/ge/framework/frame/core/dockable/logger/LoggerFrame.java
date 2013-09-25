package ge.framework.frame.core.dockable.logger;

import com.jidesoft.action.CommandBar;
import com.jidesoft.action.CommandMenuBar;
import com.jidesoft.docking.DockContext;
import com.jidesoft.grid.DefaultTableColumnWidthKeeper;
import com.jidesoft.grid.FilterableTableModel;
import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.TableColumnChooserPopupMenuCustomizer;
import com.jidesoft.grid.TableColumnWidthKeeper;
import com.jidesoft.grid.TableHeaderPopupMenuInstaller;
import com.jidesoft.swing.JideMenu;
import com.jidesoft.swing.JideScrollPane;
import ge.framework.application.frame.core.FrameApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.framework.frame.core.dockable.logger.dialog.LoggingEventDialog;
import ge.framework.frame.core.dockable.logger.model.LoggerTableModel;
import ge.framework.frame.core.dockable.logger.model.filter.LevelFilter;
import ge.framework.frame.core.dockable.logger.renderer.LoggerRenderer;
import ge.framework.frame.core.status.utils.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.utils.bundle.Resources;
import ge.utils.log.LoggerAppender;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.jdom2.CDATA;
import org.jdom2.Content;
import org.jdom2.Element;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

public class LoggerFrame extends ApplicationDockableFrame implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core.dockable.logger.resources" );

    private FilterableTableModel filterableTableModel;

    private JideTable table;

    private CommandBar commandBar;

    private JideMenu levelMenu;

    private StatusBarEnabledSpacerMenuItem errorMenuItem;

    private StatusBarEnabledSpacerMenuItem warnMenuItem;

    private StatusBarEnabledSpacerMenuItem infoMenuItem;

    private StatusBarEnabledSpacerMenuItem allMenuItem;

    private Level filterLevel;

    public LoggerFrame()
    {
        super( "logger" );

        setInitMode( DockContext.STATE_FRAMEDOCKED );
        setInitSide( DockContext.DOCK_SIDE_SOUTH );
        setInitIndex( -1 );
        setFloatable( false );

        setPreferredSize( new Dimension( 10, 10 ) );

        setTitle( resources.getResourceString( LoggerFrame.class, "title" ) );
        setSideTitle( getTitle() );
        setTabTitle( getTitle() );
        setFrameIcon( resources.getResourceIcon( LoggerFrame.class, "icon" ) );

        JPanel panel = new JPanel( new BorderLayout( 1, 1 ) );
        panel.setBorder( BorderFactory.createEmptyBorder( 1, 1, 1, 1 ) );

        initialiseTable();
        initialiseFilter();

        panel.add( BorderLayout.CENTER, new JideScrollPane( table ) );

        setTitleBarComponent( commandBar );

        getContentPane().add( panel );

        setOpaque( false );

        changeFilter( null );
    }

    private void initialiseFilter()
    {
        errorMenuItem = new StatusBarEnabledSpacerMenuItem();
        errorMenuItem.setText( resources.getResourceString( LoggerFrame.class, "ERROR" ) );
        errorMenuItem.setStatusBarText( resources.getResourceString( LoggerFrame.class, "ERROR.status" ) );
        errorMenuItem.setIcon( resources.getResourceIcon( LoggerFrame.class, "icon", "error" ) );
        errorMenuItem.addActionListener( this );

        warnMenuItem = new StatusBarEnabledSpacerMenuItem();
        warnMenuItem.setText( resources.getResourceString( LoggerFrame.class, "WARN" ) );
        warnMenuItem.setStatusBarText( resources.getResourceString( LoggerFrame.class, "WARN.status" ) );
        warnMenuItem.setIcon( resources.getResourceIcon( LoggerFrame.class, "icon", "warn" ) );
        warnMenuItem.addActionListener( this );

        infoMenuItem = new StatusBarEnabledSpacerMenuItem();
        infoMenuItem.setText( resources.getResourceString( LoggerFrame.class, "INFO" ) );
        infoMenuItem.setStatusBarText( resources.getResourceString( LoggerFrame.class, "INFO.status" ) );
        infoMenuItem.setIcon( resources.getResourceIcon( LoggerFrame.class, "icon", "info" ) );
        infoMenuItem.addActionListener( this );

        allMenuItem = new StatusBarEnabledSpacerMenuItem();
        allMenuItem.setText( resources.getResourceString( LoggerFrame.class, "ALL" ) );
        allMenuItem.setStatusBarText( resources.getResourceString( LoggerFrame.class, "ALL.status" ) );
        allMenuItem.setIcon( resources.getResourceIcon( LoggerFrame.class, "icon", "other" ) );
        allMenuItem.addActionListener( this );

        commandBar = new CommandMenuBar( "LoggerCommandBar" );
        commandBar.setStretch( false );
        commandBar.setChevronAlwaysVisible( false );
        commandBar.setPaintBackground( false );

        levelMenu = new JideMenu( resources.getResourceString( LoggerFrame.class, "ALL" ) );

        levelMenu.add( errorMenuItem );
        levelMenu.add( warnMenuItem );
        levelMenu.add( infoMenuItem );
        levelMenu.add( allMenuItem );

        levelMenu.setOpaque( true );

        commandBar.add( levelMenu );
    }

    private void initialiseTable()
    {
        LoggerTableModel tableModel = new LoggerTableModel();

        filterableTableModel = new FilterableTableModel( tableModel );

        table = new JideTable( filterableTableModel );
        table.setAutoResizeMode( JideTable.AUTO_RESIZE_OFF );
        table.setBorder( BorderFactory.createEmptyBorder( 0, 3, 0, 2 ) );
        table.setDefaultRenderer( String.class, new LoggerRenderer() );
        table.setDefaultRenderer( Date.class, new LoggerRenderer() );
        table.setDefaultRenderer( Level.class, new LoggerRenderer() );
        table.setColumnAutoResizable( true );
        table.setTableColumnWidthKeeper( new DefaultTableColumnWidthKeeper() );
        table.addMouseListener( new LoggerFrameMouseListener( table ) );

        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller( table );

        TableColumnChooserPopupMenuCustomizer tableColumnChooserPopupMenuCustomizer =
                new TableColumnChooserPopupMenuCustomizer();

        installer.addTableHeaderPopupMenuCustomizer( tableColumnChooserPopupMenuCustomizer );

        tableColumnChooserPopupMenuCustomizer.setFixedColumns( new int[]{ 0 } );
    }

    @Override
    public Element saveLayoutData()
    {
        Element element = new Element( "LoggerFrame" );

        if ( filterLevel == null )
        {
            element.setAttribute( "filter", "ALL" );
        }
        else
        {
            element.setAttribute( "filter", filterLevel.toString() );
        }

        TableColumnWidthKeeper tableColumnWidthKeeper = table.getTableColumnWidthKeeper();

        String object = ( String ) tableColumnWidthKeeper.saveTableColumnWidth( table );

        element.addContent( new CDATA( object ) );

        return element;
    }

    @Override
    public void loadLayoutData( Element element )
    {
        String filter = element.getAttributeValue( "filter" );

        if ( ( filter != null ) && ( filter.length() != 0 ) )
        {
            if ( filter.equals( "ALL" ) == true )
            {
                changeFilter( null );
            }
            else if ( filter.equals( "INFO" ) == true )
            {
                changeFilter( Level.INFO );
            }
            else if ( filter.equals( "WARNING" ) == true )
            {
                changeFilter( Level.WARN );
            }
            else if ( filter.equals( "ERROR" ) == true )
            {
                changeFilter( Level.ERROR );
            }
        }

        List<Content> children = element.getContent();

        for ( Content child : children )
        {
            if ( child instanceof CDATA )
            {
                CDATA cdata = ( CDATA ) child;

                TableColumnWidthKeeper tableColumnWidthKeeper = table.getTableColumnWidthKeeper();

                tableColumnWidthKeeper.restoreTableColumnWidth( table, cdata.getText() );
            }
        }
    }

    private void changeFilter( Level level )
    {
        this.filterLevel = level;

        filterableTableModel.clearFilters();
        filterableTableModel.setFiltersApplied( false );

        if ( level != null )
        {
            filterableTableModel.addFilter( 1, new LevelFilter( level ) );
            filterableTableModel.setFiltersApplied( true );
        }

        if ( level == null )
        {
            levelMenu.setIcon( resources.getResourceIcon( LoggerFrame.class, "icon", "other" ) );
            levelMenu.setText( resources.getResourceString( LoggerFrame.class, "ALL" ) );
        }
        else if ( level == Level.INFO )
        {
            levelMenu.setIcon( resources.getResourceIcon( LoggerFrame.class, "icon", "info" ) );
            levelMenu.setText( resources.getResourceString( LoggerFrame.class, "INFO" ) );
        }
        else if ( level == Level.WARN )
        {
            levelMenu.setIcon( resources.getResourceIcon( LoggerFrame.class, "icon", "warn" ) );
            levelMenu.setText( resources.getResourceString( LoggerFrame.class, "WARN" ) );
        }
        else if ( level == Level.ERROR )
        {
            levelMenu.setIcon( resources.getResourceIcon( LoggerFrame.class, "icon", "error" ) );
            levelMenu.setText( resources.getResourceString( LoggerFrame.class, "ERROR" ) );
        }
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object source = e.getSource();

        if ( source == allMenuItem )
        {
            changeFilter( null );
        }
        else if ( source == infoMenuItem )
        {
            changeFilter( Level.INFO );
        }
        else if ( source == warnMenuItem )
        {
            changeFilter( Level.WARN );
        }
        else if ( source == errorMenuItem )
        {
            changeFilter( Level.ERROR );
        }
    }

    private class LoggerFrameMouseListener extends MouseAdapter
    {
        private final JideTable table;

        public LoggerFrameMouseListener( JideTable table )
        {
            this.table = table;
        }

        @Override
        public void mouseClicked( MouseEvent e )
        {
            if ( e.getSource() == table )
            {
                if ( e.getClickCount() == 2 )
                {
                    if ( ( e.getModifiers() & InputEvent.BUTTON1_MASK ) == InputEvent.BUTTON1_MASK )
                    {
                        int i = table.rowAtPoint( e.getPoint() );

                        int actualRowAt = filterableTableModel.getActualRowAt( i );

                        LoggingEvent logCompilerEvent = LoggerAppender.get( actualRowAt );

                        if ( logCompilerEvent != null )
                        {
                            ApplicationFrame applicationFrame = getApplicationFrame();
                            FrameApplication application = ( FrameApplication ) applicationFrame.getApplication();
                            LoggingEventDialog loggingEventDialog = new LoggingEventDialog(applicationFrame,application, logCompilerEvent );

                            loggingEventDialog.doModal();
                        }
                    }
                }
            }
        }
    }
}
