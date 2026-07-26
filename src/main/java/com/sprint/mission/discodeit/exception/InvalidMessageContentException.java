package com.sprint.mission.discodeit.exception;

public class InvalidMessageContentException extends RuntimeException{

    public InvalidMessageContentException () {
        super("입력된 내용이 없습니다.");
    }

}
