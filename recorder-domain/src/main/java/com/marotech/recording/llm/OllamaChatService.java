package com.marotech.recording.llm;

import com.marotech.recording.config.Config;
import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.models.embed.OllamaEmbedRequest;
import io.github.ollama4j.models.embed.OllamaEmbedResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.OptionsBuilder;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OllamaChatService extends ChatService {

    @Autowired
    private Config config;
    private Ollama ollama;
    private boolean valid = true;

    @PostConstruct
    public void build() {
        String ollamaHost = config.getProperty("platform.ollama.host");
        if (StringUtils.isBlank(ollamaHost)) {
            LOG.error("Could not resolve Ollama host. Will not continue");
            valid = false;
            return;
        }
        ollama = new Ollama(ollamaHost);
        //registerFunctionTools();
    }

    @Override
    public String getResponse(String model, String message, String schemaParseRequest, OllamaChatMessageRole role) throws OllamaException {
        if (!valid) {
            LOG.error("Could not resolve Ollama host. Will not continue");
            return null;
        }

        if (StringUtils.isBlank(model)) {
            LOG.error("Could not resolve Ollama model name in request. Will not continue");
            return null;
        }
        if (StringUtils.isBlank(model)) {
            LOG.error("Could not resolve Ollama chat message. Will not continue");
            return null;
        }
        OllamaChatRequest chatRequestBuilder;

        boolean useOllamaTools = config.getBooleanProperty("platform.ollama.usetools");
        if (useOllamaTools) {
            chatRequestBuilder = OllamaChatRequest.builder()
                    .withUseTools(false)
                    .withModel(model);

            if(StringUtils.isNotBlank(schemaParseRequest)) {
                OllamaChatRequest requestModel = chatRequestBuilder.withMessage(role, message).
                        withOptions((new OptionsBuilder().build())).
                        build();

                //this should parse a schema
                String rawResponse = ollama.chat(requestModel, null)
                        .getResponseModel().getMessage().getResponse();
            }

            List<Tools.Tool> tools = new ArrayList<>();
            //tools.add(voucherDatabaseTool.getSpecification());
            ollama.registerTools(tools);

            chatRequestBuilder = OllamaChatRequest.builder()
                    .withUseTools(true)
                    .withModel(model);
        } else {
            chatRequestBuilder = OllamaChatRequest.builder()
                    .withUseTools(false)
                    .withModel(model);
        }

        OllamaChatRequest requestModel = chatRequestBuilder.withMessage(role, message).
                withOptions((new OptionsBuilder().build())).
                build();

        // converse with the model
        try {
            OllamaChatResult chatResult = ollama.chat(requestModel, null);
            return chatResult.getResponseModel().getMessage().getResponse();
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return null;
    }

    @Override
    public OllamaEmbedResult embed(String model, List<String> inputs) throws IOException,
            InterruptedException, OllamaException {
        OllamaEmbedRequest modelRequest = new OllamaEmbedRequest(model, inputs);
        return ollama.embed(modelRequest);
    }

    @Override
    public String getResponse(String model, String message, String schema) throws OllamaException {
        return getResponse(model, message, schema, OllamaChatMessageRole.USER);
    }

    private static final Logger LOG = LoggerFactory.getLogger(OllamaChatService.class);
}
