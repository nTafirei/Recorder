package com.marotech.vending.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @Column
    @NotNull
    private String address;
    @Column
    @NotNull
    private String city;
    @Column
    private String province;
    @Column
    private String postcode;
    @Column
    @NotNull
    private String country = "ZW";
    @Enumerated(EnumType.STRING)
    @NotNull
    private AddressType addressType = AddressType.HOME;
}