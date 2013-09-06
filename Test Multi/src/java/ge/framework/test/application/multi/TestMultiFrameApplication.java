package ge.framework.test.application.multi;

import ge.framework.application.multi.MultiFrameApplication;
import ge.utils.properties.PropertiesDialogPage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 13:45
 */
public class TestMultiFrameApplication extends MultiFrameApplication
{
    @Override
    protected List<PropertiesDialogPage> getMultiApplicationConfigurationPages()
    {
        return null;
    }

    @Override
    protected void validateMultiApplicationObject()
    {

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
    protected void initialiseApplicationConfiguration()
    {

    }
}
