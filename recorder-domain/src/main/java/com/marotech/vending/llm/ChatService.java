package com.marotech.vending.llm;

import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.embed.OllamaEmbedResult;

import java.io.IOException;
import java.util.List;

public abstract class ChatService {

    public abstract OllamaEmbedResult embed(String model, List<String> inputs)
            throws IOException, InterruptedException, OllamaException;

    public abstract String getResponse(String model, String message, String schemaParseRequest) throws OllamaException;

    public abstract String getResponse(String model, String message, String schemaParseRequest, OllamaChatMessageRole role) throws OllamaException;

}
