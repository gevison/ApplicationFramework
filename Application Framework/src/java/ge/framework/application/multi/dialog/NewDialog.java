package ge.framework.application.multi.dialog;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.list.StyledListCellRenderer;
import com.jidesoft.swing.FolderChooser;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideComboBox;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.application.core.Application;
import ge.framework.application.multi.MultiApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.multi.objects.FrameDefinition;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;
import ge.utils.file.FileUtils;
import ge.utils.message.MessageDialog;
import ge.utils.message.enums.MessageLevel;
import ge.utils.message.enums.MessageResult;
import ge.utils.message.enums.MessageType;
import ge.utils.problem.ProblemBannerPanel;
import ge.utils.problem.enums.ProblemType;
import ge.utils.problem.object.Problem;
import ge.utils.problem.object.ProblemList;
import ge.utils.text.StringArgumentMessageFormat;
import org.apache.log4j.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewDialog extends ApplicationStandardDialog implements ActionListener,
                                                                    CaretListener
{
    private Logger logger = Logger.getLogger( NewDialog.class );

    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    private static final String DEFAULT_NAME =
            resources.getResourceString( NewDialog.class, "default", "name" );

    private static final File USER_HOME_PATH = new File( System.getProperty( "user.home" ) );

    private static final File DEFAULT_PATH = new File( USER_HOME_PATH, DEFAULT_NAME );

    private ProblemBannerPanel bannerPanel = null;

    private JPanel contentPanel;

    private ButtonPanel buttonPanel;

    private JButton okButton;

    private JButton cancelButton;

    private boolean result;

    private JideComboBox frameDefinitionsField;

    private JTextField pathField;

    private JButton browseButton;

    private JTextField nameField;

    private boolean nameEdited = false;

    private boolean pathEdited = false;

    private List<FrameDefinition> frameDefinitions;

    private FrameDefinition frameDefinition;

    private FrameInstanceDetailsObject frameInstanceDetailsObject;

    public NewDialog( ApplicationFrame applicationFrame, MultiApplication application, FrameDefinition frameDefinition )
    {
        super( applicationFrame, application);

        this.frameDefinition = frameDefinition;

        initialiseDialog();
    }

    private void initialiseDialog()
    {
        MultiApplication multiApplication = ( MultiApplication ) application;
        frameDefinitions = multiApplication.getFrameDefinitions();

        setModal( true );

        setTitle( resources.getResourceString( NewDialog.class, "title" ) );
    }

    @Override
    public JComponent createBannerPanel()
    {
        if ( bannerPanel == null )
        {
            bannerPanel = new ProblemBannerPanel();
            bannerPanel.setTitle( resources.getResourceString( NewDialog.class, "banner", "title" ) );
            bannerPanel.setSubtitle( resources.getResourceString( NewDialog.class, "banner", "subtitle" ) );
            bannerPanel.setTitleIcon( ( ImageIcon ) application.getLargeIcon() );
            bannerPanel.setTitleIconLocation( SwingConstants.WEST );
            bannerPanel.setBackground( getBackground() );
            bannerPanel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.SOUTH ) );
        }
        return bannerPanel;
    }

    @Override
    public JComponent createContentPanel()
    {
        if ( contentPanel == null )
        {
            DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();

            for ( FrameDefinition frameDefinition : frameDefinitions )
            {
                comboBoxModel.addElement( frameDefinition );
            }

            frameDefinitionsField = new JideComboBox( comboBoxModel );
            frameDefinitionsField.setRenderer( new FrameDefinitionRenderer() );

            if ( ( frameDefinition != null ) && ( frameDefinitions.contains( frameDefinition ) == true ) )
            {
                frameDefinitionsField.setSelectedItem( frameDefinition );
            }

            nameField = new JTextField( DEFAULT_NAME, 40 );
            nameField.addCaretListener( this );

            pathField = new JTextField( DEFAULT_PATH.toString(), 40 );
            pathField.addCaretListener( this );

            browseButton = new JButton( "..." );
            browseButton.setMinimumSize( new Dimension( 20, 20 ) );
            browseButton.setPreferredSize( new Dimension( 20, 20 ) );
            browseButton.addActionListener( this );

            JPanel projectPathPanel = new JPanel( new BorderLayout() );
            projectPathPanel.add( BorderLayout.CENTER, pathField );
            projectPathPanel.add( BorderLayout.EAST, browseButton );

            contentPanel = new JPanel();
            contentPanel.setLayout( new JideBoxLayout( contentPanel, JideBoxLayout.Y_AXIS, 3 ) );
            contentPanel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );

            contentPanel
                    .add( new JLabel( resources.getResourceString( NewDialog.class, "type", "label" ) ),
                          JideBoxLayout.FIX );
            contentPanel.add( frameDefinitionsField, JideBoxLayout.FIX );

            contentPanel
                    .add( new JLabel( resources.getResourceString( NewDialog.class, "name", "label" ) ),
                          JideBoxLayout.FIX );
            contentPanel.add( nameField, JideBoxLayout.FIX );
            contentPanel.add( Box.createVerticalStrut( 6 ), JideBoxLayout.FIX );
            contentPanel
                    .add( new JLabel( resources.getResourceString( NewDialog.class, "path", "label" ) ),
                          JideBoxLayout.FIX );
            contentPanel.add( projectPathPanel, JideBoxLayout.FIX );
            contentPanel.add( Box.createVerticalStrut( 6 ), JideBoxLayout.FIX );
        }

        return contentPanel;
    }

    @Override
    public ButtonPanel createButtonPanel()
    {
        if ( buttonPanel == null )
        {
            buttonPanel = new ButtonPanel();

            okButton = new JButton( resources.getResourceString( NewDialog.class, "button", "ok" ) );
            okButton.addActionListener( this );

            buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );

            cancelButton = new JButton( resources.getResourceString( NewDialog.class, "button", "cancel" ) );
            cancelButton.addActionListener( this );

            buttonPanel.addButton( cancelButton, ButtonPanel.CANCEL_BUTTON );

            buttonPanel.setSizeConstraint( ButtonPanel.NO_LESS_THAN );

            buttonPanel.setBorder(
                    BorderFactory.createCompoundBorder( new PartialEtchedBorder( PartialEtchedBorder.LOWERED,
                                                                                 PartialSide.NORTH ),
                                                        new EmptyBorder( 5, 5, 5, 5 ) ) );
        }

        return buttonPanel;
    }

    public boolean doModal()
    {
        setLocationRelativeTo( getOwner() );
        setResizable( false );
        pack();
        nameField.requestFocus();
        try
        {
            validateDetails();
        }
        catch ( IOException e )
        {
            logger.error( "Failed to validate Details.", e );
        }

        setVisible( true );

        dispose();

        return result;
    }

    @Override
    public void actionPerformed( ActionEvent event )
    {
        Object source = event.getSource();

        if ( source == okButton )
        {
            try
            {
                validateDetails();
            }
            catch ( IOException e )
            {
                logger.error( "Failed to validate Details.", e );
            }

            if ( okButton.isEnabled() == true )
            {
                File location = new File( pathField.getText() );

                FrameInstanceDetailsObject detailsObject = new FrameInstanceDetailsObject();
                FrameDefinition frameDefinition = ( FrameDefinition ) frameDefinitionsField.getSelectedItem();
                detailsObject.setFrameDefinition( frameDefinition );
                detailsObject.setName( nameField.getText() );
                detailsObject.setLocation( location );

                if ( location.exists() == false )
                {
                    String message = resources.getResourceString( NewDialog.class, "message", "nodirectory" );

                    Map<String, Object> arguments = new HashMap<String, Object>();
                    arguments.put( "frameDefinition", frameDefinition.getName() );

                    message = StringArgumentMessageFormat.format( message, arguments );

                    MessageDialog messageDialog = new MessageDialog( this, message, MessageType.YES_NO,
                                                                     MessageLevel.WARNING );

                    if ( messageDialog.doModal() == MessageResult.YES )
                    {
                        frameInstanceDetailsObject = detailsObject;
                        setDialogResult( RESULT_AFFIRMED );
                        result = true;
                        dispose();
                    }
                }
                else if ( detailsObject.isDirectory() == true )
                {
                    String message = resources.getResourceString( NewDialog.class, "message", "alreadyexists" );

                    Map<String, Object> arguments = new HashMap<String, Object>();
                    arguments.put( "frameDefinition", frameDefinition.getName() );

                    message = StringArgumentMessageFormat.format( message, arguments );

                    MessageDialog messageDialog = new MessageDialog( this, message, MessageType.YES_NO,
                                                                     MessageLevel.WARNING );

                    if ( messageDialog.doModal() == MessageResult.YES )
                    {
                        frameInstanceDetailsObject = detailsObject;
                        setDialogResult( RESULT_AFFIRMED );
                        result = true;
                        dispose();
                    }
                }
                else
                {
                    frameInstanceDetailsObject = detailsObject;
                    setDialogResult( RESULT_AFFIRMED );
                    result = true;
                    dispose();
                }
            }
        }
        else if ( source == cancelButton )
        {
            result = false;
            dispose();
        }
        else if ( source == browseButton )
        {
            String pathFieldText = pathField.getText();

            FolderChooser folderChooser;

            if ( ( pathFieldText != null ) && ( pathFieldText.isEmpty() == false ) )
            {
                File pathFile = new File( pathFieldText );

                while ( ( pathFile != null ) && ( pathFile.exists() == false ) &&
                        ( pathFile.isDirectory() == false ) )
                {
                    pathFile = pathFile.getParentFile();
                }

                if ( pathFile != null )
                {
                    folderChooser = new FolderChooser( pathFile );
                }
                else
                {
                    folderChooser = new FolderChooser();
                }
            }
            else
            {
                folderChooser = new FolderChooser();
            }

            folderChooser.setApproveButtonMnemonic( 's' );
            folderChooser.setRecentListVisible( false );
            folderChooser.setNavigationFieldVisible( true );
            folderChooser.setMultiSelectionEnabled( false );

            if ( folderChooser.showDialog( this, "Select" ) == FolderChooser.APPROVE_OPTION )
            {
                File selectedFile = folderChooser.getSelectedFile();

                FileSystemView fileSystemView = FileSystemView.getFileSystemView();

                if ( fileSystemView.isFileSystem( selectedFile ) == true )
                {
                    pathField.setText( selectedFile.getPath() );
                    pathEdited = true;

                    if ( nameEdited == false )
                    {
                        nameField.setText( selectedFile.getName() );
                    }
                }
            }
        }

        try
        {
            validateDetails();
        }
        catch ( IOException e )
        {
            logger.error( "Failed to validate Project Details.", e );
        }
    }

    @Override
    public void caretUpdate( CaretEvent event )
    {
        Object source = event.getSource();

        if ( source == nameField )
        {
            String nameText = nameField.getText();

            if ( pathEdited == false )
            {
                if ( ( FileUtils.isFileNameValid( nameText ) == true ) &&
                        ( nameText.startsWith( "." ) == false ) )
                {
                    File file = new File( USER_HOME_PATH, nameText );

                    pathField.removeCaretListener( this );
                    pathField.setText( file.getPath() );
                    pathField.addCaretListener( this );
                }
            }

            nameEdited = true;
        }
        else if ( source == pathField )
        {
            if ( nameEdited == false )
            {
                File projectPathFile = new File( pathField.getText() );
                nameField.removeCaretListener( this );
                nameField.setText( projectPathFile.getName() );
                nameField.addCaretListener( this );
            }

            pathEdited = true;
        }

        try
        {
            validateDetails();
        }
        catch ( IOException e )
        {
            logger.error( "Failed to validate Project Details.", e );
        }
    }

    private void validateDetails() throws IOException
    {
        ProblemList problems = new ProblemList();

        String nameText = nameField.getText();

        if ( nameText == null )
        {
            problems.add(
                    new Problem( resources, NewDialog.class, ProblemType.ERROR, "problem", "null", "name" ) );
        }
        else if ( nameText.length() == 0 )
        {
            problems.add( new Problem( resources, NewDialog.class, ProblemType.ERROR,
                                       "problem", "blank", "name" ) );
        }
        else if ( ( FileUtils.isFileNameValid( nameText ) == false ) ||
                ( nameText.startsWith( "." ) == true ) )
        {
            problems.add( new Problem( resources, NewDialog.class, ProblemType.ERROR,
                                       "problem", "invalid", "name" ) );
        }

        String pathText = pathField.getText();

        if ( pathText == null )
        {
            problems.add( new Problem( resources, NewDialog.class, ProblemType.ERROR,
                                       "problem", "null", "path" ) );
        }
        else if ( pathText.length() == 0 )
        {
            problems.add( new Problem( resources, NewDialog.class, ProblemType.ERROR,
                                       "problem", "blank", "path" ) );
        }
        else
        {
            FrameDefinition frameDefinition = ( FrameDefinition ) frameDefinitionsField.getSelectedItem();
            File pathFile = new File( pathText );

            if ( FileUtils.isFileValidName( pathFile ) == false )
            {
                problems.add( new Problem( resources, NewDialog.class, ProblemType.ERROR,
                                           "problem", "invalid", "path" ) );
            }
            else if ( pathFile.exists() == false )
            {
                problems.add( new Problem( resources, NewDialog.class, ProblemType.WARNING,
                                           "problem", "notthere", "path" ) );
            }
            else if ( frameDefinition.isDirectory( pathFile ) == true )
            {
                if ( frameDefinition.isConfigurationFileLocked( pathFile ) == true )
                {
                    Problem problem = new Problem( resources, NewDialog.class, ProblemType.ERROR,
                                                   "problem", "locked", "path" );

                    problem.putArgument( "frameDefinition", frameDefinition.getName() );

                    problems.add( problem );
                }
                else
                {
                    Problem problem = new Problem( resources, NewDialog.class, ProblemType.WARNING,
                                                   "problem", "exists", "path" );

                    problem.putArgument( "frameDefinition", frameDefinition.getName() );

                    problems.add( problem );
                }
            }
        }

        if ( problems.isEmpty() == false )
        {
            bannerPanel.setProblems( problems );

            boolean error = false;

            for ( Problem problem : problems )
            {
                if ( problem.getProblemType() == ProblemType.ERROR )
                {
                    error = true;
                }
            }

            if ( error == true )
            {
                okButton.setEnabled( false );
            }
            else
            {
                okButton.setEnabled( true );
            }
        }
        else
        {
            bannerPanel.setProblems( null );
            okButton.setEnabled( true );
        }

        pack();
    }

    public FrameInstanceDetailsObject getFrameInstanceDetailsObject()
    {
        return frameInstanceDetailsObject;
    }

    private class FrameDefinitionRenderer extends StyledListCellRenderer
    {
        @Override
        protected void customizeStyledLabel( JList list, Object value, int index, boolean isSelected,
                                             boolean cellHasFocus )
        {
            if ( value instanceof FrameDefinition )
            {
                FrameDefinition frameDefinition = ( FrameDefinition ) value;

                super.customizeStyledLabel( list, frameDefinition.getName(), index, isSelected,
                                            cellHasFocus );

                setIcon( frameDefinition.getSmallIcon() );
            }
            else
            {
                super.customizeStyledLabel( list, value, index, isSelected, cellHasFocus );
            }
        }
    }
}
