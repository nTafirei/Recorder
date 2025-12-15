package com.marotech.vending.action.users;


import com.marotech.vending.action.BaseActionBean;
import com.marotech.vending.action.RequiresOneRoleOf;
import com.marotech.vending.action.converters.UserConverter;
import com.marotech.vending.model.Activity;
import com.marotech.vending.model.ActivityType;
import com.marotech.vending.model.User;
import com.marotech.vending.model.Verified;
import com.marotech.vending.service.RepositoryService;
import com.marotech.vending.util.Constants;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UrlBinding("/web/verify-user")
@RequiresOneRoleOf({Constants.SYS_ADMIN, Constants.AGENT, Constants.CUSTOMER_SERVICE})
public class VerifyUserActionBean extends BaseActionBean {
    @Getter
    @Setter
    @Validate(converter = UserConverter.class, required = true)
    private User user;

    @HandlesEvent(VERIFY)
    public Resolution verify() {

        if (user.getPhotoId() == null) {
            getContext().getValidationErrors().add("photoid",
                    new LocalizableError("uploadphotoidfirst"));
            return new ForwardResolution(DETAILS_JSP);
        }
        user.setVerified(Verified.YES);
        user.setDateLastUpdated(LocalDateTime.now());
        repositoryService.save(user);


        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.VERIFIED_USER);
            activity.setActor(getCurrentUser());
            activity.setTitle("Verified user: " + user.getFullName());
            repositoryService.save(activity);
        }
        return new RedirectResolution(USER_DETAILS + "/" + user.getId());
    }

    @Override
    protected String getErrorPage() {
        return DETAILS_JSP;
    }

    @SpringBean
    private RepositoryService repositoryService;
    public static final String VERIFY = "verify";
    public static final String USER_DETAILS = "/web/user-details";
    public static final String DETAILS_JSP = "/WEB-INF/jsp/users/user-details.jsp";
}