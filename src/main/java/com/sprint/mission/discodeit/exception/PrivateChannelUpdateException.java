package com.sprint.mission.discodeit.exception;

public class PrivateChannelUpdateException extends RuntimeException {
    public PrivateChannelUpdateException() {
        super("PRIVATE 채널은 수정할 수 없습니다.");
    }

}
