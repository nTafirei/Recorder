package com.marotech.vending.action.users;

import com.marotech.vending.action.BaseActionBean;
import com.marotech.vending.service.RepositoryService;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

@UrlBinding("/web/my-details")
public class MyDetailsActionBean extends BaseActionBean {

    @DefaultHandler
    public Resolution list() {
        return new ForwardResolution(LIST_JSP);
    }

    @Override
    public String getNavSection() {
        return "home";
    }

    @SpringBean
    private RepositoryService repositoryService;
    private static final String LIST_JSP = "/WEB-INF/jsp/users/my-details.jsp";
}
