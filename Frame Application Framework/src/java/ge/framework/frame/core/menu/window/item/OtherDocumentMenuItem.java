package ge.framework.frame.core.menu.window.item;

import ge.framework.frame.core.DocumentWorkspaceApplicationFrame;
import ge.framework.frame.core.document.ApplicationDocumentComponent;
import ge.framework.frame.core.document.dialog.DocumentsDialog;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public class OtherDocumentMenuItem extends DocumentWorkspaceApplicationFrameMenuItem
{
    private static Resources resources = Resources.getInstance( "ge.framework.frame.core" );

    public OtherDocumentMenuItem( DocumentWorkspaceApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( OtherDocumentMenuItem.class, "label" ) );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        DocumentsDialog documentsDialog =
                new DocumentsDialog( ( DocumentWorkspaceApplicationFrame ) applicationFrame );

        if ( documentsDialog.doModal() == true )
        {
            ApplicationDocumentComponent documentComponent = documentsDialog.getDocumentComponent();

            documentComponent.showDocument();
        }
    }

    @Override
    public void update()
    {
    }
}
