package ge.framework.application.frame.multi.dialog;

import ge.framework.application.frame.core.FrameApplication;
import ge.framework.application.frame.multi.MultiFrameApplicationFrame;
import ge.framework.application.frame.multi.objects.FrameConfiguration;
import ge.utils.bundle.Resources;
import ge.utils.properties.PropertiesDialog;
import ge.utils.properties.PropertiesDialogPage;
import ge.utils.text.StringArgumentMessageFormat;

import javax.swing.ImageIcon;
import java.awt.Dimension;
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
            "ge.framework.application.frame.multi" );

    private MultiFrameApplicationFrame applicationFrame;

    public FramePropertiesDialog( MultiFrameApplicationFrame applicationFrame )
    {
        super( applicationFrame, applicationFrame.getFrameConfiguration() );
        this.applicationFrame = applicationFrame;

        setSize( 700, 500 );
        setMinimumSize( new Dimension( 700, 500 ) );

        FrameApplication application = ( FrameApplication ) applicationFrame.getApplication();

        setIconImage( application.getSmallImage() );
        setTitle( getBannerTitle() );
    }

    @Override
    protected final String getBannerTitle()
    {
        FrameConfiguration frameConfiguration = applicationFrame.getFrameConfiguration();

        String title = resources.getResourceString( FramePropertiesDialog.class, "title" );

        Map<String, Object> arguments = new HashMap<String, Object>();
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
        FrameApplication application = ( FrameApplication ) applicationFrame.getApplication();
        return ( ImageIcon ) application.getLargeIcon();
    }

    @Override
    protected List<PropertiesDialogPage> getPages()
    {
        return applicationFrame.getFrameConfigurationPages();
    }
}
