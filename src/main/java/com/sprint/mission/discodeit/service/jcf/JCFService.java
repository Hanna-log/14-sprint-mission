package com.sprint.mission.discodeit.service.jcf;


import com.sprint.mission.discodeit.entity.Entity;
import com.sprint.mission.discodeit.service.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public abstract class JCFService<T extends Entity> implements Service<T> {
    private final Map<UUID,T> data;

    protected JCFService() {
        this.data = new LinkedHashMap<>();
    }

    @Override
    public T save(T entity) {
        this.data.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public Optional<T> findById(UUID id) {
         return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public List<T> findAll() {
         // return new ArrayList<>(this.data.values());
       return this.data.values().stream().toList();
    }

    @Override
    public T update(T entity) {
        this.data.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void delete(UUID id) {
        this.data.remove(id);
    }
}
