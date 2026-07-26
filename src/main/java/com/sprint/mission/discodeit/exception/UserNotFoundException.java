package com.sprint.mission.discodeit.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID id) {
        super("해당 유저를 찾을 수 없습니다. 유저ID(" + id + ")");
    }

}
