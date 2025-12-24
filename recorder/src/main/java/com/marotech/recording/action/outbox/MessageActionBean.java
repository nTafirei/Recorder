package com.marotech.recording.action.outbox;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.SkipAuthentication;
import com.marotech.recording.model.Notification;
import com.marotech.recording.model.User;
import com.marotech.recording.service.RepositoryService;
import com.marotech.recording.util.Constants;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

import java.util.Arrays;
import java.util.List;

@SkipAuthentication
@UrlBinding("/web/message")
public class MessageActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String name;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String subject;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String message;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String email;

    @DefaultHandler
    public Resolution save() throws Exception {
        List<User> list = repositoryService.fetchActiveUsersByRoleNames(Arrays.asList(ROLES));
        for (User staff : list) {
            Notification notification = new Notification();
            notification.setBody(message);
            notification.setFrom(email);
            notification.setSubject(subject);
            notification.setRecipient(staff);
            repositoryService.save(notification);
        }
        return new RedirectResolution(NEXT_PAGE);
    }

    @HandlesEvent(SENT)
    public Resolution sent() throws Exception {
        return new ForwardResolution(SENT_JSP);
    }

    public Boolean getHasErrors() {
        return getContext().getValidationErrors().size() > 0;
    }

    @Override
    public String getNavSection() {
        return "home";
    }

    @Override
    protected String getErrorPage() {
        return SENT_JSP;
    }

    @SpringBean
    private RepositoryService repositoryService;
    public static final String NEXT_PAGE = "/web/message/sent";
    public static final String SENT = "sent";
    private static final String[] ROLES = {Constants.SYS_ADMIN, Constants.CUSTOMER_SERVICE};
    public static final String SENT_JSP = "/WEB-INF/jsp/outbox/sent.jsp";
}
