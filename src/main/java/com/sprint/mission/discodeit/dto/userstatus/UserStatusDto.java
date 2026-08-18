package com.sprint.mission.discodeit.dto.userstatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.UUID;

@JsonInclude(Include.NON_NULL)
public record UserStatusDto(
    UUID id,
    @JsonProperty("user_id")
    UUID userId,
    Instant lastActiveAt
) {
}
