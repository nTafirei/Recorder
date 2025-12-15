package com.marotech.vending.service.messaging;

import com.marotech.vending.config.Config;
import com.marotech.vending.model.*;
import com.marotech.vending.service.RepositoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class MessageBase {

    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected Config config;
    protected String fromEmail = null;

    protected String formatDateNicely(LocalDateTime date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(PATTERN);
        return date.format(fmt);
    }

    @PostConstruct
    public void initMembers() {
        fromEmail = config.getProperty("app.from.email");
    }

    public abstract void notify(Notification notification);

    public static final String PATTERN = "dd MMM yyyy HH:mm";
}
