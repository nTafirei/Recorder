package com.marotech.recording.action;

import com.marotech.recording.model.*;
import com.marotech.recording.service.RepositoryService;
import com.marotech.recording.util.Constants;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;

@SkipAuthentication
@UrlBinding("/web/login")
public class LoginActionBean extends BaseActionBean {

    @Getter
    private String demoUser;
    @Getter
    private String demoAdmin;
    @Getter
    @Setter
    private User user;
    @Getter
    @Setter
    @Validate(on = LOGIN, required = true)
    private String username;
    @Getter
    @Setter
    @Validate(on = LOGIN, required = true)
    private String password;
    @Getter
    @Setter
    @Validate(on = LOGIN)
    private String target;

    @DefaultHandler
    public Resolution view() {
        fetchDemoCredentials();

        String roleErrorMessage = (String) getContext()
                .getRequest().getSession()
                .getAttribute(Constants.ROLE_ERROR_MESSAGE);
        if (StringUtils.isNotBlank(roleErrorMessage)) {
            getContext().getValidationErrors().add("username",
                    new LocalizableError(roleErrorMessage));
            getContext().getRequest().getSession()
                    .setAttribute(Constants.ROLE_ERROR_MESSAGE, null);
        }
        return new ForwardResolution(WEB_INF_JSP_LOGIN_JSP);
    }

    private void fetchDemoCredentials() {
        if (getIsShowDemoCredentials()) {
            AuthUser demoAuth = repositoryService.fetchDemoUser();
            demoUser = demoAuth.getUserName();
            demoAuth = repositoryService.fetchDemoAdmin();
            demoAdmin = demoAuth.getUserName();
        }
    }

    @HandlesEvent(LOGIN)
    public Resolution login() throws Exception {

        username = username.replaceAll(" ", "");
        AuthUser authUser = repositoryService.fetchAuthUserByUserName(username.toLowerCase());

        if (authUser == null) {
            fetchDemoCredentials();
            getContext().getValidationErrors().add("username",
                    new LocalizableError("usernotfound"));
            return new ForwardResolution(WEB_INF_JSP_LOGIN_JSP);
        }

        String newPassword = AuthUser.encodedPassword(password);
        if (!newPassword.equals(authUser.getPassword())) {
            fetchDemoCredentials();
            getContext().getValidationErrors().add("username",
                    new LocalizableError("invalidcredentials"));
            return new ForwardResolution(WEB_INF_JSP_LOGIN_JSP);
        }

        user = authUser.getUser();

        if (user.getActiveStatus() != ActiveStatus.ACTIVE) {
            return new RedirectResolution(ACCOUNT_NOT_ACTIVE);
        }
        setCurrentUser(user);

        Activity activity = new Activity();
        activity.setActivityType(ActivityType.LOGGED_IN_ONLINE);
        activity.setActor(getCurrentUser());
        activity.setTitle(getCurrentUser().getFullName() + " logged in online on " + LocalDateTime.now());
        repositoryService.save(activity);

        if (!StringUtils.isBlank(target)) {
            return new RedirectResolution(target);
        }

        return new RedirectResolution(NEXT_PAGE);
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors)
            throws Exception {
        super.handleValidationErrors(errors);
        fetchDemoCredentials();
        return new ForwardResolution(getErrorPage());
    }

    public boolean getIsShowDemoCredentials() {
        String env = config.getProperty("env.deployment");
        if ("prod".equalsIgnoreCase(env)) {
            return false;
        }
        return true;
    }

    @Override
    public String getNavSection() {
        return "login";
    }

    @Override
    protected String getErrorPage() {
        return WEB_INF_JSP_LOGIN_JSP;
    }


    @SpringBean
    private RepositoryService repositoryService;

    public static final String LOGIN = "login";
    public static final String NEXT_PAGE = "/web/inbox";

    public static final String ACCOUNT_NOT_ACTIVE = "/web/account-not-active";
    public static final String WEB_INF_JSP_LOGIN_JSP = "/WEB-INF/jsp/login.jsp";
}
