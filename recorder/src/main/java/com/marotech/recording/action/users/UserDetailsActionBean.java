package com.marotech.recording.action.users;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.RequiresOneRoleOf;
import com.marotech.recording.action.converters.UserConverter;
import com.marotech.recording.model.User;
import com.marotech.recording.service.RepositoryService;
import com.marotech.recording.util.Constants;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

@UrlBinding("/web/user-details/{user}/{_eventName}")
@RequiresOneRoleOf({Constants.SYS_ADMIN, Constants.AGENT, Constants.CUSTOMER_SERVICE})
public class UserDetailsActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate(converter = UserConverter.class, required = true)
    private User user;


    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(USER_DETAIL_JSP);
    }

    @Override
    protected String getErrorPage() {
        return USER_DETAIL_JSP;
    }

    @Override
    public String getNavSection() {
        return "users";
    }

    @SpringBean
    private RepositoryService repositoryService;

    public static final String USER_DETAIL_JSP = "/WEB-INF/jsp/users/user-details.jsp";
}
