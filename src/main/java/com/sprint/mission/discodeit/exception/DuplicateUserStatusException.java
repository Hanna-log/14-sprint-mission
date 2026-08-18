package com.sprint.mission.discodeit.exception;

import java.util.UUID;

public class DuplicateUserStatusException extends RuntimeException {

    public DuplicateUserStatusException() {
        super("이미 해당 유저의 UserStatus가 존재합니다.");
    }
}
