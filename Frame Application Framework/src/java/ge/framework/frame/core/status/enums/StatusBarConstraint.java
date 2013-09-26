package ge.framework.frame.core.status.enums;

import com.jidesoft.swing.JideBoxLayout;

public enum StatusBarConstraint
{
    FLEXIBLE( JideBoxLayout.FLEXIBLE ),
    FIX( JideBoxLayout.FIX ),
    VARY( JideBoxLayout.VARY );

    private String constraint;

    StatusBarConstraint( String constraint )
    {
        this.constraint = constraint;
    }

    public String getConstraint()
    {
        return constraint;
    }
}
