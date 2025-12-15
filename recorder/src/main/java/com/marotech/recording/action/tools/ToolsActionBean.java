package com.marotech.recording.action.tools;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.SkipAuthentication;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@SkipAuthentication
@UrlBinding("/web/tools")
public class ToolsActionBean extends BaseActionBean {

    @DefaultHandler
    public Resolution list() {
        return new ForwardResolution(LIST_JSP);
    }

    @Override
    public String getNavSection() {
        return "tools";
    }

    private static final String LIST_JSP = "/WEB-INF/jsp/tools/list.jsp";
}
