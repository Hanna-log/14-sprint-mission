package com.sprint.mission.discodeit.entity;

import java.util.UUID;
import lombok.Getter;

@Getter
public class Message extends Entity {
    private String contents;
    private final UUID userId;
    private final UUID channelId;

    private Message(String contents, UUID userId, UUID channelId) {
        super();
        this.contents = contents;
        this.userId = userId;
        this.channelId = channelId;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String update(String contents) {
        if(contents == null || contents.isBlank()) {
            throw new IllegalArgumentException("내용을 빈칸으로 둘 수 없습니다.");
        }
        this.contents = contents;
        updateTimeStamp();
        return this.contents;
    }

    @Override
    public String toString() {
        return this.contents;
    }

    public static class Builder {
        private String contents;
        private UUID userId;
        private UUID channelId;

        public Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder channelId(UUID channelId) {
            this.channelId = channelId;
            return this;
        }

        public Message build() {
            return new Message(this.contents, this.userId, this.channelId);
        }






    }

}
