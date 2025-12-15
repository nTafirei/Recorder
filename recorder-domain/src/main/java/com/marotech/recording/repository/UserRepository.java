package com.marotech.recording.repository;

import com.marotech.recording.neo4j.NeoUser;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<NeoUser, String> {

    NeoUser findByMobileNumber(@Param("mobileNumber") String mobileNumber);
}
