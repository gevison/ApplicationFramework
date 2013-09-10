package ge.framework.frame.multi;

import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.DocumentWorkspaceApplicationFrame;
import ge.framework.frame.core.document.ApplicationDocumentComponent;
import ge.framework.frame.core.document.ApplicationDocumentPane;
import ge.utils.log.LoggerEx;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 09/09/13
 * Time: 09:31
 */
public abstract class DocumentMultiFrameApplicationFrame extends MultiFrameApplicationFrame implements
                                                                                            DocumentWorkspaceApplicationFrame
{
    private ApplicationDocumentPane documentPane;

    public DocumentMultiFrameApplicationFrame( MultiFrameApplication application ) throws HeadlessException
    {
        super( application );
    }

    @Override
    protected final void initialiseApplicationFrame()
    {
        LoggerEx.trace( "Initialising document pane." );
        documentPane = new ApplicationDocumentPane();

        setWorkspaceComponent( documentPane );

        initialiseDocumentWorkspaceApplicationFrame();
    }

    protected abstract void initialiseDocumentWorkspaceApplicationFrame();

    public final void openDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        openDocument( applicationDocumentComponent, false );
    }

    public final void openDocument( ApplicationDocumentComponent applicationDocumentComponent, boolean floating )
    {
        documentPane.openDocument( applicationDocumentComponent, floating );
    }

    public final void closeDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        documentPane.closeDocument( applicationDocumentComponent );
    }

    public final void closeAllDocuments()
    {
        documentPane.closeAll();
    }

    public final void closeAllButThis( ApplicationDocumentComponent applicationDocumentComponent )
    {
        documentPane.closeAllButThis( applicationDocumentComponent );
    }

    public final void closeCurrentDocument()
    {
        ApplicationDocumentComponent activeDocument = ( ApplicationDocumentComponent ) documentPane.getActiveDocument();

        if ( activeDocument != null )
        {
            closeDocument( activeDocument );
        }
    }

    public final void closeAllDocumentExceptCurrent()
    {
        ApplicationDocumentComponent activeDocument = ( ApplicationDocumentComponent ) documentPane.getActiveDocument();

        if ( activeDocument != null )
        {
            closeAllButThis( activeDocument );
        }
    }

    public final List<ApplicationDocumentComponent> getDocumentComponents()
    {
        List<ApplicationDocumentComponent> retVal = new ArrayList<ApplicationDocumentComponent>();

        for ( int i = 0; i < documentPane.getDocumentCount(); i++ )
        {
            retVal.add( ( ApplicationDocumentComponent ) documentPane.getDocumentAt( i ) );
        }

        return retVal;
    }

    public final void gotoNextDocument()
    {
        documentPane.nextDocument();
    }

    public final void gotoPreviousDocument()
    {
        documentPane.prevDocument();
    }
}
