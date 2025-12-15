package com.marotech.recording.service.messaging;

import com.marotech.recording.model.Notification;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService extends MessageBase {

    public void notify(Notification notification) {

        if(StringUtils.isBlank(notification.getRecipient().getEmail())){
            return;
        }
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", config.getBooleanProperty("mail.smtp.auth"));
            prop.put("mail.smtp.starttls.enable", config.getProperty("mail.smtp.starttls.enable"));
            prop.put("mail.smtp.host", config.getProperty("mail.smtp.host"));
            prop.put("mail.smtp.port", config.getProperty("mail.smtp.port"));
            prop.put("mail.smtp.ssl.trust", config.getProperty("mail.smtp.ssl.trust"));

            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(config.getProperty("mail.username"),
                            config.getProperty("mail.password"));
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(notification.getFrom()));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(notification.getRecipient().getEmail()));
            message.setSubject(notification.getSubject());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(notification.getBody(), "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
            Transport.send(message);
        }catch (Exception e){
            LOG.error("Error", e);
        }
    }
    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);
}
