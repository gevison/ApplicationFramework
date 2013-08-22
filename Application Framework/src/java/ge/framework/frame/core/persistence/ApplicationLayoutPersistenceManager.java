package ge.framework.frame.core.persistence;

import com.jidesoft.swing.LayoutPersistenceManager;
import ge.framework.frame.core.persistence.callback.LoadCallBack;
import ge.framework.frame.core.persistence.callback.SaveCallBack;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/02/13
 * Time: 18:31
 */
public class ApplicationLayoutPersistenceManager extends LayoutPersistenceManager
{
    public ApplicationLayoutPersistenceManager( String profileKey )
    {
        super();

        setProfileKey( profileKey );

        setLoadCallback( new LoadCallBack() );
        setSaveCallback( new SaveCallBack() );

        setUsePref( false );
        setXmlFormat( true );

        setUseFrameBounds( true );
        setUseFrameState( true );
    }

    public void setLayoutDirectory( File metadataDirectory )
    {
        super.setLayoutDirectory( metadataDirectory.getPath() );
    }
}
