package ge.framework.test.application.multi;

import ge.framework.application.core.objects.ApplicationBean;
import ge.framework.application.frame.multi.MultiFrameApplication;
import ge.framework.frame.multi.objects.FrameConfiguration;
import ge.framework.test.application.multi.objects.TestMultiFrameApplicationConfiguration;
import ge.framework.test.frame.multi.TestMultiFrameApplicationFrame;
import ge.framework.test.frame.multi.objects.TestMultiFrameConfiguration;
import ge.utils.properties.PropertiesDialogPage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 13:45
 */
public class TestMultiFrameApplication extends MultiFrameApplication<TestMultiFrameApplicationFrame,TestMultiFrameApplicationConfiguration>
{
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
    protected void initialiseApplication( String[] args )
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
