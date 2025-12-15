package com.marotech.recording.action.register;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.SkipAuthentication;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@SkipAuthentication
@UrlBinding("/web/pre-register")
public class PreRegisterActionBean extends BaseActionBean {
    @DefaultHandler
    public Resolution view() {
        setCurrentUser(null);
        return new ForwardResolution(REGISTER2_JSP);
    }

    private static final String REGISTER2_JSP = "/WEB-INF/jsp/register/pre-register.jsp";
}
