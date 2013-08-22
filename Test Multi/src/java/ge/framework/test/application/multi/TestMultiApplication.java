package ge.framework.test.application.multi;

import ge.framework.application.multi.MultiApplication;
import ge.utils.properties.PropertiesDialogPage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 13:45
 */
public class TestMultiApplication extends MultiApplication
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
    protected void initialiseMultiApplicationConfiguration()
    {

    }

    @Override
    protected void processMultiApplicationShutdown()
    {

    }
}
