package ge.framework.frame.core.dockable.logger.model.filter;

import com.jidesoft.filter.EqualFilter;
import org.apache.log4j.Level;

public class LevelFilter extends EqualFilter<Level>
{
    private Level level;

    public LevelFilter( Level level )
    {
        this.level = level;
    }

    @Override
    public String getName()
    {
        return LevelFilter.class.getSimpleName();
    }

    @Override
    public boolean isValueFiltered( Level level )
    {
        if ( level == null )
        {
            return false;
        }
        else
        {
            return !level.isGreaterOrEqual( this.level );
        }
    }
}
