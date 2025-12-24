package com.marotech.recording.action.inbox;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.converters.NotificationConverter;
import com.marotech.recording.model.Notification;
import com.marotech.recording.service.RepositoryService;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

import java.util.List;

@UrlBinding("/web/inbox")
public class InboxActionBean extends BaseActionBean {

    @Getter
    private List<Notification> notifications;
    @Getter
    @Setter
    @Validate(on = DELETE, converter = NotificationConverter.class, required = true)
    private Notification notification;

    @DefaultHandler
    public Resolution list() {
        notifications = repositoryService.fetchNotificationsByRecipient(getCurrentUser());
        return new ForwardResolution(LIST_JSP);
    }

    @HandlesEvent(DELETE)
    public Resolution delete() {
        repositoryService.getRepository().delete(notification);
        return new RedirectResolution(LIST);
    }

    public long getNotificationsSize() {
        return notifications.size();
    }

    @Override
    public String getNavSection() {
        return "inbox";
    }

    @Override
    protected String getErrorPage() {
        return LIST_JSP;
    }

    @SpringBean
    private RepositoryService repositoryService;

    private static final String LIST = "/web/inbox";
    private static final String LIST_JSP = "/WEB-INF/jsp/inbox/list.jsp";
}
