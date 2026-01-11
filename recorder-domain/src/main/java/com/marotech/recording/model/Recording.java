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
    private String name;
    @Column
    private String deviceLocation;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(nullable = false)
    private String mediaType;
}
