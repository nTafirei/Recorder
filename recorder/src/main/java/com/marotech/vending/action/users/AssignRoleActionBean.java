package com.marotech.vending.action.users;

import com.marotech.vending.action.BaseActionBean;
import com.marotech.vending.action.RequiresOneRoleOf;
import com.marotech.vending.action.converters.UserConverter;
import com.marotech.vending.action.converters.UserRoleConverter;
import com.marotech.vending.model.Activity;
import com.marotech.vending.model.ActivityType;
import com.marotech.vending.model.User;
import com.marotech.vending.model.UserRole;
import com.marotech.vending.service.RepositoryService;
import com.marotech.vending.util.Constants;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.ArrayList;
import java.util.List;

@UrlBinding("/web/assign-role/{user}/{_eventName}")
@RequiresOneRoleOf({Constants.SYS_ADMIN})
public class AssignRoleActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate(required = true, converter = UserConverter.class)
    private User user;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true, converter = UserRoleConverter.class)
    private UserRole userRole;

    @Getter
    private Iterable<UserRole> roles;

    @DefaultHandler
    public Resolution list() {
        fetchData();
        return new ForwardResolution(CHOOSE_ROLE_JSP);
    }

    @HandlesEvent(SAVE)
    public Resolution save() {

        if (getContext().getValidationErrors().size() > 0) {
            fetchData();
            return new ForwardResolution(CHOOSE_ROLE_JSP);
        }
        user.addUserRole(userRole);

        repositoryService.save(user);

        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.ASSIGNED_USER_ROLE);
            activity.setActor(getCurrentUser());
            activity.setTitle("Assigned role: " + userRole.getRoleName() + " to " + user.getFullName());
            repositoryService.save(activity);
        }
        return new RedirectResolution(USER_DETAILS + "/" + user.getId());
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors)
            throws Exception {
        super.handleValidationErrors(errors);
        fetchData();
        return new ForwardResolution(getErrorPage());
    }

    private void fetchData() {
        List<String> roleNames = new ArrayList<>();

        for (UserRole tmp : user.getUserRoles()) {
            roleNames.add(tmp.getRoleName());
        }
        roleNames.add(Constants.AGENT);
        roles = repositoryService.fetchRolesNotInCollection(roleNames);
    }

    @Override
    public String getNavSection() {
        return "users";
    }

    @Override
    protected String getErrorPage() {
        return CHOOSE_ROLE_JSP;
    }

    public String getRolesAsJson() {
        return GSON.toJson(roles);
    }

    @SpringBean
    private RepositoryService repositoryService;
    public static final String USER_DETAILS = "/web/user-details";
    public static final String CHOOSE_ROLE_JSP = "/WEB-INF/jsp/users/choose-role.jsp";
}