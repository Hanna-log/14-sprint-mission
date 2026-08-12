package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BaseEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T extends BaseEntity> {

    T save(T entity);
    Optional<T> findById(UUID id);
    List<T> findAll();
    T update(T entity);
    void delete(UUID id);



}
