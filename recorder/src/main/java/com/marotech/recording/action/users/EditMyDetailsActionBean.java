package com.marotech.recording.action.users;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.converters.LocalDateConverter;
import com.marotech.recording.model.*;
import com.marotech.recording.service.RepositoryService;
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
import java.time.format.DateTimeFormatter;

@UrlBinding("/web/edit-my-details")
public class EditMyDetailsActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate(on = SAVE)
    private String email;
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


    @DefaultHandler
    public Resolution view() {
        User user = getCurrentUser();
        email = user.getEmail();
        dateOfBirth = user.getDateOfBirth().toString();
        mobileNumber = user.getMobileNumber();
        country = user.getAddress().getCountry();
        town = user.getAddress().getCity();
        address = user.getAddress().getAddress();

        try {
            dateOfBirth = FORMATTER.format(user.getDateOfBirth());
        } catch (Exception e) {
            getContext().getValidationErrors().add("dateOfBirth",
                    new LocalizableError("invaliddateofbirth"));
            return new ForwardResolution(JSP);
        }
        return new ForwardResolution(JSP);
    }

    @HandlesEvent(SAVE)
    public Resolution save() throws Exception {
        User user = getCurrentUser();
        Address theAddress = user.getAddress();
        theAddress.setAddress(address);
        theAddress.setCity(town);
        theAddress.setCountry(country);
        repositoryService.save(theAddress);

        AuthUser existing = repositoryService.fetchAuthUserByMobileNumber(mobileNumber);
        if (existing != null && !existing.getMobileNumber().equals(mobileNumber)) {
            getContext().getValidationErrors().add("mobileNumber",
                    new LocalizableError("mobilenumberistaken"));
            return new ForwardResolution(JSP);
        }

        AuthUser authUser = repositoryService.fetchAuthUserByMobileNumber(user.getMobileNumber());

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
        user.setEmail(email);
        user.setMobileNumber(mobileNumber);
        repositoryService.save(user);

        authUser.setMobileNumber(mobileNumber);
        repositoryService.save(authUser);

        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.EDITED_USER);
            activity.setActor(getCurrentUser());
            activity.setTitle(getCurrentUser().getFullName() + " their edited own details on " + LocalDate.now());
            repositoryService.save(activity);
        }
        return new RedirectResolution("/web/my-details");
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
    private static final String JSP = "/WEB-INF/jsp/users/edit-my-details.jsp";
    private static final Logger LOG = LoggerFactory.getLogger(EditMyDetailsActionBean.class);
}
