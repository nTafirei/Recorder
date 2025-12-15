package com.marotech.vending.action;

import com.marotech.vending.util.Constants;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@SkipAuthentication
@UrlBinding("/web/logout")
public class LogoutActionBean extends BaseActionBean {

    @DefaultHandler
    public Resolution view() {
        setCurrentUser(null);
        getContext().getRequest().getSession()
                .setAttribute(Constants.ROLE_ERROR_MESSAGE, null);
        return new RedirectResolution("/web/login");
    }
}
