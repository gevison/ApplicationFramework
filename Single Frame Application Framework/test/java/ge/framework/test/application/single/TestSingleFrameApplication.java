package ge.framework.test.application.single;

import ge.framework.application.core.objects.ApplicationBean;
import ge.framework.application.frame.single.SingleFrameApplication;
import ge.framework.test.application.single.objects.TestSingleFrameApplicationConfiguration;
import ge.utils.properties.PropertiesDialogPage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 17:21
 */
public class TestSingleFrameApplication extends SingleFrameApplication<TestSingleFrameApplicationFrame,TestSingleFrameApplicationConfiguration>
{
    public TestSingleFrameApplication( ApplicationBean applicationBean )
    {
        super( applicationBean );
    }

    @Override
    protected void initialiseApplication( String[] args )
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
    protected Class<TestSingleFrameApplicationConfiguration> getApplicationConfigurationClass()
    {
        return TestSingleFrameApplicationConfiguration.class;
    }
}
