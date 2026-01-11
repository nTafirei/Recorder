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
    @Column(nullable = false)
    private String mobileNumber;
    @Column(nullable = false)
    private String mediaType;
}
