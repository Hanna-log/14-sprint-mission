package com.sprint.mission.discodeit.exception;

import java.util.UUID;

public class BinaryContentNotFoundException extends RuntimeException {

    public BinaryContentNotFoundException(UUID id) {
        super("BinaryContent를 찾을 수 없습니다. id: " + id);
    }
}
