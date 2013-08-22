package ge.framework.frame.multi.dialog;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.framework.frame.multi.dialog.properties.AbstractFramePropertiesPage;
import ge.framework.frame.multi.objects.FrameConfiguration;
import ge.framework.frame.multi.objects.FrameDefinition;
import ge.utils.bundle.Resources;
import ge.utils.properties.PropertiesDialog;
import ge.utils.properties.PropertiesDialogPage;
import ge.utils.text.StringArgumentMessageFormat;

import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 18/02/2013
 * Time: 21:14
 */
public class FramePropertiesDialog extends PropertiesDialog<FrameConfiguration>
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.frame.multi" );

    private MultiApplicationFrame applicationFrame;

    public FramePropertiesDialog( MultiApplicationFrame applicationFrame )
    {
        super( applicationFrame, applicationFrame.getFrameConfiguration() );
        this.applicationFrame = applicationFrame;

        setSize( 700, 500 );
        setMinimumSize( new Dimension( 700, 500 ) );

        FrameDefinition frameDefinition = applicationFrame.getFrameDefinition();

        setIconImage( frameDefinition.getSmallImage() );
        setTitle( getBannerTitle() );
    }

    @Override
    protected final String getBannerTitle()
    {
        FrameConfiguration frameConfiguration = applicationFrame.getFrameConfiguration();

        String title = resources.getResourceString( FramePropertiesDialog.class, "title" );

        FrameDefinition frameDefinition = applicationFrame.getFrameDefinition();

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "frameDefinition", frameDefinition.getName() );
        arguments.put( "configName", frameConfiguration.getName() );
        title = StringArgumentMessageFormat.format( title, arguments );

        return title;
    }

    @Override
    protected final String getBannerSubTitle()
    {
        return null;
    }

    @Override
    protected final ImageIcon getBannerIcon()
    {
        FrameDefinition frameDefinition = applicationFrame.getFrameDefinition();
        return ( ImageIcon ) frameDefinition.getLargeIcon();
    }

    @Override
    protected List<PropertiesDialogPage> getPages()
    {
        return applicationFrame.getFrameConfigurationPages();
    }
}
