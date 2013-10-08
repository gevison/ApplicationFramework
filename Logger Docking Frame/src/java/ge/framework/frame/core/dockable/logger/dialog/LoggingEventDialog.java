package ge.framework.frame.core.dockable.logger.dialog;

import com.jidesoft.dialog.BannerPanel;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.swing.JideLabel;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.application.frame.core.dialog.ApplicationStandardDialog;
import ge.framework.application.frame.core.dialog.panel.ApplicationBannerPanel;
import ge.framework.application.frame.core.FrameApplication;
import ge.framework.application.frame.core.ApplicationFrame;
import ge.utils.bundle.Resources;
import ge.utils.layout.SpringLayoutUtilities;
import ge.utils.text.StringArgumentMessageFormat;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 05/03/13
 * Time: 11:42
 */
public class LoggingEventDialog extends ApplicationStandardDialog implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.core.dockable.logger.resources" );

    private static final DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss,SSS" );

    private static final String causedBy =
            resources.getResourceString( LoggingEventDialog.class, "exception", "causedBy" );

    private static final String at =
            resources.getResourceString( LoggingEventDialog.class, "exception", "at" );

    private BannerPanel bannerPanel = null;

    private JPanel contentPanel;

    private final LoggingEvent logEvent;

    private ButtonPanel buttonPanel;

    private JButton okButton;

    private JScrollPane messageScrollPane;

    private JPanel dataPanel;

    public LoggingEventDialog(ApplicationFrame applicationFrame, FrameApplication application, LoggingEvent logEvent ) throws HeadlessException
    {
        super(applicationFrame,application);
        this.logEvent = logEvent;
        setModal( true );

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }

    @Override
    public JComponent createBannerPanel()
    {
        if ( bannerPanel == null )
        {
            bannerPanel = new ApplicationBannerPanel(application);

            Level level = logEvent.getLevel();

            String title = resources.getResourceString( LoggingEventDialog.class, "title" );

            Map<String, Object> arguments = new HashMap<String, Object>();
            arguments.put( "levelString",
                           resources.getResourceString( LoggingEventDialog.class, "details", "level",
                                                        level.toString() ) );
            title = StringArgumentMessageFormat.format( title, arguments );
            bannerPanel.setSubtitle( title );

            if ( level.isGreaterOrEqual( Level.ERROR ) == true )
            {
                bannerPanel
                        .setTitleIcon(
                                ( ImageIcon ) resources.getResourceIcon( LoggingEventDialog.class, "icon", "error" ) );
            }
            else if ( level.isGreaterOrEqual( Level.WARN ) == true )
            {
                bannerPanel
                        .setTitleIcon(
                                ( ImageIcon ) resources.getResourceIcon( LoggingEventDialog.class, "icon", "warn" ) );
            }
            else if ( level.isGreaterOrEqual( Level.INFO ) == true )
            {
                bannerPanel
                        .setTitleIcon(
                                ( ImageIcon ) resources.getResourceIcon( LoggingEventDialog.class, "icon", "info" ) );
            }

            bannerPanel.setBackground( getBackground() );
        }

        return bannerPanel;
    }

    @Override
    public JComponent createContentPanel()
    {
        if ( contentPanel == null )
        {
            contentPanel = new JPanel( new BorderLayout() );
            contentPanel.add( BorderLayout.NORTH, createDataPanel() );
            contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );

            if ( logEvent.getThrowableInformation() != null )
            {
                contentPanel.add( BorderLayout.CENTER, createExceptionPanel() );
            }
            else
            {
                setResizable( false );
            }
        }

        return contentPanel;
    }

    private Component createExceptionPanel()
    {
        JPanel exceptionPanel = new JPanel( new BorderLayout() );
        PartialLineBorder partialLineBorder = new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH );

        TitledBorder titledBorder = new TitledBorder( partialLineBorder,
                                                      resources.getResourceString( LoggingEventDialog.class,
                                                                                   "exception", "label" ) );
        exceptionPanel.setBorder( BorderFactory.createCompoundBorder( titledBorder, new EmptyBorder( 5, 5, 5, 5 ) ) );

        String label = "";

        ThrowableInformation throwableInformation = logEvent.getThrowableInformation();

        Throwable throwable = throwableInformation.getThrowable();

        while ( true )
        {
            if ( throwable != throwable.getCause() )
            {
                label += causedBy + " ";
            }

            label += throwable.getClass().getName();

            if ( throwable.getLocalizedMessage() != null )
            {
                label += ": " + throwable.getLocalizedMessage() + "\n";
            }

            StackTraceElement[] stackTrace = throwable.getStackTrace();

            if ( ( stackTrace != null ) && ( stackTrace.length != 0 ) )
            {
                for ( StackTraceElement stackTraceElement : stackTrace )
                {
                    label += "    " + at + " " + stackTraceElement.toString() + "\n";
                }
            }

            if ( ( throwable.getCause() == null ) || ( throwable == throwable.getCause() ) )
            {
                break;
            }
            else
            {
                throwable = throwable.getCause();
            }
        }

        JTextArea textArea = new JTextArea( label );
        textArea.setEditable( false );
        textArea.setOpaque( false );

        JScrollPane jScrollPane = new JScrollPane( textArea );

        exceptionPanel.add( BorderLayout.CENTER, jScrollPane );

        Dimension size = new Dimension( 450, 300 );
        exceptionPanel.setSize( size );
        exceptionPanel.setPreferredSize( size );
        exceptionPanel.setMinimumSize( size );

        return exceptionPanel;
    }

    private JPanel createDataPanel()
    {
        dataPanel = new JPanel( new SpringLayout() );
        Dimension size = new Dimension( 450, 195 );
        dataPanel.setSize( size );
        dataPanel.setPreferredSize( size );
        dataPanel.setMinimumSize( size );

        PartialLineBorder partialLineBorder = new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH );

        TitledBorder titledBorder = new TitledBorder( partialLineBorder,
                                                      resources.getResourceString( LoggingEventDialog.class,
                                                                                   "details", "label" ) );

        dataPanel.setBorder( BorderFactory.createCompoundBorder( titledBorder, new EmptyBorder( 5, 5, 5, 5 ) ) );

        String message = logEvent.getRenderedMessage();
        JTextArea messageLabel = new JTextArea( message );
        messageLabel.setEditable( false );
        messageLabel.setOpaque( false );

        messageScrollPane = new JScrollPane( messageLabel );
        Dimension messageSize = new Dimension( 350, 60 );
        messageScrollPane.setSize( messageSize );
        messageScrollPane.setPreferredSize( messageSize );
        messageScrollPane.setMinimumSize( messageSize );

        JideLabel comp = new JideLabel(
                resources.getResourceString( LoggingEventDialog.class, "details", "message", "label" ) );
        comp.setVerticalTextPosition( SwingConstants.TOP );
        comp.setVerticalAlignment( SwingConstants.TOP );
        dataPanel.add( comp );
        dataPanel.add( messageScrollPane );

        dataPanel.add( new JideLabel(
                resources.getResourceString( LoggingEventDialog.class, "details", "level", "label" ) ) );

        Level level = logEvent.getLevel();

        JideLabel comp1 = new JideLabel(
                resources.getResourceString( LoggingEventDialog.class, "details", "level", level.toString() ) );

        if ( level.isGreaterOrEqual( Level.ERROR ) == true )
        {
            comp1.setIcon( resources.getResourceIcon( LoggingEventDialog.class, "details", "level", "icon", "error" ) );
        }
        else if ( level.isGreaterOrEqual( Level.WARN ) == true )
        {
            comp1.setIcon( resources.getResourceIcon( LoggingEventDialog.class, "details", "level", "icon", "warn" ) );
        }
        else if ( level.isGreaterOrEqual( Level.INFO ) == true )
        {
            comp1.setIcon( resources.getResourceIcon( LoggingEventDialog.class, "details", "level", "icon", "info" ) );
        }

        LocationInfo locationInformation = logEvent.getLocationInformation();

        dataPanel.add( comp1 );

        dataPanel.add( new JideLabel(
                resources.getResourceString( LoggingEventDialog.class, "details", "time", "label" ) ) );
        dataPanel.add( new JideLabel( dateFormat.format( logEvent.getTimeStamp() ) ) );

        dataPanel.add( new JideLabel(
                resources.getResourceString( LoggingEventDialog.class, "details", "class", "label" ) ) );
        dataPanel.add( new JideLabel( locationInformation.getClassName() ) );

        dataPanel.add( new JideLabel(
                resources.getResourceString( LoggingEventDialog.class, "details", "method", "label" ) ) );
        dataPanel.add( new JideLabel( locationInformation.getMethodName() ) );

        dataPanel.add( new JideLabel(
                resources.getResourceString( LoggingEventDialog.class, "details", "line", "label" ) ) );
        dataPanel.add( new JideLabel( locationInformation.getLineNumber()) );

        SpringLayoutUtilities.makeCompactGrid( dataPanel, dataPanel.getComponentCount() / 2, 2, 0, 0, 5, 5 );

        return dataPanel;
    }

    @Override
    public ButtonPanel createButtonPanel()
    {
        if ( buttonPanel == null )
        {
            buttonPanel = new ButtonPanel();

            okButton = new JButton( resources.getResourceString( LoggingEventDialog.class, "button", "ok" ) );
            okButton.addActionListener( this );

            buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );

            buttonPanel.setSizeConstraint( ButtonPanel.NO_LESS_THAN );

            buttonPanel.setBorder(
                    BorderFactory.createCompoundBorder( new PartialEtchedBorder( PartialEtchedBorder.LOWERED,
                                                                                 PartialSide.NORTH ),
                                                        new EmptyBorder( 5, 5, 5, 5 ) ) );
        }

        return buttonPanel;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if ( e.getSource() == okButton )
        {
            dispose();
        }
    }

    public void doModal()
    {
        setLocationRelativeTo( getOwner() );
        pack();

        Dimension size = getSize();
        setMinimumSize( size );
        setPreferredSize( size );

        setVisible( true );

        dispose();
    }
}
