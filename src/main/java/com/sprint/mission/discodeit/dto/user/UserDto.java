package com.sprint.mission.discodeit.dto.user;

import java.time.Instant;
import java.util.UUID;

// 유저정보 관련 dto 별개 만듬. 비밀번호 중요해서 entity에 있는거
// dto에선 빼버림. 나갈 정보만 선별해서 응답으로 내보내니까 중요정보 노출 막음.
public record UserDto(
    UUID id,
    String username,
    String email,
    String nickName,
    UUID profileId,
    boolean online,      // UserStatus 기반 접속 여부
    Instant createdAt
) {

}
