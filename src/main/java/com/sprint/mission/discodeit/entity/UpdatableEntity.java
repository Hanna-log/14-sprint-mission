package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

// 기본 공통필드가지는 BaseEntity 추상클래스 따로 빼고
// 또 거기서 updatedAt만 가지는 상속 추상 클래스 만들기 (BaseEntity->updatedAt)

@Getter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class UpdatableEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    Instant updatedAt;
    // 시간대 문제 없애려고 Instant 타입 사용
    // UTC 기준의 절대 시간(타임스탬프) 을 표현하는 클래스, 협정시 / DB에 사용
    // update는 시간 바뀌니까 final 붙이면 안됨

    protected UpdatableEntity() {
        super();
        this.updatedAt = this.createdAt;
    }

    protected void updateTimeStamp() {
        this.updatedAt = Instant.now();
    }

    // 상속할거라 접근 제어자 : 필드, 생성자 모두 protected, 자식클래스만 허용
    // updateTimeStamp()같은 경우 자식클래스 내부에서만 쓰는 메서드라 protected

}