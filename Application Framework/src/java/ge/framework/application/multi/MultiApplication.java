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
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.framework.frame.multi.objects.FrameConfiguration;
import ge.framework.frame.multi.objects.FrameDefinition;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.properties.PropertiesDialogPage;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
public abstract class MultiApplication extends Application
{
    private static Logger logger = LogManager.getLogger( MultiApplication.class );

    private List<FrameDefinition> frameDefinitions;

    private Map<String, FrameDefinition> frameDefinitionsMap;

    private Map<FrameInstanceDetailsObject, MultiApplicationFrame> frames =
            new HashMap<FrameInstanceDetailsObject, MultiApplicationFrame>();

    private MultiApplicationClosedWindowAdapter
            multiApplicationClosedWindowAdapter = new MultiApplicationClosedWindowAdapter(this);

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
        Assert.notEmpty( frameDefinitions );

        frameDefinitionsMap = new HashMap<String, FrameDefinition>();

        for ( FrameDefinition frameDefinition : frameDefinitions )
        {
            frameDefinitionsMap.put( frameDefinition.getBeanName(), frameDefinition );
        }

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
    protected final void initialiseApplicationConfiguration()
    {
        MultiApplicationConfiguration multiApplicationConfiguration =
                ( MultiApplicationConfiguration ) getConfiguration();

        multiApplicationConfiguration.initialiseDetails( frameDefinitionsMap );

        initialiseMultiApplicationConfiguration();
    }

    protected abstract void initialiseMultiApplicationConfiguration();

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
        InitialDialog initialDialog = new InitialDialog(this);

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
            FrameDefinition frameDefinition = frameInstanceDetailsObject.getFrameDefinition();

            Class<? extends MultiApplicationFrame> frameClass =
                    ( Class<? extends MultiApplicationFrame> ) frameDefinition.getFrameClass();

            MultiApplicationFrame newApplicationFrame = ConstructorUtils.invokeConstructor(frameClass, this, frameDefinition );

            newApplicationFrame.initialise( );

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
            logger.error( e.getMessage(), e );
            System.exit( -1 );
            return false;
        }
    }

    private void removeFrame( ApplicationFrame applicationFrame )
    {
        if ( frames.containsValue( applicationFrame ) == true )
        {
            Set<Map.Entry<FrameInstanceDetailsObject, MultiApplicationFrame>> entries = frames.entrySet();

            for ( Map.Entry<FrameInstanceDetailsObject, MultiApplicationFrame> entry : entries )
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

    public boolean processNew( ApplicationFrame applicationFrame, FrameDefinition frameDefinition )
    {
        NewDialog newDialog = new NewDialog( null, this, frameDefinition );

        if ( newDialog.doModal() == true )
        {
            return openFrame( applicationFrame, newDialog.getFrameInstanceDetailsObject(), false );
        }

        return false;
    }

    public boolean processOpen( ApplicationFrame applicationFrame )
    {
        OpenDialog openDialog = new OpenDialog( null, this);

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
        if ( frameInstanceDetailsObject.doesConfigurationFileExist() == false )
        {
            MissingLocationDialog missingLocationDialog =
                    new MissingLocationDialog( applicationFrame, frameInstanceDetailsObject.getName() );

            return missingLocationDialog.showMessage();
        }
        else if ( frameInstanceDetailsObject.isConfigurationFileLocked() == true )
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

    public final List<FrameDefinition> getFrameDefinitions()
    {
        return frameDefinitions;
    }

    public final void setFrameDefinitions( List<FrameDefinition> frameDefinitions )
    {
        testInitialised();
        this.frameDefinitions = frameDefinitions;
    }

    public final int getFrameCount()
    {
        return frames.size();
    }

    public final void nextFrame()
    {
        ApplicationFrame applicationFrame = discoverFocusedFrame();

        List<ApplicationFrame> values = new ArrayList<ApplicationFrame>(frames.values());

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

        List<ApplicationFrame> values = new ArrayList<ApplicationFrame>(frames.values());

        int index = values.indexOf( applicationFrame );

        index -= 1;

        if ( index < 0 )
        {
            index = values.size() - 1;
        }

        values.get( index ).setVisible( true );
    }

    public List<MultiApplicationFrame> getFrames()
    {
        List<MultiApplicationFrame> retVal = new ArrayList<MultiApplicationFrame>( frames.values() );
        return retVal;
    }

    private class MultiApplicationClosedWindowAdapter extends WindowAdapter
    {
        private MultiApplication multiApplication;

        public MultiApplicationClosedWindowAdapter( MultiApplication multiApplication )
        {
            this.multiApplication = multiApplication;
        }

        @Override
        public final void windowClosed( WindowEvent e )
        {
            Object source = e.getSource();

            multiApplication.removeFrame( ( ApplicationFrame ) source );

            if ( frames.isEmpty() == true )
            {
                multiApplication.showInitialDialog();
            }
        }
    }

    private class MultiApplicationShutdownWindowAdapter extends WindowAdapter
    {
        private MultiApplication application;

        private Map<FrameInstanceDetailsObject, MultiApplicationFrame> frames;

        public MultiApplicationShutdownWindowAdapter( MultiApplication application,
                                                      Map<FrameInstanceDetailsObject, MultiApplicationFrame> frames )
        {
            this.application = application;
            this.frames = frames;

            for ( MultiApplicationFrame multiApplicationFrame : frames.values() )
            {
                multiApplicationFrame.addWindowListener( this );
            }
        }

        @Override
        public final void windowClosed( WindowEvent e )
        {
            MultiApplicationFrame applicationFrame = ( MultiApplicationFrame ) e.getSource();

            if ( frames.containsValue( applicationFrame ) == true )
            {
                Set<Map.Entry<FrameInstanceDetailsObject, MultiApplicationFrame>> entries = frames.entrySet();

                for ( Map.Entry<FrameInstanceDetailsObject, MultiApplicationFrame> entry : entries )
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
