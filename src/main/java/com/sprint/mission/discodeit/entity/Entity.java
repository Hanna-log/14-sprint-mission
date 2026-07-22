package com.sprint.mission.discodeit.entity;

import java.util.UUID;
import lombok.Getter;

@Getter
public abstract class Entity {

    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;


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