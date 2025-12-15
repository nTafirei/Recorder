package com.marotech.vending.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "message")
public class KeyValuePair extends BaseEntity {

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column
    private String value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ValueType valueType;
}