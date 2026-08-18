package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.DuplicateReadStatusException;
import com.sprint.mission.discodeit.exception.DuplicateUserStatusException;
import com.sprint.mission.discodeit.exception.UserNotFoundException;
import com.sprint.mission.discodeit.exception.UserStatusNotFoundException;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;
    // UserStatus 만들려면 User가 실제로 존재하는지 확인해야 해서 UserRepository도 필요

    @Override
    public UserStatusDto create(UserStatusCreateRequest request) {
        // 1. User가 실제로 존재하는지 userRepository가서 확인해봄
        userRepository.findById(request.userId())
            .orElseThrow(() -> new UserNotFoundException(request.userId()));

        // 2.이미 만들어진 UserStatus가 있는지 중복여부 확인
        userStatusRepository.findByUserId(request.userId())
            .ifPresent(status -> {
                throw new DuplicateUserStatusException();
            });
        /*
        if문 버전 >>
        Optional<UserStatus> found = userStatusRepository.findByUserId(request.userId());
if (found.isPresent()) {
    throw new DuplicateUserStatusException();
    값이 존재하면 오류 던져라

    .ifPresent은 Optional이 갖고 있는 메소드 :
    안에 값이 있으면 괄호안 내용 실행해

    optional.ifPresent(무언가);
    Optional 안에 값이 있으면 → 무언가를 실행
Optional 안이 비어있으면 → 아무것도 안 하고 그냥 넘어감


         */

        // 3. 위에 유저가 있는지, 중복여부 검증 다 거치고 이제 새로 만들어서 저장함.
        UserStatus userStatus = UserStatus.builder()
            .userId(request.userId())
            .build();
        userStatusRepository.save(userStatus);
        return toDto(userStatus);
    }

    @Override
    public Optional<UserStatusDto> find(UUID id) {
        return userStatusRepository.findById(id)
            // Optional - map 한 번으로 바로 새로운 Optional이 나옴, 그걸 바로 return
            .map(BasicUserStatusService::toDto); // <- 이 자체로 이미 결과물(Optional)이 완성됨
    }

    /*
     1. 스트림의 .toList(), .forEach()처럼 종결문 안쓰는 이유
     : return 자체가 종결문. Optional<UserStatusDto>가 최종 결과물이
     완성되어서 return으로 돌려줌.

     2. 값있으면
     Optional<UserStatus>  --.map(toDto)-->  Optional<UserStatusDto>
     값 없으면 Optional.empty() -> .map()는 빈상자라서 값 변환할게 없어서 스킵 -->Optional.empty()

    3. find는 단순 조회하는거라 찾는 값 없다고 예외 발생X
    --- 반면 update,delete는 대상이 있어야 작동하기때문에 예외 던져야함
    반드시 값이 있어야하는 것만 예외처리 넣음.
     */

    @Override
    public List<UserStatusDto> findAll() {
        List<UserStatus> userStatuses = userStatusRepository.findAll();

        List<UserStatusDto> result = new ArrayList<>();
        for (UserStatus userStatus : userStatuses) {
            result.add(toDto(userStatus));
            // result 담는 바구니 타입이 UserStatusDto라서 add하면 잘들어감.
            // 이미 toDto로 바뀐 객체라
        }
        return result;
    }

    @Override // 1. UserStatus의 고유의 id로 검색해서 찾아서 업데이트 수정
    public UserStatusDto update(UUID id, UserStatusUpdateRequest request) {
        UserStatus userStatus = userStatusRepository.findById(id)
            .orElseThrow(() -> new UserStatusNotFoundException(id));

        userStatus.updateLastActiveAt(request.lastActiveAt());
        userStatusRepository.update(userStatus);
        return toDto(userStatus);
    }

    @Override // 2. userId 기준으로 UserStatus를 찾아 업데이트 수정
    public UserStatusDto updateByUserId(UUID userId, UserStatusUpdateRequest request) {
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
            .orElseThrow(() -> new UserStatusNotFoundException(userId));

        userStatus.updateLastActiveAt(request.lastActiveAt());
        userStatusRepository.update(userStatus);
        return toDto(userStatus);
    }

    @Override
    public void delete(UUID id) {
        userStatusRepository.findById(id)
            .orElseThrow(() -> new UserStatusNotFoundException(id));

        userStatusRepository.delete(id);

    }

    // ========= toDto(UserStatusDto)로 만드는 공통 메서드 추출 아래로 분리 ========
    // 외부에서 호출되는게 아니라 여기서만 쓸거라 private, 여기 객체 값따로 쓸거없고 UserStatus만 쓸거라서 static 가능
    private static UserStatusDto toDto(UserStatus userStatus) {
        return new UserStatusDto(
            userStatus.getId(),
            userStatus.getUserId(),
            userStatus.getLastActiveAt()
        );
    }


}
