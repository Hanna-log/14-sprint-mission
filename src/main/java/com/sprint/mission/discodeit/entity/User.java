package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.exception.InvalidNicknameException;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class User extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

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
            throw new InvalidNicknameException();
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









