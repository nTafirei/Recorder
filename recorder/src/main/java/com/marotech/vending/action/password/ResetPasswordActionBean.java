package com.marotech.vending.action.password;

import com.marotech.vending.action.BaseActionBean;
import com.marotech.vending.action.SkipAuthentication;
import com.marotech.vending.model.*;
import com.marotech.vending.service.RepositoryService;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;

import java.time.LocalDateTime;
import java.util.List;

@SkipAuthentication
@UrlBinding("/web/reset-password")
public class ResetPasswordActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate(required = true)
    private String token;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String password;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String answer1;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String answer2;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String answer3;
    @Getter
    private String securityQuestion1;
    @Getter
    private String securityQuestion2;
    @Getter
    private String securityQuestion3;

    @DefaultHandler
    public Resolution view() {
        ResetInfo resetInfo = repositoryService.fetchResetInfoByToken(token);
        if (resetInfo == null) {
            getContext().getValidationErrors().add("token",
                    new LocalizableError("invalidresettokenfound"));
            return new ForwardResolution(RESET_ERROR_JSP);
        }
        long ttl = config.getLongProperty("reset.link.ttl");
        if (isTTLExceeded(resetInfo.getDateCreated(), ttl)) {
            getContext().getValidationErrors().add("token",
                    new LocalizableError("resetlinkexpired"));
            return new ForwardResolution(RESET_ERROR_JSP);
        }
        String mobileNumber = resetInfo.getMobileNumber();
        AuthUser authUser = repositoryService.fetchAuthUserByMobileNumber(mobileNumber);
        if (authUser == null) {
            getContext().getValidationErrors().add("token",
                    new LocalizableError("usernotfound"));
            return new ForwardResolution(RESET_ERROR_JSP);
        }
        List<SecurityQuestion> securityQuestions = authUser.getSecurityQuestions();
        for (SecurityQuestion question : securityQuestions) {
            if (question.getTag() == SecurityQuestionTag.ONE) {
                securityQuestion1 = question.getQuestion();
            } else if (question.getTag() == SecurityQuestionTag.TWO) {
                securityQuestion2 = question.getQuestion();
            } else if (question.getTag() == SecurityQuestionTag.THREE) {
                securityQuestion3 = question.getQuestion();
            }
        }
        return new ForwardResolution(JSP);
    }

    @HandlesEvent(SAVE)
    public Resolution send() throws Exception {
        long ttl = config.getLongProperty("reset.link.ttl");
        ResetInfo resetInfo = repositoryService.fetchResetInfoByToken(token);
        if (resetInfo == null) {
            getContext().getValidationErrors().add("token",
                    new LocalizableError("invalidresettokenfound"));
            return new ForwardResolution(RESET_ERROR_JSP);
        }
        if (isTTLExceeded(resetInfo.getDateCreated(), ttl)) {
            getContext().getValidationErrors().add("token",
                    new LocalizableError("resetlinkexpired"));
            return new ForwardResolution(RESET_ERROR_JSP);
        }
        String mobileNumber = resetInfo.getMobileNumber();
        AuthUser authUser = repositoryService.fetchAuthUserByMobileNumber(mobileNumber);
        if (authUser == null) {
            getContext().getValidationErrors().add("token",
                    new LocalizableError("usernotfound"));
            return new ForwardResolution(RESET_ERROR_JSP);
        }

        boolean valid = true;
        List<SecurityQuestion> securityQuestions = authUser.getSecurityQuestions();
        for (SecurityQuestion question : securityQuestions) {
            if (question.getTag() == SecurityQuestionTag.ONE) {
                if (!question.getAnswer().equalsIgnoreCase(answer1)) {
                    valid = false;
                    getContext().getValidationErrors().add("securityQuestion1",
                            new LocalizableError("invalidanswerfound"));
                }
            } else if (question.getTag() == SecurityQuestionTag.TWO) {
                if (!question.getAnswer().equalsIgnoreCase(answer2)) {
                    valid = false;
                    getContext().getValidationErrors().add("securityQuestion2",
                            new LocalizableError("invalidanswerfound"));
                }
            } else if (question.getTag() == SecurityQuestionTag.THREE) {
                if (!question.getAnswer().equalsIgnoreCase(answer3)) {
                    valid = false;
                    getContext().getValidationErrors().add("securityQuestion3",
                            new LocalizableError("invalidanswerfound"));
                }
            }
        }
        if (!valid) {
            return new ForwardResolution(JSP);
        }
        String newPassword = AuthUser.encodedPassword(password);
        authUser.setPassword(newPassword);
        repositoryService.save(authUser);
        resetInfo.setDateCreated(resetInfo.getDateCreated().minusSeconds(ttl));
        repositoryService.save(resetInfo);
        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.RESET_PASSWORD);
            activity.setActor(authUser.getUser());
            activity.setTitle(mobileNumber + " has reset their password");
            repositoryService.save(activity);
        }
        return new RedirectResolution(NEXT_PAGE);
    }

    private boolean isTTLExceeded(LocalDateTime creationTime, long ttlSeconds) {
        LocalDateTime expirationTime = creationTime.plusSeconds(ttlSeconds);
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expirationTime);
    }

    @Override
    public String getNavSection() {
        return "login";
    }

    @Override
    protected String getErrorPage() {
        return JSP;
    }

    @SpringBean
    private RepositoryService repositoryService;

    public static final String NEXT_PAGE = "/web/login";

    public static final String RESET_ERROR_JSP = "/WEB-INF/jsp/password/reset-error.jsp";
    public static final String JSP = "/WEB-INF/jsp/password/reset-password.jsp";
}
