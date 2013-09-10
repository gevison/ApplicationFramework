package ge.framework.frame.core;

import ge.framework.frame.core.document.ApplicationDocumentComponent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 09/09/13
 * Time: 09:31
 */
public interface DocumentWorkspaceApplicationFrame
{
    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent );

    public  void openDocument( ApplicationDocumentComponent applicationDocumentComponent, boolean floating );

    public  void closeDocument( ApplicationDocumentComponent applicationDocumentComponent );

    public  void closeAllDocuments();

    public  void closeAllButThis( ApplicationDocumentComponent applicationDocumentComponent );

    public  void closeCurrentDocument();

    public  void closeAllDocumentExceptCurrent();

    public  List<ApplicationDocumentComponent> getDocumentComponents();

    public  void gotoNextDocument();

    public  void gotoPreviousDocument();
}
