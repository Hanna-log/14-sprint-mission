package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;
import java.util.Optional;

public interface UserRepository extends Repository<User> {

    // userName, email 중복 검사에 쓸 조회 메소드
    // 기본 데이터 있는지 DB에서 조회하는거라 Repository에 존재해야함.

    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);

}
