package com.marotech.recording.action.tools;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.SkipAuthentication;
import com.marotech.recording.model.LanguageModel;
import com.marotech.recording.service.RepositoryService;
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
