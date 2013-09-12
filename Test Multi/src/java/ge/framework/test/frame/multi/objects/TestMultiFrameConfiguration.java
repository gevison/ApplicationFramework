package ge.framework.test.frame.multi.objects;

import ge.framework.frame.multi.objects.FrameConfiguration;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/08/13
 * Time: 14:33
 */
@XmlAccessorType( XmlAccessType.FIELD )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
@XmlRootElement
public class TestMultiFrameConfiguration extends FrameConfiguration
{
    private short test;
    public TestMultiFrameConfiguration()
    {
    }

    public TestMultiFrameConfiguration( String name )
    {
        super( name );
    }
}
