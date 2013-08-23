package ge.framework.frame.core.persistence.callback;

import com.jidesoft.utils.PersistenceUtilsCallback;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.utils.xml.w3c.XmlHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoadCallBack implements PersistenceUtilsCallback.Load
{
    private final static Logger logger = LogManager.getLogger( LoadCallBack.class );

    @Override
    public void load( Document document, Element element, Object object )
    {
        if ( object instanceof ApplicationDockableFrame )
        {
            ApplicationDockableFrame frame = ( ApplicationDockableFrame ) object;
            logger.trace( "Loading Frame: " + frame.getKey() );

            NodeList childNodes = element.getChildNodes();

            for ( int i = 0; i < childNodes.getLength(); i++ )
            {
                Node item = childNodes.item( i );

                if ( item instanceof Element )
                {
                    org.jdom2.Element content = ( org.jdom2.Element ) XmlHelper.nodeToContent( item );

                    frame.loadLayoutData( content );

                    break;
                }
            }
        }
    }
}