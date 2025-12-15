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

import java.util.List;

@SkipAuthentication
@UrlBinding("/web/forgot-password")
public class ForgotPasswordActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate(on = LOGIN, required = true)
    private String mobileNumber;
    @Getter
    private String securityQuestion1;
    @Getter
    private String securityQuestion2;
    @Getter
    private String securityQuestion3;
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

    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(FORGOT_JSP);
    }

    @HandlesEvent(SEND)
    public Resolution send() {
        AuthUser authUser = repositoryService.fetchAuthUserByMobileNumber(mobileNumber);
        List<SecurityQuestion> questions = authUser.getSecurityQuestions();
        SecurityQuestion one = null, two = null, three = null;
        for (SecurityQuestion question : questions) {
            if (question.getTag() == SecurityQuestionTag.ONE) {
                securityQuestion1 = question.getQuestion();
                one = question;
            } else if (question.getTag() == SecurityQuestionTag.TWO) {
                securityQuestion2 = question.getQuestion();
                two = question;
            } else if (question.getTag() == SecurityQuestionTag.THREE) {
                securityQuestion3 = question.getQuestion();
                three = question;
            }
        }
        if (!one.getAnswer().equals(answer1)) {
            getContext().getValidationErrors().add("answer1",
                    new LocalizableError("invalidanswerfound"));
        }
        if (!two.getAnswer().equals(answer2)) {
            if (getContext().getValidationErrors().size() == 0) {
                getContext().getValidationErrors().add("answer2",
                        new LocalizableError("invalidanswerfound"));

            }
        }
        if (!three.getAnswer().equals(answer3)) {
            if (getContext().getValidationErrors().size() == 0) {
                getContext().getValidationErrors().add("answer3",
                        new LocalizableError("invalidanswerfound"));
            }
        }

        if (getContext().getValidationErrors().size() > 0) {
            return new ForwardResolution(ANSWER_JSP);
        }
        ResetInfo resetInfo = new ResetInfo();
        resetInfo.setMobileNumber(mobileNumber);
        repositoryService.save(resetInfo);

        String baseUrl = config.getProperty("app.base.url");
        String url = baseUrl + "/web/reset-password?token=" + resetInfo.getToken();

        //TODO: send them an sms and email with the link

        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.REQUESTED_PASSWORD_RESET);
            activity.setTitle(mobileNumber + " has requested to change password");
            repositoryService.save(activity);
        }
        return new RedirectResolution(NEXT_PAGE);
    }

    @HandlesEvent("answerQuestions")
    public Resolution answerQuestions() throws Exception {

        mobileNumber = mobileNumber.replaceAll(" ", "");
        AuthUser authUser = repositoryService.fetchAuthUserByMobileNumber(mobileNumber);
        if (authUser == null) {
            getContext().getValidationErrors().add("username",
                    new LocalizableError("usernotfound"));
            return new ForwardResolution(FORGOT_JSP);
        }

        List<SecurityQuestion> questions = authUser.getSecurityQuestions();
        for (SecurityQuestion question : questions) {
            if (question.getTag() == SecurityQuestionTag.ONE) {
                securityQuestion1 = question.getQuestion();
            } else if (question.getTag() == SecurityQuestionTag.TWO) {
                securityQuestion2 = question.getQuestion();
            } else if (question.getTag() == SecurityQuestionTag.THREE) {
                securityQuestion3 = question.getQuestion();
            }
        }

        return new ForwardResolution(ANSWER_JSP);
    }

    @Override
    public String getNavSection() {
        return "login";
    }

    @Override
    protected String getErrorPage() {
        return FORGOT_JSP;
    }

    @SpringBean
    private RepositoryService repositoryService;

    public static final String SEND = "send";
    public static final String LOGIN = "login";
    public static final String NEXT_PAGE = "/web/link-sent";
    public static final String FORGOT_JSP = "/WEB-INF/jsp/password/forgot.jsp";
    public static final String ANSWER_JSP = "/WEB-INF/jsp/password/answer.jsp";
}
