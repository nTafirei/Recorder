package com.marotech.vending.neo4j;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public abstract class BaseNeoEntity {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    private LocalDateTime dateCreated = LocalDateTime.now();
}
