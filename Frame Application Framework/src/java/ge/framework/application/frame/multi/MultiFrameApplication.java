package ge.framework.application.frame.multi;

import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.application.core.objects.ApplicationBean;
import ge.framework.application.frame.core.FrameApplication;
import ge.framework.application.frame.multi.dialog.InitialDialog;
import ge.framework.application.frame.multi.dialog.LockedLocationDialog;
import ge.framework.application.frame.multi.dialog.MissingLocationDialog;
import ge.framework.application.frame.multi.dialog.NewDialog;
import ge.framework.application.frame.multi.dialog.OpenDialog;
import ge.framework.application.frame.multi.dialog.OpenLocationDialog;
import ge.framework.application.frame.multi.objects.MultiFrameApplicationConfiguration;
import ge.framework.application.frame.multi.objects.enums.OpenLocationEnum;
import ge.framework.application.frame.multi.properties.MultiGeneralApplicationPropertiesPage;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.multi.MultiFrameApplicationFrame;
import ge.framework.frame.multi.objects.FrameConfiguration;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.file.LockFile;
import ge.utils.log.LoggerEx;
import ge.utils.properties.PropertiesDialogPage;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 14:19
 */
public abstract class MultiFrameApplication<FRAME extends MultiFrameApplicationFrame, CONFIGURATION extends MultiFrameApplicationConfiguration> extends FrameApplication<FRAME,CONFIGURATION>
{
    private Map<FrameInstanceDetailsObject, FRAME> frames = new HashMap<FrameInstanceDetailsObject, FRAME>();

    private MultiApplicationClosedWindowAdapter
            multiApplicationClosedWindowAdapter = new MultiApplicationClosedWindowAdapter( this );

    private MultiApplicationShutdownWindowAdapter shutdownWindowsAdapter;

    public MultiFrameApplication( ApplicationBean applicationBean )
    {
        super( applicationBean );
    }

    @Override
    public final CloseOrExitEnum closeOrExit()
    {
        MultiFrameApplicationConfiguration configuration = ( MultiFrameApplicationConfiguration ) getConfiguration();

        if ( ( frames.size() == 1 ) && ( configuration.isExitOnFinalWindowClose() == true ) )
        {
            return CloseOrExitEnum.EXIT;
        }
        else
        {
            return CloseOrExitEnum.CLOSE;
        }
    }

    @Override
    public final List<PropertiesDialogPage> getApplicationConfigurationPages()
    {
        List<PropertiesDialogPage> retVal = new ArrayList<PropertiesDialogPage>();

        retVal.add( new MultiGeneralApplicationPropertiesPage() );

        List<PropertiesDialogPage> pages = getMultiApplicationConfigurationPages();

        if ( ( pages != null ) && ( pages.isEmpty() == false ) )
        {
            retVal.addAll( pages );
        }

        return retVal;
    }

    protected abstract List<PropertiesDialogPage> getMultiApplicationConfigurationPages();

    @Override
    protected final void startupVisibleApplication()
    {
        List<FrameInstanceDetailsObject> open = configuration.getOpen();

        if ( ( configuration.isReOpenLast() == true ) && ( open != null ) )
        {
            for ( FrameInstanceDetailsObject frameInstanceDetailsObject : open )
            {
                openFrame( null, frameInstanceDetailsObject, true );
            }

            if ( frames.isEmpty() == true )
            {
                showInitialDialog();
            }
        }
        else
        {
            showInitialDialog();
        }
    }

    @Override
    protected void processApplicationShutdown()
    {
        frames.clear();
        processMultiApplicationShutdown();
    }

    protected abstract void processMultiApplicationShutdown();

    private void showInitialDialog()
    {
        InitialDialog initialDialog = new InitialDialog( this );

        initialDialog.doModal();

        saveApplicationConfiguration();
    }

    public boolean openFrame( FRAME applicationFrame, FrameInstanceDetailsObject frameInstanceDetailsObject,
                              boolean requiresValidation )
    {
        if ( ( requiresValidation == true ) &&
                ( validateFrameInstanceDetails( applicationFrame, frameInstanceDetailsObject ) == false ) )
        {
            return false;
        }

        OpenLocationEnum openLocation = configuration.getOpenLocation();

        if ( ( applicationFrame != null ) && ( openLocation == OpenLocationEnum.ASK ) )
        {
            OpenLocationDialog openLocationDialog = new OpenLocationDialog();

            openLocation = openLocationDialog.showMessage();

            if ( openLocation != null )
            {
                if ( openLocationDialog.isCheckBoxSelected() == true )
                {
                    configuration.setOpenLocation( openLocation );
                }
            }
            else
            {
                return false;
            }
        }

        if ( ( applicationFrame != null ) &&
                ( openLocation == OpenLocationEnum.EXISTING ) )
        {
            applicationFrame.removeWindowListener( multiApplicationClosedWindowAdapter );
            applicationFrame.close();

            removeFrame( applicationFrame );
        }

        try
        {
            Class<FRAME> frameClass = getFrameClass();
            FRAME newApplicationFrame = ConstructorUtils.invokeConstructor( frameClass, this );

            newApplicationFrame.initialise();

            newApplicationFrame.addWindowListener( multiApplicationClosedWindowAdapter );
            frames.put( frameInstanceDetailsObject, newApplicationFrame );

            newApplicationFrame.open( frameInstanceDetailsObject );

            FrameConfiguration frameConfiguration = newApplicationFrame.getFrameConfiguration();

            frameInstanceDetailsObject.setName( frameConfiguration.getName() );

            configuration.addOpen( frameInstanceDetailsObject );
            configuration.addRecent( frameInstanceDetailsObject );

            saveApplicationConfiguration();

            return true;
        }
        catch ( Exception e )
        {
            LoggerEx.error( e.getMessage(), e );
            System.exit( -1 );
            return false;
        }
    }

    private void removeFrame( FRAME applicationFrame )
    {
        if ( frames.containsValue( applicationFrame ) == true )
        {
            Set<Map.Entry<FrameInstanceDetailsObject, FRAME>> entries = frames.entrySet();

            for ( Map.Entry<FrameInstanceDetailsObject, FRAME> entry : entries )
            {
                if ( entry.getValue() == applicationFrame )
                {
                    FrameInstanceDetailsObject key = entry.getKey();

                    configuration.removeOpen( key );
                    saveApplicationConfiguration();

                    frames.remove( key );
                    break;
                }
            }
        }
    }

    public boolean processNew( FRAME applicationFrame )
    {
        NewDialog newDialog = new NewDialog( null, this );

        if ( newDialog.doModal() == true )
        {
            return openFrame( applicationFrame, newDialog.getFrameInstanceDetailsObject(), false );
        }

        return false;
    }

    public boolean processOpen( FRAME applicationFrame )
    {
        OpenDialog openDialog = new OpenDialog( null, this );

        if ( openDialog.doModal() == true )
        {
            return openFrame( applicationFrame, openDialog.getFrameInstanceDetailsObject(), false );
        }

        return false;
    }

    public void processClose( FRAME applicationFrame )
    {
        applicationFrame.close();
    }

    @Override
    protected void processApplicationExit()
    {
        shutdownWindowsAdapter = new MultiApplicationShutdownWindowAdapter( this, frames );
        for ( ApplicationFrame frame : frames.values() )
        {
            frame.removeWindowListener( multiApplicationClosedWindowAdapter );
            frame.close();
        }
    }

    private boolean validateFrameInstanceDetails( FRAME applicationFrame,
                                                  FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        if ( doesFrameConfigurationFileExist( frameInstanceDetailsObject ) == false )
        {
            MissingLocationDialog missingLocationDialog =
                    new MissingLocationDialog( applicationFrame, frameInstanceDetailsObject.getName() );

            return missingLocationDialog.showMessage();
        }
        else if ( isFrameConfigurationFileLocked( frameInstanceDetailsObject ) == true )
        {
            LockedLocationDialog lockedLocationDialog =
                    new LockedLocationDialog( applicationFrame, frameInstanceDetailsObject.getName() );

            lockedLocationDialog.doModal();

            return false;
        }

        return true;
    }

    public List<FrameInstanceDetailsObject> getRecentlyOpened()
    {
        List<FrameInstanceDetailsObject> retVal = new ArrayList<FrameInstanceDetailsObject>();

        List<FrameInstanceDetailsObject> recent = configuration.getRecent();

        for ( FrameInstanceDetailsObject frameInstanceDetailsObject : recent )
        {
            if ( frames.containsKey( frameInstanceDetailsObject ) == false )
            {
                retVal.add( frameInstanceDetailsObject );
            }
        }

        return retVal;
    }

    public void clearRecent()
    {
        configuration.clearRecent();
        saveApplicationConfiguration();
    }

    @Override
    protected boolean terminateNow()
    {
        return frames.isEmpty();
    }

    @Override
    public FRAME discoverFocusedFrame()
    {
        FRAME applicationFrame = null;

        for ( FRAME frame : frames.values() )
        {
            if ( frame.isFocused() == true )
            {
                applicationFrame = frame;
                break;
            }
        }

        return applicationFrame;
    }

    public final int getFrameCount()
    {
        return frames.size();
    }

    public final void nextFrame()
    {
        FRAME applicationFrame = discoverFocusedFrame();

        List<FRAME> values = new ArrayList<FRAME>( frames.values() );

        int index = values.indexOf( applicationFrame );

        index += 1;

        if ( index == values.size() )
        {
            index = 0;
        }

        values.get( index ).setVisible( true );
    }

    public void previousFrame()
    {
        FRAME applicationFrame = discoverFocusedFrame();

        List<FRAME> values = new ArrayList<FRAME>( frames.values() );

        int index = values.indexOf( applicationFrame );

        index -= 1;

        if ( index < 0 )
        {
            index = values.size() - 1;
        }

        values.get( index ).setVisible( true );
    }

    public List<FRAME> getFrames()
    {
        List<FRAME> retVal = new ArrayList<FRAME>( frames.values() );
        return retVal;
    }

    public abstract String getFrameName();

    public final String getFrameMetaDataName()
    {
        return "."+getName();
    }

    public final String getFrameConfigurationName()
    {
        return getName()+".xml";
    }

    public abstract Class<? extends FrameConfiguration> getFrameConfigurationClass();

    public boolean doesFrameConfigurationFileExist( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        File configFile = getFrameConfigurationFile( frameInstanceDetailsObject );
        return configFile.exists();
    }

    public boolean isFrameConfigurationFileLocked( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        try
        {
            File configFile = getFrameConfigurationFile( frameInstanceDetailsObject );
            return LockFile.isFileLocked( configFile );
        }
        catch ( IOException e )
        {
            LoggerEx.error( e.getMessage(), e );
            return true;
        }
    }

    public File getFrameConfigurationFile( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        File metadataDirectory = getFrameMetaDataDirectory( frameInstanceDetailsObject );
        return new File( metadataDirectory, getFrameConfigurationName() );
    }

    public File getFrameMetaDataDirectory( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        return new File( frameInstanceDetailsObject.getLocation(), getFrameMetaDataName() );
    }

    public boolean isFrameLocationLocked( File location )
    {
        try
        {
            File metaDataDirectory = new File( location, getFrameMetaDataName() );
            File configFile = new File( metaDataDirectory, getFrameConfigurationName() );
            return LockFile.isFileLocked( configFile );
        }
        catch ( IOException e )
        {
            LoggerEx.error( e.getMessage(), e );
            return true;
        }
    }

    public boolean isFrameLocation( File location )
    {
        File metaDataDirectory = new File( location, getFrameMetaDataName() );
        File configFile = new File( metaDataDirectory, getFrameConfigurationName() );
        return configFile.exists();
    }

    private class MultiApplicationClosedWindowAdapter extends WindowAdapter
    {
        private MultiFrameApplication multiFrameApplication;

        public MultiApplicationClosedWindowAdapter( MultiFrameApplication multiFrameApplication )
        {
            this.multiFrameApplication = multiFrameApplication;
        }

        @Override
        public final void windowClosed( WindowEvent e )
        {
            Object source = e.getSource();

            multiFrameApplication.removeFrame( ( FRAME ) source );

            if ( frames.isEmpty() == true )
            {
                multiFrameApplication.showInitialDialog();
            }
        }
    }

    private class MultiApplicationShutdownWindowAdapter extends WindowAdapter
    {
        private MultiFrameApplication application;

        private Map<FrameInstanceDetailsObject, FRAME> frames;

        public MultiApplicationShutdownWindowAdapter( MultiFrameApplication application,
                                                      Map<FrameInstanceDetailsObject, FRAME> frames )
        {
            this.application = application;
            this.frames = frames;

            for ( MultiFrameApplicationFrame multiFrameApplicationFrame : frames.values() )
            {
                multiFrameApplicationFrame.addWindowListener( this );
            }
        }

        @Override
        public final void windowClosed( WindowEvent e )
        {
            FRAME applicationFrame = ( FRAME ) e.getSource();

            if ( frames.containsValue( applicationFrame ) == true )
            {
                Set<Map.Entry<FrameInstanceDetailsObject, FRAME>> entries = frames.entrySet();

                for ( Map.Entry<FrameInstanceDetailsObject, FRAME> entry : entries )
                {
                    if ( entry.getValue() == applicationFrame )
                    {
                        FrameInstanceDetailsObject key = entry.getKey();

                        frames.remove( key );

                        break;
                    }
                }
            }

            if ( frames.isEmpty() == true )
            {
                application.processShutdown();
            }
        }
    }
}
