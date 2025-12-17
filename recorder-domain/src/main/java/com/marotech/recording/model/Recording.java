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
@Table(name = "recording")
public class Recording extends BaseEntity {

    @Column(nullable = false)
    private String deviceLocation;
    @NotNull
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @NotNull
    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    private Attachment attachment;
}
