package com.sprint.mission.discodeit.exception;

public class DuplicateUserNameException extends RuntimeException {
    public DuplicateUserNameException(String userName) {
        super("이미 사용 중인 userName입니다: " + userName);
    }
}
