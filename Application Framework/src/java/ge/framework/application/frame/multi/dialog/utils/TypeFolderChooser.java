package ge.framework.application.frame.multi.dialog.utils;

import com.jidesoft.swing.FolderChooser;
import ge.framework.application.frame.multi.MultiFrameApplication;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 04/02/13
 * Time: 11:37
 */
public class TypeFolderChooser extends FolderChooser
{
    private MultiFrameApplication application;

    public TypeFolderChooser( MultiFrameApplication application )
    {
        super( new TypeFileSystemView( application ) );
        this.application = application;

        setMultiSelectionEnabled( false );
    }

    @Override
    public void approveSelection()
    {
        if ( application.isFrameLocation( getSelectedFile() ) == true )
        {
            super.approveSelection();
        }
    }
}
