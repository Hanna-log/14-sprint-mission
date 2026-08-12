package com.sprint.mission.discodeit.exception;

public class InvalidNicknameException extends RuntimeException {
    public InvalidNicknameException() {
        super("입력된 닉네임이 없습니다.");
    }

}
