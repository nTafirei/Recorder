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
@Table(name = "recording")
public class Recording extends BaseEntity {

    private String mobileNumber;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    private Attachment attachment;
}
