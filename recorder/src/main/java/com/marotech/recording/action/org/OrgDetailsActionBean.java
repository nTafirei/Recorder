package com.marotech.recording.action.org;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.RequiresOneRoleOf;
import com.marotech.recording.action.converters.OrgConverter;
import com.marotech.recording.model.Org;
import com.marotech.recording.model.OrgType;
import com.marotech.recording.util.Constants;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;


@UrlBinding("/web/org-details")
@RequiresOneRoleOf({Constants.SYS_ADMIN, Constants.CUSTOMER_SERVICE})
public class OrgDetailsActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate(required = true, converter = OrgConverter.class)
    private Org org;

    @DefaultHandler
    public Resolution list() {
        return new ForwardResolution(DETAILS_JSP);
    }

    @Override
    public String getNavSection() {
        if (org != null && OrgType.MERCHANT == org.getOrgType()) {
            return "merchants";
        }
        return "vendors";
    }

    @Override
    protected String getErrorPage() {
        return DETAILS_JSP;
    }

    private static final String DETAILS_JSP = "/WEB-INF/jsp/org/details.jsp";
}
