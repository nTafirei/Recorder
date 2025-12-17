package com.marotech.recording.action.recordings;

import com.marotech.recording.model.User;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/web/user-recordings")
public class UserRecordingsActionBean extends BaseRecordingsActionBean {

    @Getter
    @Setter
    private User user;

    public User getUser() {
        return user;
    }

    public String getPageTitle() {
        return "User Recordings";
    }

    @Override
    public String getListPage() {
        return LIST_PAGE;
    }

    private static final String LIST_PAGE = "/web/user-recordings";
}
