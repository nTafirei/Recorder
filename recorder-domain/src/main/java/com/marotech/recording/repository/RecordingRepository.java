package com.marotech.recording.repository;

import com.marotech.recording.neo4j.NeoRecording;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordingRepository extends Neo4jRepository<NeoRecording, String> {

    NeoRecording findByName(@Param("name") String name);
}
