package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    UUID id;
    Instant createdAt;
    // 시간대 문제 없애려고 Instant 타입 사용
    // UTC 기준의 절대 시간(타임스탬프) 을 표현하는 클래스, 협정시 / DB에 사용

    protected BaseEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
    }

    // 상속할거라 접근 제어자 : 필드, 생성자 모두 protected, 자식클래스만 허용

}

