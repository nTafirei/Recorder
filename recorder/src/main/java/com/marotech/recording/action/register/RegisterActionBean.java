package com.marotech.recording.action.register;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.SkipAuthentication;
import com.marotech.recording.action.converters.LocalDateConverter;
import com.marotech.recording.model.*;
import com.marotech.recording.service.RepositoryService;
import com.marotech.recording.util.Constants;
import com.marotech.recording.util.EmailValidator;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@SkipAuthentication
@UrlBinding("/web/register")
public class RegisterActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate(on = SAVE)
    private String email;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String password;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String firstName;
    @Getter
    @Setter
    @Validate(on = SAVE)
    private String middleName;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String lastName;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String nationalId;
    @Getter
    @Setter
    @Validate
    private String fax;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String address;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String town;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String country;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String mobileNumber;

    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String dateOfBirth;
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
        fetchSecurityQuestions();
        setCurrentUser(null);
        return new ForwardResolution(REGISTER_JSP);
    }

    private void fetchSecurityQuestions() {
        securityQuestion1 = config.getProperty("securityQuestion1");
        securityQuestion2 = config.getProperty("securityQuestion2");
        securityQuestion3 = config.getProperty("securityQuestion3");
    }

    @HandlesEvent(SAVE)
    public Resolution save() throws Exception {
        fetchSecurityQuestions();
        nationalId = nationalId.replaceAll("\\D+", "");
        boolean exists = repositoryService.fetchAuthUserByUserName(nationalId) != null;
        if (exists) {
            getContext().getValidationErrors().add("username",
                    new LocalizableError("userexists"));
            return new ForwardResolution(REGISTER_JSP);
        }

        LocalDate dob;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LocalDateConverter.PATTERN);
            dob = LocalDate.parse(dateOfBirth, formatter);
            int minAge = config.getIntegerProperty(Constants.REG_MIN_AGE);
            if (dob.isAfter(LocalDate.now().minusYears(minAge))) {
                fetchSecurityQuestions();
                getContext().getValidationErrors().add("dateOfBirth",
                        new SimpleError("You must be at least " + minAge + " years old to register with this service"));
                return new ForwardResolution(REGISTER_JSP);
            }
        } catch (Exception e) {
            fetchSecurityQuestions();
            getContext().getValidationErrors().add("dateOfBirth",
                    new LocalizableError("invaliddateofbirth"));
            return new ForwardResolution(REGISTER_JSP);
        }

        if (StringUtils.isNotBlank(email)) {
            if (!EmailValidator.isValidEmail(email)) {
                fetchSecurityQuestions();
                getContext().getValidationErrors().add("email",
                        new LocalizableError("invalidemailfound"));
                return new ForwardResolution(REGISTER_JSP);
            }
        }
        AuthUser authUser = new AuthUser();
        mobileNumber = mobileNumber.replaceAll("\\D+", "");
        mobileNumber = mobileNumber.replaceAll(" ", "");
        authUser.setUserName(mobileNumber.toLowerCase());
        String newPassword = AuthUser.encodedPassword(password);
        authUser.setPassword(newPassword);
        authUser.setMobileNumber(mobileNumber);
        repositoryService.save(authUser);

        Address theAddress = new Address();
        theAddress.setAddress(address);
        theAddress.setCity(town);
        theAddress.setCountry(country);
        theAddress.setAddressType(AddressType.HOME);
        repositoryService.save(theAddress);

        User user = new User(firstName,
                middleName, lastName, theAddress,
                mobileNumber, nationalId);

        UserRole role = repositoryService.fetchUserRoleByRoleName(Constants.USER);
        user.getUserRoles().add(role);

        user.setDateOfBirth(dob);
        user.setEmail(email);
        user.setActiveStatus(ActiveStatus.NOT_ACTIVE);
        repositoryService.save(user);

        authUser.setUser(user);

        SecurityQuestion question = new SecurityQuestion();
        question.setQuestion(securityQuestion1);
        question.setAnswer(answer1);
        question.setTag(SecurityQuestionTag.ONE);
        repositoryService.save(question);
        authUser.getSecurityQuestions().add(question);

        question = new SecurityQuestion();
        question.setQuestion(securityQuestion2);
        question.setAnswer(answer2);
        question.setTag(SecurityQuestionTag.TWO);
        repositoryService.save(question);
        authUser.getSecurityQuestions().add(question);

        question = new SecurityQuestion();
        question.setQuestion(securityQuestion3);
        question.setAnswer(answer3);
        question.setTag(SecurityQuestionTag.THREE);
        repositoryService.save(question);
        authUser.getSecurityQuestions().add(question);

        repositoryService.save(authUser);

        List<User> list = repositoryService.fetchActiveUsersByRoleNames(Arrays.asList(ROLES));
        for (User staff : list) {
            Notification notification = new Notification();
            notification.setBody(config.getProperty(Constants.REG_BODY));
            notification.setFrom(email);
            notification.setSubject(config.getProperty(Constants.REG_SUBJECT));
            notification.setRecipient(staff);
            repositoryService.save(notification);
        }

        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.NEW_REGISTRATION_ONLINE);
            activity.setActor(user);
            activity.setTitle(user.getFullName() + " registered on line on " + LocalDate.now());
            repositoryService.save(activity);
        }
        return new RedirectResolution(PENDING);
    }

    private static final String[] ROLES = {Constants.SYS_ADMIN, Constants.CUSTOMER_SERVICE};


    @Override
    public String getNavSection() {
        return "register";
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors)
            throws Exception {
        super.handleValidationErrors(errors);
        fetchSecurityQuestions();
        return new ForwardResolution(getErrorPage());
    }

    @Override
    protected String getErrorPage() {
        return REGISTER_JSP;
    }

    public String[] getCountries() {
        String str = config.getProperty("applicable.countries");
        return str.split(",");
    }

    @SpringBean
    private RepositoryService repositoryService;

    private static final String PENDING = "/web/inbox/verification-pending";
    private static final String REGISTER_JSP = "/WEB-INF/jsp/register/register.jsp";
}
