package ge.framework.frame.core.menu.utils;

import com.jidesoft.swing.JideMenu;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.status.utils.menu.StatusBarEnabledSpacerMenu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ApplicationFrameMenu extends StatusBarEnabledSpacerMenu implements ApplicationFrameMenuComponent,
                                                                                         JideMenu.PopupMenuCustomizer
{
    private List<ApplicationFrameMenuComponent> menuItems = new ArrayList<ApplicationFrameMenuComponent>();

    protected ApplicationFrame applicationFrame;

    public ApplicationFrameMenu( ApplicationFrame applicationFrame )
    {
        this( applicationFrame, true );
    }

    public ApplicationFrameMenu( ApplicationFrame applicationFrame, boolean useSpacer )
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