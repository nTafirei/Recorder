package com.marotech.recording.action.recordings;

import com.marotech.recording.action.converters.UserConverter;
import com.marotech.recording.model.User;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.springframework.beans.factory.annotation.Value;

@UrlBinding("/web/user-recordings")
public class UserRecordingsActionBean extends BaseRecordingsActionBean {

    @Getter
    @Setter
    @Validate(required = true, converter = UserConverter.class)
    private User user;
    @Override
    public User getUser() {
        return user;
    }

    public String getPageTitle() {
        return "User Recordings";
    }
    @Override
    public String getJsp() {
        return LIST_JSP;
    }
    @Override
    public String getListPage() {
        return LIST_PAGE;
    }

    private static final String LIST_PAGE = "/web/user-recordings";
    private static final String LIST_JSP = "/WEB-INF/jsp/recordings/list.jsp";
}
