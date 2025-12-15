package com.marotech.recording.action.users;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.RequiresOneRoleOf;
import com.marotech.recording.action.converters.EnumConverter;
import com.marotech.recording.action.converters.UserConverter;
import com.marotech.recording.model.ActiveStatus;
import com.marotech.recording.model.User;
import com.marotech.recording.service.RepositoryService;
import com.marotech.recording.util.Constants;
import com.marotech.recording.util.Page;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


@UrlBinding("/web/users/{_eventName}")
@RequiresOneRoleOf({Constants.SYS_ADMIN, Constants.AGENT, Constants.CUSTOMER_SERVICE})
public class UsersActionBean extends BaseActionBean {

    @Getter
    private List<User> users = new ArrayList<>();
    @Getter
    @Setter
    @Validate(on = DISABLE, converter = UserConverter.class, required = true)
    private User user;
    @Getter
    @Setter
    @Validate(converter = EnumConverter.class)
    private ActiveStatus activeStatus;

    @Getter
    protected Page page = new Page();

    @DefaultHandler
    public Resolution list() {
        configurePagination();
        if (activeStatus == null) {
            activeStatus = ActiveStatus.ACTIVE;
        }
        page.setTotalItemsFound(repositoryService.countUsersByRoleAndActiveStatus(Constants.USER,
                activeStatus));
        users = repositoryService.fetchUsersByRoleAndActiveStatus(Constants.USER, activeStatus, page);
        page.setNumItemsShowing(users.size());
        return new ForwardResolution(USERS_LIST_JSP);
    }

    @HandlesEvent(DISABLE)
    public Resolution disable() {
        return new RedirectResolution(USERS_LIST);
    }

    private void configurePagination() {
        String pageNumber = getContext().getRequest().getParameter(Constants.CURR_PAGE);
        if (StringUtils.isBlank(pageNumber)) {
            pageNumber = "1";
        }
        int pageSize = config.getIntegerProperty(Constants.PAGINATION_SIZE);
        page.setCurrPage(Integer.valueOf(pageNumber));
        page.setItemsPerPage(pageSize);
    }

    public long getUsersSize() {
        return users.spliterator().getExactSizeIfKnown();
    }

    @Override
    protected String getErrorPage() {
        return USERS_LIST_JSP;
    }

    @Override
    public String getNavSection() {
        return "users";
    }

    @SpringBean
    private RepositoryService repositoryService;
    public static final String USERS_LIST = "/web/users";
    private static final String USERS_LIST_JSP = "/WEB-INF/jsp/users/list.jsp";
}
