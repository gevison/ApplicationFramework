package ge.framework.application.frame.core.dialog.panel;

import ge.framework.application.frame.core.FrameApplication;
import ge.utils.problem.ProblemBannerPanel;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 08/03/13
 * Time: 18:16
 */
public class ApplicationProblemBannerPanel extends ProblemBannerPanel
{
    private FrameApplication application;

    public ApplicationProblemBannerPanel( FrameApplication application )
    {
        super();
        this.application = application;

        initialise();
    }

    private void initialise()
    {
        setTitle( application.getDisplayName() );
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
