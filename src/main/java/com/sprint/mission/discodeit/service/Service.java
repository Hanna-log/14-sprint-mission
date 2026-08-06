package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.UpdatableEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Service<T extends UpdatableEntity> {

    /* public abstract */ T save(T entity);
    /* public abstract */ Optional<T> findById(UUID id);
    /* public abstract */ List<T> findAll();
    /* public abstract */ T update(T entity);
    /* public abstract */ void delete(UUID id);

    default String findIdAsString(UUID id) {
        return findById(id)
            .map(Object::toString)
            .orElse("해당 데이터를 찾을 수 없습니다. ID(" + id + ")");
    }



}

