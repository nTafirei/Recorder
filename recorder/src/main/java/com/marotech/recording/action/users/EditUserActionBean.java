package com.marotech.recording.action.users;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.RequiresOneRoleOf;
import com.marotech.recording.action.converters.EnumConverter;
import com.marotech.recording.action.converters.LocalDateConverter;
import com.marotech.recording.action.converters.UserConverter;
import com.marotech.recording.model.*;
import com.marotech.recording.service.RepositoryService;
import com.marotech.recording.util.Constants;
import com.marotech.recording.util.EmailValidator;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UrlBinding("/web/edit-user")
@RequiresOneRoleOf({Constants.SYS_ADMIN, Constants.CUSTOMER_SERVICE})
public class EditUserActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate(required = true, converter = UserConverter.class)
    private User user;
    @Getter
    @Setter
    @Validate(on = SAVE)
    private String email;
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
    private String mobileNumber;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String dateOfBirth;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true, converter = EnumConverter.class)
    protected ActiveStatus activeStatus;

    @DefaultHandler
    public Resolution view() {
        email = user.getEmail();
        dateOfBirth = user.getDateOfBirth().toString();
        mobileNumber = user.getMobileNumber();
        country = user.getAddress().getCountry();
        town = user.getAddress().getCity();
        address = user.getAddress().getAddress();
        nationalId = user.getNationalId();
        lastName = user.getLastName();
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        activeStatus = user.getActiveStatus();

        try {
            dateOfBirth = FORMATTER.format(user.getDateOfBirth());
        } catch (Exception e) {
            getContext().getValidationErrors().add("dateOfBirth",
                    new LocalizableError("invaliddateofbirth"));
            return new ForwardResolution(JSP);
        }
        return new ForwardResolution(JSP);
    }

    @HandlesEvent(DISABLE)
    public Resolution disable() throws Exception {
        user.setActiveStatus(ActiveStatus.NOT_ACTIVE);
        repositoryService.save(user);

        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.DISABLED_USER);
            activity.setActor(getCurrentUser());
            activity.setTitle(getCurrentUser().getFullName() + " disabled user " + user.getFullName() + " on " + LocalDate.now());
            repositoryService.save(activity);
        }
        return new RedirectResolution(LIST);
    }

    @HandlesEvent(ENABLE)
    public Resolution enable() throws Exception {
        user.setActiveStatus(ActiveStatus.ACTIVE);
        user.setDateLastUpdated(LocalDateTime.now());
        repositoryService.save(user);

        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.ENABLED_USER);
            activity.setActor(getCurrentUser());
            activity.setTitle(getCurrentUser().getFullName() + " enabled user " + user.getFullName() + " on " + LocalDate.now());
            repositoryService.save(activity);
        }
        return new RedirectResolution(LIST);
    }

    @HandlesEvent(SAVE)
    public Resolution save() throws Exception {

        AuthUser existing = repositoryService.fetchAuthUserByMobileNumber(mobileNumber);
        if (existing != null && !existing.getMobileNumber().equals(mobileNumber)) {
            getContext().getValidationErrors().add("mobileNumber",
                    new LocalizableError("mobilenumberistaken"));
            return new ForwardResolution(JSP);
        }
        AuthUser authUser = repositoryService.fetchAuthUserByMobileNumber(user.getMobileNumber());
        nationalId = nationalId.replaceAll("\\D+", "");
        Address theAddress = user.getAddress();
        theAddress.setAddress(address);
        theAddress.setCity(town);
        theAddress.setCountry(country);
        repositoryService.save(theAddress);

        try {
            LocalDate localDate = LocalDate.parse(dateOfBirth, FORMATTER);
            user.setDateOfBirth(localDate);
        } catch (Exception e) {
            getContext().getValidationErrors().add("dateOfBirth",
                    new LocalizableError("invaliddateofbirth"));
            return new ForwardResolution(JSP);
        }
        if (StringUtils.isNotBlank(email)) {
            if (!EmailValidator.isValidEmail(email)) {
                getContext().getValidationErrors().add("email",
                        new LocalizableError("invalidemailfound"));
                return new ForwardResolution(JSP);
            }
        }
        user.setActiveStatus(activeStatus);
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setEmail(email);
        repositoryService.save(user);
        authUser.setMobileNumber(mobileNumber);
        repositoryService.save(authUser);
        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.EDITED_USER);
            activity.setActor(getCurrentUser());
            activity.setTitle(getCurrentUser().getFullName() + " edited user " + user.getFullName() + " on " + LocalDate.now());
            repositoryService.save(activity);
        }
        return new RedirectResolution("/web/user-details/" + user.getId());
    }

    public ActiveStatus[] getActiveStatuses() {
        return ActiveStatus.values();
    }

    @Override
    public String getNavSection() {
        return "users";
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors)
            throws Exception {
        super.handleValidationErrors(errors);
        return new ForwardResolution(getErrorPage());
    }

    @Override
    protected String getErrorPage() {
        return JSP;
    }

    public String[] getCountries() {
        String str = config.getProperty("applicable.countries");
        return str.split(",");
    }

    @SpringBean
    private RepositoryService repositoryService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(LocalDateConverter.PATTERN);
    private static final String LIST = "/web/users/list";
    private static final String JSP = "/WEB-INF/jsp/users/edit.jsp";
    private static final Logger LOG = LoggerFactory.getLogger(EditUserActionBean.class);
}
