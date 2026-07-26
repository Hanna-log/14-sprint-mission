package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public abstract class Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    final UUID id;
    final Long createdAt;
    Long updatedAt;


    protected Entity() {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt= this.createdAt;
    }

    /*
    public UUID getId() {
        return this.id;
    }

    public Long getCreatedAt() {
        return this.createdAt;
    }

    public Long getUpdatedAt() {
        return this.updatedAt;
    }

    */

    protected void updateTimeStamp() {
        this.updatedAt = System.currentTimeMillis();
    }

    public abstract String update(String value);

    public abstract String toString();







}