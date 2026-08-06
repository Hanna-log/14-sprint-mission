package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

// 기본 공통필드가지는 BaseEntity 추상클래스 -> updatedAt 필드가진 UpdatableEntity
// 또 분리해서 상속 추상클래스 만들기 -> 최종 상속 클래스 UserStatus
// BaseEntity -> UpdatableEntity -> UserStatus (유저상태 /마지막 접속 시간, 온라인 상태 메소드 포함)

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserStatus extends UpdatableEntity {

    private static final long serialVersionUID = 1L;
    private static final long ONLINE_THRESHOLD_MINUTES = 5;

    UUID userId;

    @NonFinal
    Instant lastActiveAt; // 마지막 활동 시간

    private UserStatus(UUID userId,Instant lastActiveAt) {
        super();
        this.userId = userId;
        this.lastActiveAt = lastActiveAt;
    }

    public void updateLastActiveAt(Instant lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
        updateTimeStamp();
    }


    public boolean isOnline() {
        return lastActiveAt.isAfter(
            Instant.now().minus(ONLINE_THRESHOLD_MINUTES,
                java.time.temporal.ChronoUnit.MINUTES));
    }

    /*

   1) 마지막 접속 시간이 현재로부터 5분 이내면 온라인 접속 메소드 추가
   2) Instant 클래스가 기본으로 제공하는 기능(java.time 패키지에 이미 내장된 메소드들)
   - java.time.temporal.ChronoUnit이라는 enum(시간단위가 뭔지) --- 여기선 분
   -. minus(숫자, 단위) : 특정 시각에서 얼마만큼을 뺀 시각을 구해라! 라는 뜻
   - 비교 a.isAfter(b) : a가 b보다 나중 시간이야? 참/거짓 판단

   지금 시각(분)에서 5분 전 시각 빼서 계산하는 거임!
   마지막 접속 시간이 지금(5분전)보다 나중이면 접속중 온라인 띄워~

   ⭐️ 예시 ::
   지금 시간 12:00인데 lastActiveAt 11:58이면 11:55분보다 나중이니까 온라인(참)
   lastActiveAt 11:50이면 11:55분보다 이전이면 오프라인(거짓)

   */

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID userId;
        private Instant lastActiceAt = Instant.now();

        public Builder userId(UUID userId) {
          this.userId = userId;
          return this;
        }

        public Builder lastActiceAt(Instant lastActiceAt) {
            this.lastActiceAt = lastActiceAt;
            return this;
        }

        public UserStatus build() {
            return new UserStatus(userId, lastActiceAt);
        }
    }









}
