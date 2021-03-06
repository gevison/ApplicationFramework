package ge.framework.application.frame.core.menu.window.item;

import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.document.ApplicationDocumentComponent;
import ge.framework.application.frame.core.menu.utils.ApplicationFrameCheckboxMenuItem;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/07/2013
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationDocumentMenuItem extends ApplicationFrameCheckboxMenuItem
{
    private ApplicationDocumentComponent applicationDocumentComponent;

    public ApplicationDocumentMenuItem( ApplicationFrame applicationFrame,
                                        ApplicationDocumentComponent applicationDocumentComponent )
    {
        super( applicationFrame );
        this.applicationDocumentComponent = applicationDocumentComponent;
    }

    @Override
    protected void initialiseMenuItem()
    {
        setText( applicationDocumentComponent.getMenuTitle() );
        setIcon( applicationDocumentComponent.getMenuIcon() );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        applicationDocumentComponent.showDocument();
    }

    @Override
    public void update()
    {
    }
}
