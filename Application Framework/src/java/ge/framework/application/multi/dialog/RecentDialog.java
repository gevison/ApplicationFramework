package ge.framework.application.multi.dialog;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.application.core.dialog.ApplicationStandardDialog;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.application.multi.dialog.panel.RecentPanel;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 17/01/13
 * Time: 10:29
 */
public class RecentDialog extends ApplicationStandardDialog implements ActionListener
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.application.multi" );

    private JPanel contentPanel;

    private FrameInstanceDetailsObject frameInstanceDetailsObject;

    private boolean result = false;

    private ButtonPanel buttonPanel;

    private JButton okButton;

    private JButton cancelButton;

    private RecentPanel recentListPanel;

    private ApplicationFrame applicationFrame;

    public RecentDialog(ApplicationFrame applicationFrame)
    {
        super( applicationFrame, applicationFrame.getApplication() );
        this.applicationFrame = applicationFrame;

        initializeDialog();
    }

    private void initializeDialog()
    {
        setModal( true );

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        setTitle( application.getName() );

        setSize( 450, 500 );
        setMinimumSize( new Dimension( 450, 500 ) );
    }

    @Override
    public JComponent createBannerPanel()
    {
        return null;
    }

    @Override
    public JComponent createContentPanel()
    {
        if ( contentPanel == null )
        {
            contentPanel = new JPanel( new BorderLayout() );

            contentPanel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ) );

            contentPanel.add( BorderLayout.CENTER, createRecentList() );
        }

        return contentPanel;
    }

    private Component createRecentList()
    {
        recentListPanel = new RecentPanel( ( MultiFrameApplication ) application );

        return recentListPanel;
    }

    @Override
    public ButtonPanel createButtonPanel()
    {
        if ( buttonPanel == null )
        {
            buttonPanel = new ButtonPanel();

            okButton = new JButton( resources.getResourceString( RecentDialog.class, "button", "ok" ) );
            okButton.addActionListener( this );

            buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );

            cancelButton = new JButton( resources.getResourceString( RecentDialog.class, "button", "cancel" ) );
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
    public void actionPerformed( ActionEvent e )
    {
        Object source = e.getSource();

        if ( source == okButton )
        {
            frameInstanceDetailsObject = recentListPanel.getSelectedValue();

            if ( frameInstanceDetailsObject != null )
            {
                MultiFrameApplication multiFrameApplication = ( MultiFrameApplication ) application;
                if ( multiFrameApplication.doesFrameConfigurationFileExist( frameInstanceDetailsObject ) == false )
                {
                    MissingLocationDialog missingLocationDialog =
                            new MissingLocationDialog( applicationFrame, frameInstanceDetailsObject.getName() );

                    if ( missingLocationDialog.showMessage() == false )
                    {
                        return;
                    }
                }
                else if ( multiFrameApplication.isFrameConfigurationFileLocked( frameInstanceDetailsObject ) == true )
                {
                    LockedLocationDialog lockedLocationDialog =
                            new LockedLocationDialog( applicationFrame, frameInstanceDetailsObject.getName() );

                    lockedLocationDialog.doModal();

                    return;
                }

                result = true;

                dispose();
            }
        }
        else if ( source == cancelButton )
        {
            result = false;
            dispose();
        }
    }

    public FrameInstanceDetailsObject getFrameInstanceDetailsObject()
    {
        return frameInstanceDetailsObject;
    }
}
