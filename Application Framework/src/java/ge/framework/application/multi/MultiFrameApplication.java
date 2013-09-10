package ge.framework.application.multi;

import ge.framework.application.core.Application;
import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.application.multi.dialog.InitialDialog;
import ge.framework.application.multi.dialog.LockedLocationDialog;
import ge.framework.application.multi.dialog.MissingLocationDialog;
import ge.framework.application.multi.dialog.NewDialog;
import ge.framework.application.multi.dialog.OpenDialog;
import ge.framework.application.multi.dialog.OpenLocationDialog;
import ge.framework.application.multi.objects.MultiApplicationConfiguration;
import ge.framework.application.multi.objects.enums.OpenLocationEnum;
import ge.framework.application.multi.properties.MultiGeneralApplicationPropertiesPage;
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

import static org.springframework.util.Assert.hasLength;
import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/07/13
 * Time: 14:19
 */
public abstract class MultiFrameApplication extends Application
{
    private String frameName;

    private String frameMetaDataName;

    private String frameConfigurationName;

    private Class<? extends FrameConfiguration> frameConfigurationClass;

    private Map<FrameInstanceDetailsObject, MultiFrameApplicationFrame> frames =
            new HashMap<FrameInstanceDetailsObject, MultiFrameApplicationFrame>();

    private MultiApplicationClosedWindowAdapter
            multiApplicationClosedWindowAdapter = new MultiApplicationClosedWindowAdapter( this );

    private MultiApplicationShutdownWindowAdapter shutdownWindowsAdapter;

    @Override
    public final CloseOrExitEnum closeOrExit()
    {
        MultiApplicationConfiguration configuration = ( MultiApplicationConfiguration ) getConfiguration();

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
    protected final void validateApplicationObject()
    {

        hasLength( frameMetaDataName );
        hasLength( frameConfigurationName );
        notNull( frameConfigurationClass );

        validateMultiApplicationObject();
    }

    protected abstract void validateMultiApplicationObject();

    @Override
    protected final void initialiseApplication( String[] args )
    {
        initialiseMultiApplication( args );
    }

    protected abstract void initialiseMultiApplication( String[] args );

    @Override
    protected final void startupApplication()
    {
        MultiApplicationConfiguration configuration = ( MultiApplicationConfiguration ) getConfiguration();

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

    public boolean openFrame( ApplicationFrame applicationFrame, FrameInstanceDetailsObject frameInstanceDetailsObject,
                              boolean requiresValidation )
    {
        if ( ( requiresValidation == true ) &&
                ( validateFrameInstanceDetails( applicationFrame, frameInstanceDetailsObject ) == false ) )
        {
            return false;
        }

        MultiApplicationConfiguration configuration = ( MultiApplicationConfiguration ) getConfiguration();

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
            MultiFrameApplicationFrame newApplicationFrame =
                    ( MultiFrameApplicationFrame ) ConstructorUtils.invokeConstructor( frameClass, this );

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

    private void removeFrame( ApplicationFrame applicationFrame )
    {
        if ( frames.containsValue( applicationFrame ) == true )
        {
            Set<Map.Entry<FrameInstanceDetailsObject, MultiFrameApplicationFrame>> entries = frames.entrySet();

            for ( Map.Entry<FrameInstanceDetailsObject, MultiFrameApplicationFrame> entry : entries )
            {
                if ( entry.getValue() == applicationFrame )
                {
                    FrameInstanceDetailsObject key = entry.getKey();

                    MultiApplicationConfiguration configuration = ( MultiApplicationConfiguration ) getConfiguration();

                    configuration.removeOpen( key );
                    saveApplicationConfiguration();

                    frames.remove( key );
                    break;
                }
            }
        }
    }

    public boolean processNew( ApplicationFrame applicationFrame )
    {
        NewDialog newDialog = new NewDialog( null, this );

        if ( newDialog.doModal() == true )
        {
            return openFrame( applicationFrame, newDialog.getFrameInstanceDetailsObject(), false );
        }

        return false;
    }

    public boolean processOpen( ApplicationFrame applicationFrame )
    {
        OpenDialog openDialog = new OpenDialog( null, this );

        if ( openDialog.doModal() == true )
        {
            return openFrame( applicationFrame, openDialog.getFrameInstanceDetailsObject(), false );
        }

        return false;
    }

    public void processClose( ApplicationFrame applicationFrame )
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

    private boolean validateFrameInstanceDetails( ApplicationFrame applicationFrame,
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

        MultiApplicationConfiguration configuration = ( MultiApplicationConfiguration ) getConfiguration();

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
        MultiApplicationConfiguration configuration = ( MultiApplicationConfiguration ) getConfiguration();
        configuration.clearRecent();
        saveApplicationConfiguration();
    }

    @Override
    protected boolean terminateNow()
    {
        return frames.isEmpty();
    }

    @Override
    public ApplicationFrame discoverFocusedFrame()
    {
        ApplicationFrame applicationFrame = null;

        for ( ApplicationFrame frame : frames.values() )
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
        ApplicationFrame applicationFrame = discoverFocusedFrame();

        List<ApplicationFrame> values = new ArrayList<ApplicationFrame>( frames.values() );

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
        ApplicationFrame applicationFrame = discoverFocusedFrame();

        List<ApplicationFrame> values = new ArrayList<ApplicationFrame>( frames.values() );

        int index = values.indexOf( applicationFrame );

        index -= 1;

        if ( index < 0 )
        {
            index = values.size() - 1;
        }

        values.get( index ).setVisible( true );
    }

    public List<MultiFrameApplicationFrame> getFrames()
    {
        List<MultiFrameApplicationFrame> retVal = new ArrayList<MultiFrameApplicationFrame>( frames.values() );
        return retVal;
    }

    public String getFrameName()
    {
        return frameName;
    }

    public void setFrameName( String frameName )
    {
        this.frameName = frameName;
    }

    public String getFrameMetaDataName()
    {
        return frameMetaDataName;
    }

    public void setFrameMetaDataName( String frameMetaDataName )
    {
        testInitialised();
        this.frameMetaDataName = frameMetaDataName;
    }

    public String getFrameConfigurationName()
    {
        return frameConfigurationName;
    }

    public void setFrameConfigurationName( String frameConfigurationName )
    {
        testInitialised();
        this.frameConfigurationName = frameConfigurationName;
    }

    public Class<? extends FrameConfiguration> getFrameConfigurationClass()
    {
        return frameConfigurationClass;
    }

    public void setFrameConfigurationClass( Class<? extends FrameConfiguration> frameConfigurationClass )
    {
        testInitialised();
        this.frameConfigurationClass = frameConfigurationClass;
    }

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
        return new File( metadataDirectory, frameConfigurationName );
    }

    public File getFrameMetaDataDirectory( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        return new File( frameInstanceDetailsObject.getLocation(), frameMetaDataName );
    }

    public boolean isFrameLocationLocked( File location )
    {
        try
        {
            File metaDataDirectory = new File( location, frameMetaDataName );
            File configFile = new File( metaDataDirectory, frameConfigurationName );
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
        File metaDataDirectory = new File( location, frameMetaDataName );
        File configFile = new File( metaDataDirectory, frameConfigurationName );
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

            multiFrameApplication.removeFrame( ( ApplicationFrame ) source );

            if ( frames.isEmpty() == true )
            {
                multiFrameApplication.showInitialDialog();
            }
        }
    }

    private class MultiApplicationShutdownWindowAdapter extends WindowAdapter
    {
        private MultiFrameApplication application;

        private Map<FrameInstanceDetailsObject, MultiFrameApplicationFrame> frames;

        public MultiApplicationShutdownWindowAdapter( MultiFrameApplication application,
                                                      Map<FrameInstanceDetailsObject, MultiFrameApplicationFrame> frames )
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
            MultiFrameApplicationFrame applicationFrame = ( MultiFrameApplicationFrame ) e.getSource();

            if ( frames.containsValue( applicationFrame ) == true )
            {
                Set<Map.Entry<FrameInstanceDetailsObject, MultiFrameApplicationFrame>> entries = frames.entrySet();

                for ( Map.Entry<FrameInstanceDetailsObject, MultiFrameApplicationFrame> entry : entries )
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
