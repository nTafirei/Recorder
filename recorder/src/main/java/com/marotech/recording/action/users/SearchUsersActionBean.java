package com.marotech.recording.action.users;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.RequiresOneRoleOf;
import com.marotech.recording.model.User;
import com.marotech.recording.service.RepositoryService;
import com.marotech.recording.util.Constants;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import org.apache.commons.lang.StringUtils;

@UrlBinding("/web/search-users")
@RequiresOneRoleOf({Constants.SYS_ADMIN, Constants.AGENT, Constants.CUSTOMER_SERVICE})
public class SearchUsersActionBean extends BaseActionBean {

    @Getter
    private User user;
    @Getter
    @Setter
    @Validate(on = SEARCH)
    private String mobileNumber;
    @Getter
    @Setter
    @Validate(on = SEARCH)
    private String nationalId;

    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(SEARCH_JSP);
    }

    @HandlesEvent(SEARCH)
    public Resolution search() throws Exception {

        if (StringUtils.isNotBlank(mobileNumber)) {
            if (user == null) {
                user = repositoryService.fetchUserByMobilePhone(mobileNumber);
            }
        }
        if (StringUtils.isNotBlank(nationalId)) {
            if (user == null) {
                user = repositoryService.fetchUserByNationalId(nationalId);
            }
        }
        if (StringUtils.isNotBlank(mobileNumber) && user == null) {
            getContext().getValidationErrors().add("mobilePhone",
                    new LocalizableError("mobilephonenotfound"));
            return new ForwardResolution(SEARCH_JSP);
        }
        if (StringUtils.isNotBlank(nationalId) && user == null) {
            getContext().getValidationErrors().add("nationalId",
                    new LocalizableError("nationalidnotfound"));
            return new ForwardResolution(SEARCH_JSP);
        }
        if (user == null) {
            getContext().getValidationErrors().add("mobilePhone",
                    new LocalizableError("mobilephonenotfound"));
            return new ForwardResolution(SEARCH_JSP);
        }
        return new RedirectResolution(USER_DETAILS + "/" + user.getId());
    }

    @Override
    public String getNavSection() {
        return "users";
    }

    @SpringBean
    private RepositoryService repositoryService;

    public static final String SEARCH_JSP = "/WEB-INF/jsp/users/search.jsp";
    private static final String USER_DETAILS = "/web/user-details";

}
