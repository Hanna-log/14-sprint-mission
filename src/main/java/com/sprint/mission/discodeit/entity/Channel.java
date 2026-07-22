package com.sprint.mission.discodeit.entity;

import lombok.Getter;

@Getter
public class Channel extends Entity  {
    private String channelName;

    private Channel(String channelName) {
        super();
        this.channelName = channelName;
    }

    public static Builder builder() {
        return new Builder();
    }


    @Override
    public String update(String channelName) {
        if(channelName == null || channelName.isBlank()) {
            throw new IllegalArgumentException("입력된 채널명이 없습니다.");
        }
        this.channelName = channelName;
        updateTimeStamp();
        return this.channelName;
    }

    @Override
    public String toString() {
        return this.channelName;
    }

    public static class Builder {
        private String channelName;

        public Builder channelName(String channelName) {
            this.channelName = channelName;
            return this;
        }

        public Channel build() {
            return new Channel(this.channelName);
        }





    }
}
