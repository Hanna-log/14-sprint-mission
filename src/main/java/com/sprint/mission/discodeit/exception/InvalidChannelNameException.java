package com.sprint.mission.discodeit.exception;

public class InvalidChannelNameException extends RuntimeException {

    public InvalidChannelNameException() {
        super("입력된 채널명이 없습니다.");
    }

}
