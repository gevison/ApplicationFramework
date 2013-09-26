package ge.framework.frame.core.document;

import com.jidesoft.document.DocumentPane;
import ge.utils.log.LoggerEx;


/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/02/13
 * Time: 18:28
 */
public class ApplicationDocumentPane extends DocumentPane
{
    public ApplicationDocumentPane()
    {
    }

    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        openDocument( applicationDocumentComponent, false );
    }

    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent, boolean floating )
    {
        LoggerEx.debug( "Opening document: " + applicationDocumentComponent.getMenuTitle() );
        super.openDocument( applicationDocumentComponent, floating );
    }

    public void closeDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        super.closeSingleDocument( applicationDocumentComponent.getName() );
    }

    public void closeAllButThis( ApplicationDocumentComponent applicationDocumentComponent )
    {
        super.closeAllButThis( applicationDocumentComponent.getName() );
    }
}
