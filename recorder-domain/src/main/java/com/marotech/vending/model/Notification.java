package com.marotech.vending.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {

    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String body;
    @Column(nullable = false)
    private String from;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private User recipient;

    public ScheduledMessage toScheduledMessage() {
        ScheduledMessage message = new ScheduledMessage();
        message.setDateScheduled(LocalDate.now());
        message.setBody(getBody());
        message.setFromEmail(from);
        message.setSubject(getSubject());
        message.setNotification(this);
        return message;
    }
}
