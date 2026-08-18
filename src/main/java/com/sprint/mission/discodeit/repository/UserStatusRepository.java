package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusRepository extends Repository<UserStatus> {

    // userId로 UserStatus 찾기 (한 유저 = UserStatus 딱 1개라서 Optional)
    Optional<UserStatus> findByUserId(UUID userId);


}
