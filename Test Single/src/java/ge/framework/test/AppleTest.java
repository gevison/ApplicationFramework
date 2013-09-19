package ge.framework.test;

import ge.utils.os.OS;
import ge.utils.spring.context.ClasspathApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 17/09/2013
 * Time: 08:24
 * To change this template use File | Settings | File Templates.
 */
public class AppleTest
{
    public static void main( String[] args )
    {
        ClasspathApplicationContext classpathApplicationContext = ClasspathApplicationContext.getInstance(
                "appleApplication.xml" );


        SingleFrameApplicationBean singleFrameApplicationBean = classpathApplicationContext.getBean( "AppleApplication", SingleFrameApplicationBean.class );

        if ( OS.isMac() == true )
        {
            System.out.println(System.getProperty( "com.apple.mrj.application.apple.menu.about.name" ));
            System.setProperty( "com.apple.mrj.application.apple.menu.about.name", "Apple Test" );
            System.out.println( System.getProperty( "com.apple.mrj.application.apple.menu.about.name" ) );
        }
    }
}
