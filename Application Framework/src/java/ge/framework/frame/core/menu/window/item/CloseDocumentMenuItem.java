package ge.framework.frame.core.menu.window.item;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.document.ApplicationDocumentComponent;
import ge.framework.frame.core.menu.utils.ApplicationFrameMenuItem;
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
public class CloseDocumentMenuItem extends ApplicationFrameMenuItem
{
    private static Resources resources = Resources.getInstance( "ge.framework.frame.core" );

    public CloseDocumentMenuItem( ApplicationFrame applicationFrame )
    {
        super(applicationFrame);
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( resources.getResourceString( CloseDocumentMenuItem.class, "label" ) );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        applicationFrame.closeCurrentDocument(  );
    }

    @Override
    public void update()
    {
        List<ApplicationDocumentComponent> documentComponents = applicationFrame.getDocumentComponents();

        if ( documentComponents.isEmpty() == true )
        {
            setEnabled( false );
        }
        else
        {
            setEnabled( true );
        }
    }
}
