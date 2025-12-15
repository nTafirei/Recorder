package com.marotech.vending.action.tools;

import com.marotech.vending.action.BaseActionBean;
import com.marotech.vending.action.SkipAuthentication;
import com.marotech.vending.model.LanguageModel;
import com.marotech.vending.service.RepositoryService;
import lombok.Getter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.ArrayList;
import java.util.List;

@SkipAuthentication

@UrlBinding("/web/llms")
public class LanguageModelsActionBean extends BaseActionBean {

    @Getter
    private List<LanguageModel> models = new ArrayList<>();

    @DefaultHandler
    public Resolution list() {
        models = repositoryService.fetchAllLanguageModels();
        return new ForwardResolution(LIST_JSP);
    }

    public int getModelsSize() {
        return models.size();
    }

    @Override
    public String getNavSection() {
        return "tools";
    }

    @SpringBean
    private RepositoryService repositoryService;
    private static final String LIST_JSP = "/WEB-INF/jsp/tools/llms.jsp";
}
