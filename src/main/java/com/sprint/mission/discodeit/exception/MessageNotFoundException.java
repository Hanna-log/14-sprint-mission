package com.sprint.mission.discodeit.exception;

import java.util.UUID;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException(UUID id) {
        super("해당 메세지를 찾을 수 없습니다. 메세지ID(" + id + ")");
    }

}
