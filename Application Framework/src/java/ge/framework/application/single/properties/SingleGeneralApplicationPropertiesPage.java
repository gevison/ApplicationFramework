package ge.framework.application.single.properties;

import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.application.single.objects.SingleApplicationConfiguration;
import ge.utils.bundle.Resources;
import ge.utils.problem.object.Problem;
import ge.utils.properties.PropertiesDialogPage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/02/13
 * Time: 18:34
 */
public class SingleGeneralApplicationPropertiesPage extends PropertiesDialogPage<SingleApplicationConfiguration>
        implements
        ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.single" );

    private JCheckBox confirmExit;

    public SingleGeneralApplicationPropertiesPage()
    {
        super( "general" );
    }

    @Override
    public String getPageTitle()
    {
        return resources.getResourceString( SingleGeneralApplicationPropertiesPage.class, "title" );
    }

    @Override
    public Icon getPageIcon()
    {
        return resources.getResourceIcon( SingleGeneralApplicationPropertiesPage.class, "icon" );
    }

    @Override
    protected JComponent createContentPanel()
    {
        JPanel content = new JPanel( new BorderLayout() );

        JPanel componentPanel = new JPanel( new GridLayout( 3, 1 ) );

        componentPanel.add( createStartupShutdownSection() );

        content.add( BorderLayout.NORTH, componentPanel );

        return content;
    }

    private Component createStartupShutdownSection()
    {
        confirmExit =
                new JCheckBox( resources.getResourceString( SingleGeneralApplicationPropertiesPage.class,
                                                            "confirmExit" ) );
        confirmExit.addActionListener( this );

        JPanel startupShutdownPanel = new JPanel( new GridLayout( 3, 1 ) );
        startupShutdownPanel.setBorder(
                new TitledBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ),
                                  resources.getResourceString( SingleGeneralApplicationPropertiesPage.class,
                                                               "startupShutdown" ) ) );

        startupShutdownPanel.add( confirmExit );

        return startupShutdownPanel;
    }

    @Override
    public void setCurrentValues( SingleApplicationConfiguration applicationConfiguration )
    {
        confirmExit.setSelected( applicationConfiguration.isAskBeforeExit() );
    }

    @Override
    public List<Problem> validateProperties()
    {
        return null;
    }

    @Override
    public void updateProperties( SingleApplicationConfiguration applicationConfiguration )
    {
        applicationConfiguration.setAskBeforeExit( confirmExit.isSelected() );

    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object source = e.getSource();

        if ( source == confirmExit )
        {
            firePropertyValueChangedEvent();
        }
    }
}
