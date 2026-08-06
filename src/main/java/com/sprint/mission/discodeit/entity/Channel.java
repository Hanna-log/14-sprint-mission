package com.sprint.mission.discodeit.entity;


import com.sprint.mission.discodeit.exception.InvalidChannelNameException;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


// 기본 공통필드가지는 BaseEntity 추상클래스 -> updatedAt 필드가진 UpdatableEntity
// 또 분리해서 상속 추상클래스 만들기 -> 최종 상속 클래스 Channel
// BaseEntity -> UpdatableEntity -> Channel

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Channel extends UpdatableEntity {
    private static final long serialVersionUID = 1L;

    String channelName;

    private Channel(String channelName) {
        super();
        this.channelName = channelName;
    }

    // Build로 객체 생성할거라서 생성자 접근자 private로 막기

    public static Builder builder() {
        return new Builder();
    }

    // Build로 객체 생성 메서드, 생성된 빌드 객체 타입 던져주기, 접근 제어자 public, 클래스에 붙어있음 static

    public String update(String channelName) {
        if(channelName == null || channelName.isBlank()) {
            throw new InvalidChannelNameException();
        }
        this.channelName = channelName;
        updateTimeStamp();
        return this.channelName;
    }

    @Override
    public String toString() {
        return this.channelName;
    }

    public static class Builder { // static 접근 제어자, 객체생성 없이 바로 외부에서 사용가능
        private String channelName; // 외부 입력값은 private로 접근 막음, 자바가 Builder 기본생성자 만들어줌.

        public Builder channelName(String channelName) {
            this.channelName = channelName;
            return this; // channelName 데이터값이 들어간 Builder 자신 객체를 던져줌. 메서드 체이닝.
        }

        public Channel build() {
            return new Channel(this.channelName); // 값 다 집어넣고 Channel 객체로 최종 반환
        }



    }
}
