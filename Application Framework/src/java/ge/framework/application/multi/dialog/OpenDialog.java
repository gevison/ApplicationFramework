package ge.framework.application.multi.dialog;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.application.core.dialog.ApplicationStandardDialog;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.application.multi.dialog.utils.TypeFolderChooser;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;
import ge.utils.problem.ProblemBannerPanel;
import ge.utils.problem.enums.ProblemType;
import ge.utils.problem.object.Problem;
import ge.utils.problem.object.ProblemList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
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

public class OpenDialog extends ApplicationStandardDialog implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.multi" );

    private ProblemBannerPanel bannerPanel = null;

    private ButtonPanel buttonPanel;

    private JButton okButton;

    private JButton cancelButton;

    private boolean result;

    private JPanel contentPanel;

    private JTextField pathField;

    private JButton browseButton;

    private FrameInstanceDetailsObject frameInstanceDetailsObject;

    public OpenDialog( ApplicationFrame applicationFrame, MultiFrameApplication application )
    {
        super( applicationFrame, application );

        initialiseDialog();
    }

    private void initialiseDialog()
    {
        MultiFrameApplication multiFrameApplication = ( MultiFrameApplication ) application;

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
            MultiFrameApplication multiFrameApplication = ( MultiFrameApplication ) application;
            TypeFolderChooser folderChooser =
                    new TypeFolderChooser( multiFrameApplication );

            int result = folderChooser.showOpenDialog( this );

            if ( result == TypeFolderChooser.APPROVE_OPTION )
            {
                File pathFile = folderChooser.getSelectedFile();

                if ( multiFrameApplication.isFrameLocationLocked( pathFile ) == true )
                {
                    Problem problem = new Problem( resources, OpenDialog.class, ProblemType.ERROR,
                                                   "problem", "locked", "path" );

                    problem.putArgument( "frameDefinition", multiFrameApplication.getFrameName() );

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
}
