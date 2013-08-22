package ge.framework.frame.core.persistence.callback;

import com.jidesoft.utils.PersistenceUtilsCallback;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.utils.xml.w3c.XmlHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SaveCallBack implements PersistenceUtilsCallback.Save
{
    private final static Logger logger = Logger.getLogger( SaveCallBack.class );

    @Override
    public void save( Document document, Element element, Object object )
    {
        if ( object instanceof ApplicationDockableFrame )
        {
            ApplicationDockableFrame frame = ( ApplicationDockableFrame ) object;
            logger.trace( "Saving Frame: " + frame.getKey() );

            org.jdom2.Element saveElement = frame.saveLayoutData();

            if ( saveElement != null )
            {
                Element output =
                        ( Element ) XmlHelper.contentToNode( document, saveElement );

                element.appendChild( output );
            }
        }
    }
}