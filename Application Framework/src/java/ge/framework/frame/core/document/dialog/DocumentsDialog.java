package ge.framework.frame.core.document.dialog;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.application.core.Application;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.document.ApplicationDocumentComponent;
import ge.framework.frame.core.document.dialog.panel.DocumentPanel;
import ge.utils.bundle.Resources;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 12:13
 */
public class DocumentsDialog extends StandardDialog implements ActionListener
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.frame.core" );

    private JPanel contentPanel;

    private boolean result = false;

    private ButtonPanel buttonPanel;

    private JButton okButton;

    private JButton cancelButton;

    private DocumentPanel documentPanel;

    private ApplicationFrame applicationFrame;

    private ApplicationDocumentComponent documentComponent;

    public DocumentsDialog( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
        this.applicationFrame = applicationFrame;

        initializeDialog();
    }

    private void initializeDialog()
    {
        setModal( true );

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        setSize( 450, 500 );
        setMinimumSize( new Dimension( 450, 500 ) );

        setTitle( resources.getResourceString( DocumentsDialog.class, "title" ) );

        Application application = applicationFrame.getApplication();

        setIconImage( application.getSmallImage() );
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
            documentPanel = new DocumentPanel( applicationFrame );

            contentPanel = new JPanel( new BorderLayout() );

            contentPanel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ) );

            contentPanel.add( BorderLayout.CENTER, documentPanel );
        }

        return contentPanel;
    }

    @Override
    public ButtonPanel createButtonPanel()
    {
        if ( buttonPanel == null )
        {
            buttonPanel = new ButtonPanel();

            okButton =
                    new JButton( resources.getResourceString( DocumentsDialog.class, "button", "ok" ) );
            okButton.addActionListener( this );

            buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );

            cancelButton = new JButton(
                    resources.getResourceString( DocumentsDialog.class, "button", "cancel" ) );
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
        setResizable( false );
        pack();
        setLocationRelativeTo( getOwner() );

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
            documentComponent = documentPanel.getSelectedValue();

            if ( documentComponent != null )
            {
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

    public ApplicationDocumentComponent getDocumentComponent()
    {
        return documentComponent;
    }
}
