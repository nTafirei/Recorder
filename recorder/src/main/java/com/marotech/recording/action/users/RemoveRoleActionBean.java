package com.marotech.recording.action.users;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.RequiresOneRoleOf;
import com.marotech.recording.action.converters.UserConverter;
import com.marotech.recording.action.converters.UserRoleConverter;
import com.marotech.recording.model.Activity;
import com.marotech.recording.model.ActivityType;
import com.marotech.recording.model.User;
import com.marotech.recording.model.UserRole;
import com.marotech.recording.service.RepositoryService;
import com.marotech.recording.util.Constants;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

import java.time.LocalDateTime;
import java.util.Iterator;

@UrlBinding("/web/remove-role/{user}/{userRole}/{_eventName}")
@RequiresOneRoleOf({Constants.SYS_ADMIN})
public class RemoveRoleActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate(converter = UserConverter.class, required = true)
    private User user;
    @Getter
    @Setter
    @Validate(on = Constants.DEMOTE, converter = UserRoleConverter.class, required = true)
    private UserRole userRole;

    @Getter
    private Iterable<UserRole> roles;

    @DefaultHandler
    public Resolution list() {
        roles = getCurrentUser().getUserRoles();
        return new ForwardResolution(CHOOSE_ROLE_JSP);
    }

    @HandlesEvent(Constants.DEMOTE)
    public Resolution demote() {
        Iterator<UserRole> it = user.getUserRoles().iterator();
        while (it.hasNext()) {
            UserRole tmp = it.next();
            if (userRole.getId().equals(tmp.getId())) {
                it.remove();
                break;
            }
        }
        user.setDateLastUpdated(LocalDateTime.now());
        repositoryService.save(user);

        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.REMOVED_USER_ROLE);
            activity.setActor(getCurrentUser());
            activity.setTitle(getCurrentUser().getFullName() + " removed role: " + userRole.getRoleName() + " from " + user.getFullName());
            repositoryService.save(activity);
        }
        return new RedirectResolution(USER_DETAILS + "/" + user.getId());
    }

    @Override
    public String getNavSection() {
        return "users";
    }

    @SpringBean
    private RepositoryService repositoryService;
    public static final String USER_DETAILS = "/web/user-details";
    public static final String CHOOSE_ROLE_JSP = "/WEB-INF/jsp/users/remove-role.jsp";
}