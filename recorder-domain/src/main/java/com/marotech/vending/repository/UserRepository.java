package com.marotech.vending.repository;

import com.marotech.vending.neo4j.NeoUser;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<NeoUser, String> {

    NeoUser findByMobileNumber(@Param("mobileNumber") String mobileNumber);
}
