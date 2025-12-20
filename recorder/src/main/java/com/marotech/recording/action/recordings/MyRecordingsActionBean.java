package com.marotech.recording.action.recordings;

import com.marotech.recording.model.User;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/web/my-recordings")
public class MyRecordingsActionBean extends BaseRecordingsActionBean {

    public User getUser() {
        return getCurrentUser();
    }

    @Override
    public String getListPage() {
        return LIST_PAGE;
    }

    public String getPageTitle() {
        return "My Recordings";
    }

    @Override
    public String getJsp() {
        return LIST_JSP;
    }
    @Override
    public String getNavSection() {
        return "my-recordings";
    }
    private static final String LIST_JSP = "/WEB-INF/jsp/recordings/my-list.jsp";
    private static final String LIST_PAGE = "/web/my-recordings";
}
