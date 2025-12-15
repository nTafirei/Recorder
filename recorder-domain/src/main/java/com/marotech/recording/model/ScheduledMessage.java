package com.marotech.recording.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "scheduled_message")
public class ScheduledMessage extends BaseEntity {

    @Column(nullable = false)
    @NotNull
    private String subject;

    @Column(nullable = false)
    @NotNull
    private String body;

    @Column
    @NotNull
    private String fromEmail;

    @Column
    @NotNull
    private String toEmail;

    @Column
    private LocalDate dateSent;

    @Column(nullable = false)
    @NotNull
    private LocalDate dateScheduled;

    @Column(nullable = false)
    @NotNull
    private boolean processed;

    @OneToOne
    private Notification notification;

}