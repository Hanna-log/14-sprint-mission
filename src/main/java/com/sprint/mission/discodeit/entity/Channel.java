package com.sprint.mission.discodeit.entity;


import com.sprint.mission.discodeit.exception.InvalidChannelNameException;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Channel extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    String channelName;

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
            throw new InvalidChannelNameException();
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
