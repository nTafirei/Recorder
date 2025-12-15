package com.marotech.recording.action.password;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.SkipAuthentication;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@SkipAuthentication
@UrlBinding("/web/link-sent")
public class LinkSentActionBean extends BaseActionBean {

    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(JSP);
    }

    @Override
    public String getNavSection() {
        return "login";
    }

    public static final String JSP = "/WEB-INF/jsp/password/sent.jsp";
}
