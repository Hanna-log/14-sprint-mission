package com.sprint.mission.discodeit.exception;

import java.util.UUID;

public class UserStatusNotFoundException extends RuntimeException {

    public UserStatusNotFoundException(UUID id) {
        super("UserStatus를 찾을 수 없습니다. id:" + id);
    }
}
