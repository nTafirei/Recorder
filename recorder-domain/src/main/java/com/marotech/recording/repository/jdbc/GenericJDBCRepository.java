package com.marotech.recording.repository.jdbc;

import com.marotech.recording.model.BaseEntity;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@DependsOn("entityManagerFactory")
public interface GenericJDBCRepository<T extends BaseEntity> extends CrudRepository<T, String> {
}

