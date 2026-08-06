package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.exception.InvalidNicknameException;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

// 기본 공통필드가지는 BaseEntity 추상클래스 -> updatedAt 필드가진 UpdatableEntity
// 또 분리해서 상속 추상클래스 만들기 -> 최종 상속 클래스 User
// BaseEntity -> UpdatableEntity -> User

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends UpdatableEntity {
    private static final long serialVersionUID = 1L;

    String nickName;
    UUID profileId; // nullable(값이 없어도 됨) -> 0..1로 표시 (프로필 이미지 필수아님)!

    private User (String nickName,UUID profileId) {
        super();
        this.nickName = nickName;
        this.profileId = profileId;
    }


    public static Builder builder() {
        return new Builder();
    }


    public String update(String nickName) {
        if(nickName == null || nickName.isBlank()) {
            throw new InvalidNicknameException();
        }
        this.nickName = nickName;
        updateTimeStamp();
        return this.nickName;
    }

    public void updateProfileId(UUID profileId) {
        this.profileId = profileId;
        updateTimeStamp();
    }

    @Override
    public String toString() {
        return this.nickName;
    }

    public static class Builder {
        private String nickName;
        private UUID profileId;

    public Builder nickName(String nickName){
        this.nickName = nickName;
        return this;
    }

    public Builder profileId(UUID profileId){
        this.profileId = profileId;
        return this;
    }

    public User build() {
        return new User(this.nickName,this.profileId);
    }


    }

}









