package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// 전엔 제네릭<T>으로 T타입 User 객체 그대로 던져줬는데 이젠 DTO로 요청,응답처리해서 안맞아서 버림
public interface UserService {

    UserDto create(UserCreateRequest request,
        BinaryContentCreateRequest profileImageRequest);

    // 유저생성Dt(업데이트랑 별도 분리) + 프로필 수정 Dto -> 생성 -> 유저 데이터 응답 Dto 반환

    Optional<UserDto> findById(UUID id);
    // Id로 조회 ->없는지 있는지 Optional 확인 -> 유저 데이터 응답 Dto 반환

    List<UserDto> findAll();
    // 유저 데이터 등록된거 전부 찾아서 유저 응답 Dto 반환

    UserDto update(UUID id, UserUpdateRequest request,
        BinaryContentCreateRequest profileImageRequest);
    // 유저 id 값, 업데이트 요청클래스(별도분리), 프로필 수정 요청클래스 dto 보내서
    // 업데이트하고 반환값으로 UserDto 받음

    void  delete(UUID id);
    // id만 넣으면 기존 데이터 삭제 처리, 반환값 없음.




}
