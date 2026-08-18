package com.sprint.mission.discodeit.exception;

public class DuplicateReadStatusException extends RuntimeException {

    public DuplicateReadStatusException() {
        super("이미 해당 유저와 채널의 ReadStatus가 존재합니다.");
    }

}
