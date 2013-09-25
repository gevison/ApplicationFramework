package ge.framework.application.frame.multi.dialog;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.StyledLabel;
import ge.framework.application.frame.core.dialog.panel.ApplicationBannerPanel;
import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.application.frame.multi.dialog.buttons.ApplicationPropertiesButton;
import ge.framework.application.frame.multi.dialog.buttons.NewFrameButton;
import ge.framework.application.frame.multi.dialog.buttons.OpenFrameButton;
import ge.framework.application.frame.multi.dialog.panel.RecentPanel;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 17/01/13
 * Time: 10:29
 */
public class InitialDialog extends StandardDialog implements ListSelectionListener
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.application.frame.multi" );

    private ApplicationBannerPanel bannerPanel = null;

    private JPanel contentPanel;

    private RecentPanel recentListPanel;

    private MultiFrameApplication application;

    public InitialDialog( MultiFrameApplication application )
    {
        super();
        this.application = application;

        initializeDialog();
    }

    private void initializeDialog()
    {
        setModal( true );

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        setTitle( application.getName() );
        setIconImage( application.getSmallImage() );

        setSize( 700, 500 );
        setMinimumSize( new Dimension( 700, 500 ) );
    }

    @Override
    public JComponent createBannerPanel()
    {
        if ( bannerPanel == null )
        {
            bannerPanel = new ApplicationBannerPanel( application );

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

            contentPanel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ) );

            contentPanel.add( BorderLayout.WEST, createRecentList() );
            contentPanel.add( BorderLayout.CENTER, createActionsPanel() );
        }

        return contentPanel;
    }

    private Component createActionsPanel()
    {
        JPanel actionsPanel = new JPanel( new BorderLayout() );

        actionsPanel.setBorder( new EmptyBorder( 10, 5, 10, 10 ) );

        actionsPanel.add( BorderLayout.CENTER, createShortcutPanel() );

        return actionsPanel;
    }

    private Component createShortcutPanel()
    {
        JPanel shortcutPanel = new JPanel( new BorderLayout() );
        shortcutPanel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.ALL ) );

        StyledLabel titleLabel =
                new StyledLabel( resources.getResourceString( InitialDialog.class, "quickstart", "label" ) );
        titleLabel.setHorizontalAlignment( SwingConstants.CENTER );
        titleLabel.setVerticalAlignment( SwingConstants.CENTER );
        Font font = new Font( titleLabel.getFont().getFontName(), Font.BOLD, titleLabel.getFont().getSize() + 4 );
        titleLabel.setFont( font );
        titleLabel.setBackground( Color.LIGHT_GRAY );
        titleLabel.setOpaque( true );
        titleLabel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.SOUTH ) );

        shortcutPanel.add( BorderLayout.NORTH, titleLabel );
        shortcutPanel.add( BorderLayout.CENTER, createQuickStartButtons() );

        return shortcutPanel;
    }

    private Component createQuickStartButtons()
    {
        JPanel quickStartButtonsPanel = new JPanel( new GridLayout( 5, 1, 50, 20 ) );
        quickStartButtonsPanel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ) );

        quickStartButtonsPanel.setBorder( new EmptyBorder( 10, 50, 10, 50 ) );

        quickStartButtonsPanel.add( new NewFrameButton( this ) );
        quickStartButtonsPanel.add( new OpenFrameButton( this ) );
        quickStartButtonsPanel.add( new ApplicationPropertiesButton( this ) );

        return quickStartButtonsPanel;
    }

    private Component createRecentList()
    {
        recentListPanel = new RecentPanel( application );

        recentListPanel.addListSelectionListener( this );

        return recentListPanel;
    }

    @Override
    public ButtonPanel createButtonPanel()
    {
        return null;
    }

    public void doModal()
    {
        setLocationRelativeTo( getOwner() );
        setResizable( false );
        pack();

        setVisible( true );

        dispose();
    }

    @Override
    public void valueChanged( ListSelectionEvent e )
    {
        Object source = e.getSource();

        if ( source == recentListPanel.getList() )
        {
            FrameInstanceDetailsObject frameInstanceDetailsObject = recentListPanel.getSelectedValue();

            if ( frameInstanceDetailsObject != null )
            {
                if ( application.openFrame( null, frameInstanceDetailsObject, true ) == true )
                {
                    dispose();
                }
            }
        }
    }

    public MultiFrameApplication getApplication()
    {
        return application;
    }
}
