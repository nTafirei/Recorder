package com.marotech.vending.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "reset_info")
public class ResetInfo extends BaseEntity {

    @Column(nullable = false)
    private String mobileNumber;
    @Column(nullable = false)
    private String token = UUID.randomUUID().toString();
}
