package ge.framework.application.multi.dialog.utils;

import ge.framework.application.multi.MultiFrameApplication;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 04/02/13
 * Time: 11:33
 */
public class TypeFileSystemView extends FileSystemView
{
    private FileSystemView fsv = FileSystemView.getFileSystemView();

    private MultiFrameApplication application;

    public TypeFileSystemView( MultiFrameApplication application )
    {
        this.application = application;
    }

    @Override
    public File createNewFolder( File containingDir ) throws IOException
    {
        return fsv.createNewFolder( containingDir );
    }

    @Override
    public Icon getSystemIcon( File f )
    {
        if ( ( f.isDirectory() == true ) && ( application.isFrameLocation( f ) == true ) )
        {
            return application.getSmallIcon();
        }
        else
        {
            return fsv.getSystemIcon( f );
        }
    }
}
