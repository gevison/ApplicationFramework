package ge.framework.application.multi.dialog.utils;

import ge.framework.frame.multi.objects.FrameDefinition;

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

    private FrameDefinition frameDefinition;

    public TypeFileSystemView( FrameDefinition frameDefinition )
    {
        this.frameDefinition = frameDefinition;
    }

    @Override
    public File createNewFolder( File containingDir ) throws IOException
    {
        return fsv.createNewFolder( containingDir );
    }

    @Override
    public Icon getSystemIcon( File f )
    {
        if ( ( f.isDirectory() == true ) && ( frameDefinition.isDirectory( f ) == true ) )
        {
            return frameDefinition.getSmallIcon();
        }
        else
        {
            return fsv.getSystemIcon( f );
        }
    }
}
