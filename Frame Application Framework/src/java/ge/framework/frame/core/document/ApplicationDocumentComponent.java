package ge.framework.frame.core.document;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.IDocumentPane;

import javax.swing.Icon;
import javax.swing.JPanel;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 12:40
 */
public abstract class ApplicationDocumentComponent extends DocumentComponent
{
    private boolean initialisePanel = false;

    public ApplicationDocumentComponent( String name )
    {
        super( new JPanel(), name );
    }

    public String getMenuTitle()
    {
        return getDisplayTitle();
    }

    public Icon getMenuIcon()
    {
        return getIcon();
    }

    public void showDocument()
    {
        IDocumentPane documentPane = getDocumentPane();

        if ( documentPane != null )
        {
            documentPane.setActiveDocument( getName(), true );
        }
    }
}
