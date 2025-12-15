package com.marotech.recording.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marotech.recording.api.ServiceResponse;
import com.marotech.recording.config.Config;
import com.marotech.recording.gson.CustomExclusionStrategy;
import com.marotech.recording.gson.LocalDateTimeAdapter;
import com.marotech.recording.model.User;
import com.marotech.recording.security.SecurityAwareActionBean;
import com.marotech.recording.service.HazelcastService;
import com.marotech.recording.util.Constants;
import com.marotech.recording.util.VendActionBeanContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public abstract class BaseActionBean extends SecurityAwareActionBean implements
        ValidationErrorHandler {
    protected String target;
    protected ObjectMapper mapper = new ObjectMapper();
    protected static final String DELETE = "delete";
    protected static final String DISABLE = "disable";
    protected static final String ENABLE = "enable";
    public static final String EXPORT = "export";
    protected static final String SAVE = "save";
    protected static final String EDIT = "edit";
    protected static final String SEARCH = "search";
    protected static final String DEPLOYMENT_ENV = "env.deployment";
    private Map<String, String> navSectionClasses = new HashMap<>();
    protected VendActionBeanContext context;

    protected boolean isPresent(Object o) {
        return o != null;
    }

    protected static final Gson GSON = new GsonBuilder()
            .setExclusionStrategies(new CustomExclusionStrategy())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    protected void addCookie() {
        Cookie cookie = new Cookie("JSESSIONID", getContext().getRequest().getSession().getId());
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        getContext().getResponse().addCookie(cookie);
    }

    protected String formatDateNicely(LocalDateTime date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return date.format(fmt);
    }

    public User getUser() {
        return getCurrentUser();
    }

    protected Resolution getHomePage() {
        return new RedirectResolution(WEB_USER_HOME);
    }

    protected Resolution jsonResolution(Object o) {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        return new StreamingResolution("application/json",
                new ByteArrayInputStream(json.getBytes()));
    }

    protected Resolution streamingResolution(int status, String data) {
        return new StreamingResolution("application/txt") {
            public void stream(final HttpServletResponse response) {
                try {
                    final OutputStream out = response.getOutputStream();
                    out.write(data.getBytes());
                    response.setStatus(status);
                    response.setHeader("Access-Control-Allow-Origin", "*");
                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage());
                }
            }
        };
    }


    public String getPurchaseCurrency() {
        return config.getProperty("app.purchase.currency");
    }

    protected boolean shouldAudit() {
        return config.getBooleanProperty("app.should.audit");
    }

    public String getMessageForKey(String key) {
        // StripesResources.properties must be on the classpath
        ResourceBundle bundle = ResourceBundle.getBundle("StripesResources");
        return bundle.getString(key);
    }

    public String getNavSection() {
        return "";
    }

    public ActionBeanContext getContext() {
        return context;
    }

    public String getCopyRight() {
        return config.getProperty("copyright");
    }

    public String getClassName() {
        return this.getClass().getName();
    }

    public void setContext(ActionBeanContext context) {
        this.context = (VendActionBeanContext) context;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> printNormalizedRequestParameters() {

        LOG.debug("-----------------START REQUEST PARAMS-----------------------");

        Map<String, String[]> requestParameters = context.getRequest()
                .getParameterMap();
        Map<String, String> map = new HashMap<String, String>();

        for (Map.Entry<String, String[]> entry : requestParameters.entrySet()) {
            String name = entry.getKey().trim();
            String[] values = getContext().getRequest()
                    .getParameterValues(name);

            if (values != null) {
                for (String val : values) {
                    map.put(name, val);
                    LOG.debug(name + " = " + val);
                }
            }
        }

        LOG.debug("-----------------END REQUEST PARAMS-----------------------");
        return map;
    }

    protected Resolution opFailedResponseJsonResolution(int code, String message) {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setCode(code);
        serviceResponse.setMessage(message);
        try {
            String jsonString = mapper.writeValueAsString(serviceResponse);

            return new StreamingResolution("application/json") {
                public void stream(final HttpServletResponse response) {
                    try {
                        final OutputStream out = response.getOutputStream();
                        out.write(jsonString.getBytes());
                        response.setHeader("Access-Control-Allow-Origin", "*");
                    } catch (Exception e) {
                        throw new IllegalStateException(e.getMessage());
                    }
                }
            };
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return null;
    }

    protected Resolution jsonResolution(String jsonString) {
        return new StreamingResolution("application/json") {
            public void stream(final HttpServletResponse response) {
                try {
                    final OutputStream out = response.getOutputStream();
                    out.write(jsonString.getBytes());
                    response.setHeader("Access-Control-Allow-Origin", "*");
                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage());
                }
            }
        };
    }

    public User getCurrentUser() {
        if (config.getBooleanProperty(Constants.USE_HAZELCAST)) {
            return hazelcastService.getCurrentUser(getContext().getRequest().getSession().getId());
        }
        return (User) getContext().getRequest().getSession()
                .getAttribute(Constants.LOGGED_IN_USER);
    }

    public void setCurrentUser(User user) {
        if (config.getBooleanProperty(Constants.USE_HAZELCAST)) {
            hazelcastService.setCurrentUser(user, getContext().
                    getRequest().getSession().getId());
        }
        getContext().getRequest().getSession()
                .setAttribute(Constants.LOGGED_IN_USER, user);
    }

    protected String getDeploymentEnv() {
        return config.getProperty(DEPLOYMENT_ENV);
    }

    public boolean getIsLoggedUser() {
        return getCurrentUser() != null;
    }

    public String getBaseUrl() {
        return config.getProperty(getDeploymentEnv() + ".servername")
                + config.getProperty("context.path");
    }

    public String getSecureBaseUrl() {
        return config.getProperty(getDeploymentEnv() + ".secure.servername")
                + config.getProperty("context.path");
    }

    public boolean isAnnotationPresent(Class clazz) {
        return getClass().isAnnotationPresent(clazz);
    }

    public Map<String, String> getNavSectionClasses() {
        return navSectionClasses;
    }

    public boolean getIsLoggedIn() {
        return getCurrentUser() != null;
    }

    public int getYear() {
        LocalDate date = LocalDate.now();
        return date.getYear();
    }

    public void setContext(VendActionBeanContext context) {
        this.context = context;
    }

    public String getLetter() {
        return "All";
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors)
            throws Exception {
        if (errors.size() > 0) {
            LOG.error("Please override handleValidationErrors() in your current action bean");
            LOG.error("To see the on the current page, please override  with the name of the current page and remember to call this tag <stripes:errors/>");
            Set<String> keys = errors.keySet();
            for (String key : keys) {
                LOG.error("Validation Error Key : "
                        + errors.get(key).get(0).getFieldName());
                LOG.error("Params are : " + printNormalizedRequestParameters());
            }
        }
        return new ForwardResolution(getErrorPage());
    }

    protected String getErrorPage() {
        return "/WEB-INF/jsp/error.jsp";
    }

    public void alert(Object obj) {
        LOG.debug("" + obj);
    }

    public String getImagePath() {
        return getContext().getRequest().getContextPath() + getImagesDir();
    }

    public String getImageFolder() {
        return "images";
    }

    public String getServerPath() {
        return config.getProperty(getDeploymentEnv() + ".servername")
                + config.getProperty("context.path");
    }

    public String getImagesDir() {
        String dir = config.getProperty("app.images.dir");
        if (dir == null) {
            return "/images";
        }
        return dir;
    }

    public String getScriptDir() {
        String dir = config.getProperty("app.script.dir");
        if (dir == null) {
            return "/script";
        }
        return dir;
    }

    public String getCssDir() {
        String dir = config.getProperty("app.css.dir");
        if (dir == null) {
            return "/css";
        }
        return dir;
    }

    public String getCssPath() {
        return getContext().getRequest().getContextPath() + getCssDir();
    }

    public String getScriptPath() {
        return getContext().getRequest().getContextPath() + getScriptDir();
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getDefaultCountryCode() {
        return config.getProperty("default.country.code");
    }

    @SpringBean
    protected Config config;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Config getConfig() {
        return config;
    }

    @SpringBean
    private HazelcastService hazelcastService;

    private static final Logger LOG = LoggerFactory.getLogger(com.marotech.recording.action.BaseActionBean.class);

    private static final String WEB_USER_HOME = "/web/home";
}
