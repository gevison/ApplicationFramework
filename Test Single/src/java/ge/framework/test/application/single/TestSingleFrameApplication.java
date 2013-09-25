package ge.framework.test.application.single;

import ge.framework.application.core.objects.ApplicationBean;
import ge.framework.application.frame.single.SingleFrameApplication;
import ge.framework.test.application.single.objects.TestSingleFrameApplicationConfiguration;
import ge.framework.test.frame.single.TestSingleFrameApplicationFrame;
import ge.utils.bundle.Resources;
import ge.utils.properties.PropertiesDialogPage;

import javax.swing.Icon;
import java.awt.Image;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 17:21
 */
public class TestSingleFrameApplication extends SingleFrameApplication<TestSingleFrameApplicationFrame,TestSingleFrameApplicationConfiguration>
{
    Resources resources = Resources.getInstance( "ge.framework.test.frame.single" );

    public TestSingleFrameApplication( ApplicationBean applicationBean )
    {
        super( applicationBean );
    }

    @Override
    protected void initialiseSingleApplication( String[] args )
    {

    }

    @Override
    protected void initialiseSingleApplicationConfiguration()
    {

    }

    @Override
    protected void processSingleApplicationShutdown()
    {

    }

    @Override
    protected List<PropertiesDialogPage> getSingleApplicationConfigurationPages()
    {
        return null;
    }

    @Override
    public Image getSmallImage()
    {
        return resources.getResourceImage( TestSingleFrameApplication.class, "icon","small" );
    }

    @Override
    public Icon getSmallIcon()
    {
        return resources.getResourceIcon( TestSingleFrameApplication.class, "icon", "small" );
    }

    @Override
    public Image getLargeImage()
    {
        return resources.getResourceImage( TestSingleFrameApplication.class, "icon", "large" );
    }

    @Override
    public Icon getLargeIcon()
    {
        return resources.getResourceIcon( TestSingleFrameApplication.class, "icon", "large" );
    }

    @Override
    public Image getMacImage()
    {
        return resources.getResourceImage( TestSingleFrameApplication.class, "icon", "mac" );
    }

    @Override
    public Icon getMacIcon()
    {
        return resources.getResourceIcon( TestSingleFrameApplication.class, "icon", "mac" );
    }

    @Override
    public Class<TestSingleFrameApplicationFrame> getFrameClass()
    {
        return TestSingleFrameApplicationFrame.class;
    }

    @Override
    protected boolean getAllowMultipleApplications()
    {
        return false;
    }

    @Override
    protected String getApplicationMetaDataName()
    {
        return ".TestSingle";
    }

    @Override
    protected String getApplicationConfigurationName()
    {
        return "TestSingle.xml";
    }

    @Override
    protected Class<TestSingleFrameApplicationConfiguration> getApplicationConfigurationClass()
    {
        return TestSingleFrameApplicationConfiguration.class;
    }
}
