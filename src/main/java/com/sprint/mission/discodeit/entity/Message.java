package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.exception.InvalidMessageContentException;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Message extends Entity implements Serializable {
    static final long serialVersionUID = 1L;

    String contents;
    final UUID userId;
    final UUID channelId;

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
            throw new InvalidMessageContentException();
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
