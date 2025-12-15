package com.marotech.vending.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "device_discovery")
public class DeviceDiscovery extends BaseEntity {

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private String apiKey;

    @Column
    @NotNull
    private String deviceId;

    @Column
    @NotNull
    private String token;
}