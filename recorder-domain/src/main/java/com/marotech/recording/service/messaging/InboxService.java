package com.marotech.recording.service.messaging;

import com.marotech.recording.model.Message;
import com.marotech.recording.model.Notification;
import com.marotech.recording.model.NotificationDestinationType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InboxService extends MessageBase {

    @Override
    public void notify(Notification notification) {
        Message message = new Message();
        message.setMessageType(NotificationDestinationType.INBOX);
        message.setSystemUser(notification.getRecipient());
        message.setSubject(notification.getSubject());
        message.setBody(notification.getBody());
        message.setDateCreated(LocalDateTime.now());
        message.setFromEmail(notification.getRecipient().getEmail());
        repositoryService.save(message);
    }
}
