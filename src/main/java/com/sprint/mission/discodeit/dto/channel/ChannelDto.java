package com.sprint.mission.discodeit.dto.channel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@JsonInclude(Include.NON_NULL)
public record ChannelDto(
    /* private final */  UUID id,
                         @JsonProperty("channel_type")
    /* private final */  ChannelType type,
                         @JsonProperty("channel_name")
    /* private final */  String name,
    /* private final */  String description,
    /* private final */  List<UUID> participantIds,  // 만약 여기가 공개채널일 경우 빈 리스트임(private일때만 활성)
    /* private final */  Instant lastMessageAt    // 메세지없으면 시간 기록될게 없으니 null로 기록됨.
) {

}
