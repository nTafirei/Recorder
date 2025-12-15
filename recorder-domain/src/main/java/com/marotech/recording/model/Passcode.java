package com.marotech.recording.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "passcode")
public class Passcode extends BaseEntity {

    @NotNull
    @Column
    private String currentCode = "xxxx";

    @Column
    @NotNull
    private Integer passCodeTimeToLive = 120; //in seconds
}