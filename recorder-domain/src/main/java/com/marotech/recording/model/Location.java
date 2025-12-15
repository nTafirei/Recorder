package com.marotech.recording.model;

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
@Table(name = "location")
public class Location extends BaseEntity {
    @Column
    private String name;
    @Column
    private String description;

}