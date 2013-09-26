package ge.framework.application.frame.core.dialog.panel;

import com.jidesoft.dialog.BannerPanel;
import ge.framework.application.frame.core.FrameApplication;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 08/03/13
 * Time: 18:16
 */
public class ApplicationBannerPanel extends BannerPanel
{
    private FrameApplication application;

    public ApplicationBannerPanel( FrameApplication application )
    {
        super();
        this.application = application;

        initialise();
    }

    private void initialise()
    {
        setTitle( application.getName() );
        setTitleFont(
                new Font( getTitleFont().getName(), getTitleFont().getStyle(),
                          getTitleFont().getSize() + 5 ) );

        setSubtitle( application.getDescription() );
        setSubTitleFont(
                new Font( getSubTitleFont().getName(), getSubTitleFont().getStyle(),
                          getSubTitleFont().getSize() + 5 ) );

        setTitleIcon( ( ImageIcon ) application.getLargeIcon() );

        setTitleIconLocation( SwingConstants.WEST );
    }
}
