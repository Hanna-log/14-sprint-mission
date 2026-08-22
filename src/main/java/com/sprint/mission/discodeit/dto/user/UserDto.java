package com.sprint.mission.discodeit.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.UUID;

// 유저정보 관련 dto 별개 만듬. 비밀번호 중요해서 entity에 있는거
// dto에선 빼버림. 나갈 정보만 선별해서 응답으로 내보내니까 중요정보 노출 막음.

@JsonInclude(Include.NON_NULL)
public record UserDto(
    @JsonProperty("user_id")
    UUID id,
    @JsonProperty("user_username")
    String username,
    @JsonProperty("user_email")
    String email,
    @JsonProperty("user_nickName")
    String nickName,
    @JsonProperty("user_profileId")
    UUID profileId,
    boolean online,      // UserStatus 기반 접속 여부
    Instant createdAt
) {

}
