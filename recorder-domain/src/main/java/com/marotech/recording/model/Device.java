package com.marotech.recording.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "device")
public class Device extends BaseEntity {

    @Column
    @NotNull
    private String product;
    @Column
    @NotNull
    private String apiLevel;
    @Column
    @NotNull
    private String os;
    @Column
    @NotNull
    private String manufacturer;
    @Column
    @NotNull
    private String model;
    @Column
    @NotNull
    private String deviceId;
    @ManyToOne
    @NotNull
    private User registeredBy;
    @Enumerated(EnumType.STRING)
    private @NotNull DeviceStatus deviceStatus = DeviceStatus.ACTIVE;
}