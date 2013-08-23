package ge.framework.application.multi.dialog;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.list.StyledListCellRenderer;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideComboBox;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.application.core.dialog.ApplicationStandardDialog;
import ge.framework.application.multi.MultiApplication;
import ge.framework.application.multi.dialog.utils.TypeFolderChooser;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.multi.objects.FrameDefinition;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;
import ge.utils.problem.ProblemBannerPanel;
import ge.utils.problem.enums.ProblemType;
import ge.utils.problem.object.Problem;
import ge.utils.problem.object.ProblemList;

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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class OpenDialog extends ApplicationStandardDialog implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    private ProblemBannerPanel bannerPanel = null;

    private ButtonPanel buttonPanel;

    private JButton okButton;

    private JButton cancelButton;

    private boolean result;

    private List<FrameDefinition> frameDefinitions;

    private JPanel contentPanel;

    private JideComboBox frameDefinitionField;

    private JTextField pathField;

    private JButton browseButton;

    private FrameInstanceDetailsObject frameInstanceDetailsObject;

    public OpenDialog( ApplicationFrame applicationFrame, MultiApplication application )
    {
        super(applicationFrame, application);

        initialiseDialog();
    }

    private void initialiseDialog()
    {
        MultiApplication multiApplication = ( MultiApplication ) application;
        frameDefinitions = multiApplication.getFrameDefinitions();

        setModal( true );

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        setTitle( resources.getResourceString( OpenDialog.class, "title" ) );
    }

    @Override
    public JComponent createBannerPanel()
    {
        if ( bannerPanel == null )
        {
            bannerPanel = new ProblemBannerPanel();
            bannerPanel.setTitle( resources.getResourceString( OpenDialog.class, "banner", "title" ) );
            bannerPanel.setSubtitle( resources.getResourceString( OpenDialog.class, "banner", "subtitle" ) );
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

            frameDefinitionField = new JideComboBox( comboBoxModel );
            frameDefinitionField.setRenderer( new FrameDetailsObjectRenderer() );

            pathField = new JTextField( "", 40 );
            pathField.setEditable( false );

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
                    .add( new JLabel( resources.getResourceString( OpenDialog.class, "type", "label" ) ),
                          JideBoxLayout.FIX );
            contentPanel.add( frameDefinitionField, JideBoxLayout.FIX );

            contentPanel.add( Box.createVerticalStrut( 6 ), JideBoxLayout.FIX );
            contentPanel
                    .add( new JLabel( resources.getResourceString( OpenDialog.class, "path", "label" ) ),
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

            okButton = new JButton( resources.getResourceString( OpenDialog.class, "button", "ok" ) );
            okButton.addActionListener( this );

            buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );

            cancelButton = new JButton( resources.getResourceString( OpenDialog.class, "button", "cancel" ) );
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

        setVisible( true );

        dispose();

        return result;
    }

    @Override
    public void actionPerformed( ActionEvent event )
    {
        Object source = event.getSource();

        ProblemList problems = new ProblemList();

        if ( source == okButton )
        {
            frameInstanceDetailsObject = new FrameInstanceDetailsObject();
            frameInstanceDetailsObject.setFrameDefinition( ( FrameDefinition ) frameDefinitionField.getSelectedItem() );
            frameInstanceDetailsObject.setLocation( new File( pathField.getText() ) );

            result = true;

            dispose();
        }
        else if ( source == cancelButton )
        {
            result = false;
            dispose();
        }
        else if ( source == browseButton )
        {
            TypeFolderChooser folderChooser =
                    new TypeFolderChooser( ( FrameDefinition ) frameDefinitionField.getSelectedItem() );

            int result = folderChooser.showOpenDialog( this );

            if ( result == TypeFolderChooser.APPROVE_OPTION )
            {
                File pathFile = folderChooser.getSelectedFile();

                FrameDefinition type = ( FrameDefinition ) frameDefinitionField.getSelectedItem();

                if ( type.isConfigurationFileLocked( pathFile ) == true )
                {
                    Problem problem = new Problem( resources, OpenDialog.class, ProblemType.ERROR,
                                                   "problem", "locked", "path" );

                    problem.putArgument( "frameDefinition", type.getName() );

                    problems.add( problem );
                }

                pathField.setText( pathFile.getPath() );
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

    private class FrameDetailsObjectRenderer extends StyledListCellRenderer
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
