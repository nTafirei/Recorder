package com.marotech.recording.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "message")
public class Message extends BaseEntity {

    @Column(nullable = false)
    @NotNull
    private String subject;

    @Column(nullable = false, length = 512)
    @NotNull
    private String body;

    @Column(length = 64)
    @NotNull
    private String fromEmail;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private NotificationDestinationType messageType;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User systemUser;
}