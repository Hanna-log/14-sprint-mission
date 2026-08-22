package com.sprint.mission.discodeit.dto.channel;

import jakarta.validation.constraints.NotBlank;

public record ChannelUpdateRequest(
    @NotBlank(message = "수정할 채널명을 비워둘 수 없습니다.")
    String name,
    String description
) {

}
