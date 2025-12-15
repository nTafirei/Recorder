package com.marotech.vending.action.site;

import com.marotech.vending.action.BaseActionBean;
import com.marotech.vending.action.SkipAuthentication;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@SkipAuthentication
@UrlBinding("/web/help")
public class HelpActionBean extends BaseActionBean {

    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(JSP);
    }

    public String getNavSection() {
        return "home";
    }

    private static final String JSP = "/WEB-INF/jsp/site/help.jsp";
}
