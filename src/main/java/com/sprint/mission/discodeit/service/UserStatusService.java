package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.UserStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusService {

    UserStatusDto create(UserStatusCreateRequest request);

    Optional<UserStatusDto> find(UUID id);

    List<UserStatusDto> findAll();

    UserStatusDto update(UUID id, UserStatusUpdateRequest request);
    // UserStatus 아이디로 찾아서 수정하는 메서드

    UserStatusDto updateByUserId(UUID userId, UserStatusUpdateRequest request);
    // 유저 아이디로 찾아서 수정하는 메서드

    void delete(UUID id);

}
