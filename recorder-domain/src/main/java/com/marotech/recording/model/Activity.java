package com.marotech.recording.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "activity")
public class Activity extends BaseEntity {

    @Column(nullable = false)
    private String title;
    @Column
    private BigDecimal originalAmount;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    @OneToOne(fetch = FetchType.EAGER)
    private Recording recording;
    @ManyToOne(fetch = FetchType.EAGER)
    private User actor;
    @OneToOne(fetch = FetchType.EAGER)
    private Attachment attachment;
}