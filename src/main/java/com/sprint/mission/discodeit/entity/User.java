package com.sprint.mission.discodeit.entity;

import lombok.Getter;

@Getter
public class User extends Entity  {
    private String nickName;

    private User (String nickName) {
        super();
        this.nickName = nickName;
    }

    public static Builder builder() {
        return new Builder();
    }


    @Override
    public String update(String nickName) {
        if(nickName == null || nickName.isBlank()) {
            throw new IllegalArgumentException("입력된 닉네임이 없습니다.");
        }
        this.nickName = nickName;
        updateTimeStamp();
        return this.nickName;
    }

    @Override
    public String toString() {
        return this.nickName;
    }

    public static class Builder {
        private String nickName;

    public Builder nickName(String nickName){
        this.nickName = nickName;
        return this;
    }

    public User build() {
        return new User(this.nickName);
    }


    }

}









