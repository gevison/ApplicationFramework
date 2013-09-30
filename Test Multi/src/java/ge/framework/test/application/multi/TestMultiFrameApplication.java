package ge.framework.test.application.multi;

import ge.framework.application.core.objects.ApplicationBean;
import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.frame.multi.objects.FrameConfiguration;
import ge.framework.test.application.multi.objects.TestMultiFrameApplicationConfiguration;
import ge.framework.test.frame.multi.TestMultiFrameApplicationFrame;
import ge.framework.test.frame.multi.objects.TestMultiFrameConfiguration;
import ge.utils.bundle.Resources;
import ge.utils.properties.PropertiesDialogPage;

import javax.swing.Icon;
import java.awt.Image;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 13:45
 */
public class TestMultiFrameApplication extends MultiFrameApplication<TestMultiFrameApplicationFrame,TestMultiFrameApplicationConfiguration>
{
    Resources resources = Resources.getInstance( "ge.framework.test.frame.multi" );

    public TestMultiFrameApplication( ApplicationBean applicationBean )
    {
        super( applicationBean );
    }

    @Override
    protected boolean getAllowMultipleApplications()
    {
        return false;
    }

    @Override
    protected void initialiseApplicationConfiguration()
    {

    }

    @Override
    protected Class<TestMultiFrameApplicationConfiguration> getApplicationConfigurationClass()
    {
        return TestMultiFrameApplicationConfiguration.class;
    }

    @Override
    public Image getSmallImage()
    {
        return resources.getResourceImage( TestMultiFrameApplication.class, "icon", "small" );
    }

    @Override
    public Icon getSmallIcon()
    {
        return resources.getResourceIcon( TestMultiFrameApplication.class, "icon", "small" );
    }

    @Override
    public Image getLargeImage()
    {
        return resources.getResourceImage( TestMultiFrameApplication.class, "icon", "large" );
    }

    @Override
    public Icon getLargeIcon()
    {
        return resources.getResourceIcon( TestMultiFrameApplication.class, "icon", "large" );
    }

    @Override
    public Image getMacImage()
    {
        return resources.getResourceImage( TestMultiFrameApplication.class, "icon", "mac" );
    }

    @Override
    public Icon getMacIcon()
    {
        return resources.getResourceIcon( TestMultiFrameApplication.class, "icon", "mac" );
    }

    @Override
    public Class<TestMultiFrameApplicationFrame> getFrameClass()
    {
        return TestMultiFrameApplicationFrame.class;
    }

    @Override
    protected List<PropertiesDialogPage> getMultiApplicationConfigurationPages()
    {
        return null;
    }

    @Override
    protected void initialiseMultiApplication( String[] args )
    {

    }

    @Override
    protected void processMultiApplicationShutdown()
    {

    }

    @Override
    public String getFrameName()
    {
        return "Test Multi Frame";
    }

    @Override
    public Class<? extends FrameConfiguration> getFrameConfigurationClass()
    {
        return TestMultiFrameConfiguration.class;
    }
}
