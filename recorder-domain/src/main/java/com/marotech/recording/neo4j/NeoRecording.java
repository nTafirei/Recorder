package com.marotech.recording.neo4j;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Node("recording")
public class NeoRecording extends BaseNeoEntity{

    private String name;
    private String number;
}
