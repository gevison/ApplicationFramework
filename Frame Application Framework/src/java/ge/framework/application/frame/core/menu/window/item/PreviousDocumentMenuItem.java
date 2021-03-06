package ge.framework.application.frame.core.menu.window.item;

import ge.framework.application.frame.core.DocumentWorkspaceApplicationFrame;
import ge.framework.application.frame.core.document.ApplicationDocumentComponent;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public class PreviousDocumentMenuItem extends DocumentWorkspaceApplicationFrameMenuItem
{
    private static Resources resources = Resources.getInstance( "ge.framework.frame.core" );

    public PreviousDocumentMenuItem( DocumentWorkspaceApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( PreviousDocumentMenuItem.class, "label" ) );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        DocumentWorkspaceApplicationFrame documentWorkspaceApplicationFrame =
                ( DocumentWorkspaceApplicationFrame ) applicationFrame;
        documentWorkspaceApplicationFrame.gotoPreviousDocument();
    }

    @Override
    public void update()
    {
        DocumentWorkspaceApplicationFrame documentWorkspaceApplicationFrame =
                ( DocumentWorkspaceApplicationFrame ) applicationFrame;
        List<ApplicationDocumentComponent> documentComponents =
                documentWorkspaceApplicationFrame.getDocumentComponents();

        if ( documentComponents.size() <= 1 )
        {
            setEnabled( false );
        }
        else
        {
            setEnabled( true );
        }
    }
}
