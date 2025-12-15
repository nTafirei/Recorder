package com.marotech.vending.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity {

    @Column(nullable = false, length = 32)
    private String roleName;
    @Column(nullable = false)
    private Boolean vendorSpecific;
    @Column(nullable = false)
    private Boolean merchantSpecific;
}
