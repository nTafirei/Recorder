package com.marotech.vending.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "embedding")
public class Embedding extends BaseEntity {

    @Column
    // @JdbcTypeCode(SqlTypes.VECTOR)
    //@Array(length = 512) // dimensions
    private List<Double> embedding;
    @Column(length = 512)
    private String chunk;
}