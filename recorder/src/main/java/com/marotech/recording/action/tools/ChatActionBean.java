package com.marotech.recording.action.tools;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.SkipAuthentication;
import com.marotech.recording.llm.ChatService;
import com.marotech.recording.llm.MockChatService;
import com.marotech.recording.llm.OllamaChatService;
import com.marotech.recording.service.RepositoryService;
import io.github.ollama4j.exceptions.OllamaException;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

@SkipAuthentication
@UrlBinding("/web/chat")
public class ChatActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate(on = SEARCH, required = true)
    private String request;
    @Getter
    private String answer = "I am not sure how to respond to that";

    @DefaultHandler
    public Resolution list() {
        answer = "";
        return new ForwardResolution(LIST_JSP);
    }

    @HandlesEvent(SEARCH)
    public Resolution request() throws OllamaException {
        setChatService();
        String model = config.getProperty("platform.active.model");
        answer = chatService.getResponse(model, request, null);
        return new ForwardResolution(LIST_JSP);
    }

    private void setChatService() {
        String languageModel = config.getProperty("platform.language.service.option");
        if ("mock".equalsIgnoreCase(languageModel)) {
            chatService = mockChatService;
        } else {
            chatService = ollamaChatService;
        }
    }

    @Override
    public String getNavSection() {
        return "tools";
    }

    private ChatService chatService;
    @SpringBean
    private RepositoryService repositoryService;
    @SpringBean
    private MockChatService mockChatService;
    @SpringBean
    private OllamaChatService ollamaChatService;
    private static final String LIST_JSP = "/WEB-INF/jsp/tools/chat.jsp";
}
