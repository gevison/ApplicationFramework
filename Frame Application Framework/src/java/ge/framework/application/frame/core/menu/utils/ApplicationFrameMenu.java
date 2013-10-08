package ge.framework.application.frame.core.menu.utils;

import com.jidesoft.swing.JideMenu;
import ge.framework.application.frame.core.ApplicationFrame;
import ge.framework.application.frame.core.status.utils.menu.StatusBarEnabledSpacerMenu;

import javax.swing.JPopupMenu;
import java.util.ArrayList;
import java.util.List;

public abstract class ApplicationFrameMenu<FRAME extends ApplicationFrame> extends StatusBarEnabledSpacerMenu implements ApplicationFrameMenuComponent,
                                                                                         JideMenu.PopupMenuCustomizer
{
    private List<ApplicationFrameMenuComponent> menuItems = new ArrayList<ApplicationFrameMenuComponent>();

    protected FRAME applicationFrame;

    public ApplicationFrameMenu( FRAME applicationFrame )
    {
        this( applicationFrame, true );
    }

    public ApplicationFrameMenu( FRAME applicationFrame, boolean useSpacer )
    {
        super( useSpacer );
        this.applicationFrame = applicationFrame;
    }

    @Override
    public final void initialise()
    {
        setPopupMenuCustomizer( this );

        initialiseMenu();
    }

    protected final void addMenuComponent( ApplicationFrameMenuComponent applicationFrameMenuComponent )
    {
        menuItems.add( applicationFrameMenuComponent );
    }

    protected abstract void initialiseMenu();

    @Override
    public final void customize( JPopupMenu jPopupMenu )
    {
        if ( this.getPopupMenu() == jPopupMenu )
        {
            removeAll();

            menuItems.clear();

            customizeMenu();

            if ( menuItems.isEmpty() == false )
            {
                boolean lastItemSeparator = true;

                for ( ApplicationFrameMenuComponent additionalMenuComponent : menuItems )
                {
                    additionalMenuComponent.update();

                    if ( additionalMenuComponent.isVisible() == true )
                    {
                        if ( ( additionalMenuComponent instanceof ApplicationFrameMenuSeparator ) &&
                                ( lastItemSeparator == false ) )
                        {
                            addSeparator();

                            lastItemSeparator = true;
                        }
                        else if ( additionalMenuComponent instanceof ApplicationFrameMenuItem )
                        {
                            ApplicationFrameMenuItem applicationFrameMenuItem =
                                    ( ApplicationFrameMenuItem ) additionalMenuComponent;

                            add( applicationFrameMenuItem );

                            lastItemSeparator = false;
                        }
                        else if ( additionalMenuComponent instanceof ApplicationFrameCheckboxMenuItem )
                        {
                            ApplicationFrameCheckboxMenuItem applicationFrameCheckboxMenuItem =
                                    ( ApplicationFrameCheckboxMenuItem ) additionalMenuComponent;

                            add( applicationFrameCheckboxMenuItem );

                            lastItemSeparator = false;
                        }
                        else if ( additionalMenuComponent instanceof ApplicationFrameMenu )
                        {
                            ApplicationFrameMenu applicationFrameMenu =
                                    ( ApplicationFrameMenu ) additionalMenuComponent;

                            add( applicationFrameMenu );

                            lastItemSeparator = false;
                        }
                    }
                }

                if ( lastItemSeparator == true )
                {
                    jPopupMenu.remove( jPopupMenu.getComponentCount() - 1 );
                }
            }
        }
    }

    protected abstract void customizeMenu();
}
