package com.marotech.vending.neo4j;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Node("user")
public class NeoUser  extends BaseNeoEntity{
    private String name;
    private String mobileNumber;
}