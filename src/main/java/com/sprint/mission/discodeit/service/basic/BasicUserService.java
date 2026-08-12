package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.DuplicateEmailException;
import com.sprint.mission.discodeit.exception.DuplicateUserNameException;
import com.sprint.mission.discodeit.exception.UserNotFoundException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;


/*
스프링이 @Controller, @Repository, @Service, @Component 달린 클래스를 찾아서
객체를 만들고 빈 컨테이너에 등록해둔다.
그 객체가 필요한 다른 곳(생성자 등)에 자동으로 넣어주는 걸 "주입"이라고 한다.
*/

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class BasicUserService implements UserService {

    // BasicUserService가 필요한 Repository들 생성자 직접 주입해줌
    UserRepository userRepository; // 유저 자체 데이터 리포지토리
    BinaryContentRepository binaryContentRepository; // 유저관련 파일(프로필 등) 데이터 리포지토리
    UserStatusRepository userStatusRepository; // 유저 상태 관련 데이터 리포지토리

    @Override // 유저 새로 생성하기
    public UserDto create( //새로 생성한 유저 정보 UserDto로 응답용 데이터 객체로 반환
        UserCreateRequest request,
        BinaryContentCreateRequest profileImageRequest) {

        // 1. username, email 중복 검사하기
        if (userRepository.findByUserName(request.userName()).isPresent()) {
            throw  new DuplicateUserNameException(request.userName());
        }
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateEmailException(request.email());
        }

        /*
        ⭐️ 여기서 중복검사할때 equal이 아니라 .isPresent() 쓰는 이유? ⭐️
        .isPresent()는 "안에 값이 있니?"를 확인 하는 용도임
        Optional<User> user = userRepository.findByUsername(request.username());
        얘랑 동일함.  값이 있으면 Optional<User>가 돌아옴. 없으면 Optional.empty()로 돌아옴.
        중복 아이디 존재하면 있으니까 에러 던져버림. "찾은 User가 있는가?"

        but! equals()는 두 값이 같은지 비교하는 메서드임. >> "찾은 User와 다른 User가 같은가?"
        String a = "apple";
        String b = "apple";
        a.equals(b);
         */

        // 2. 프로필 이미지가 같이 왔으면 BinaryContent 먼저 생성
        UUID profileId = null; // 프로필 사진이 없을 수 있으니 기본 null 가정
        if (profileImageRequest != null) { // 프로필 이미지가 null이 아니면 생성
            // BinaryContentCreateRequest가 아닌이유 : 재료
            // <-> BinaryContent는 id랑 createdAt 등 추가 필드가 있는 완성된 결과물
            BinaryContent binaryContent = BinaryContent.builder()
                .fileName(profileImageRequest.fileName())
                .contentType(profileImageRequest.contentType())
                .bytes(profileImageRequest.bytes())
                .size(profileImageRequest.bytes()
                    == null ? 0 : profileImageRequest.bytes().length)
                .build();
            // 삼항연산자 : bytes()가 null이면 0을 쓰고, null이 아니면 bytes().length(파일의 실제 바이트 개수)를 써라
            // 파일 크기(byte 개수)를 자동으로 계산해서 넣어주는 것임. bytes()가 혹시 null일 상황(방어 코드)까지 대비
            binaryContentRepository.save(binaryContent);
            // 저장소에 저장함. 왜냐면 BaseEntity 생성자에서 build() 호출되는 순간 UUID.randomUUID()로 이미 id 부여함.
            profileId = binaryContent.getId();
        }

        // 3. User 생성 (-> 프로필 사진 있으면 객체 만들고 유저 생성)
        User user = User.builder()
            .userName(request.userName())
            .email(request.email())
            .password(request.password())
            .nickName(request.nickName())
            .profileId(profileId) // profileId = binaryContent.getId(); 위에서 객체 만들고 집어넣음!
            .build();
            userRepository.save(user);

        // 4. UserStatus도 같이 생성
        UserStatus userStatus = UserStatus.builder()
            .userId(user.getId())
            .lastActiveAt(Instant.now())
            .build();
        userStatusRepository.save(userStatus);

        return toDto(user, userStatus.isOnline());
    }


    @Override
    public Optional<UserDto> findById(UUID id) {
        return userRepository.findById(id)
            .map(user -> toDto(user,isOnline(user.getId())));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
            .map(user->toDto(user,isOnline(user.getId())))
            .toList();
    }

    @Override
    public UserDto update(UUID id, UserUpdateRequest request,
        BinaryContentCreateRequest profileImageRequest) {

        // 수정하려는 유저가 존재하는지 먼저 확인하고 없으면 에러던짐
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

        // 닉네임 변경
        if (request.nickName() != null) {
            user.update(request.nickName());
        }

        // 비밀번호 변경
        if (request.password() != null) {
            user.updatePassword(request.password());
        }

        // 프로필 이미지 교체 (선택)
        if (profileImageRequest != null) { // 프로필 변경 요청건이 있으면
            // 기존 프로필 이미지가 있던거 삭제해~
            if (user.getProfileId() != null) {
                binaryContentRepository.delete((user.getProfileId()));
            }

            // 기존꺼 삭제하고 또 새로 만들기 ~ BinaryContent 생성하기 (위에꺼 복붙)
            BinaryContent newBinaryContent = BinaryContent.builder()
                .fileName(profileImageRequest.fileName())
                .contentType(profileImageRequest.contentType())
                .bytes(profileImageRequest.bytes())
                .size(profileImageRequest.bytes() == null ? 0 : profileImageRequest.bytes().length)
                .build();

            binaryContentRepository.save(newBinaryContent); // 다시 객체 새로 저장함
            user.updateProfileId(newBinaryContent.getId()); // 새로 객체 만든거라 기존 프로필 아이디 교체함
        }

        userRepository.update(user); // 업데이트 된 유저정보 저장소에 새로 정보 저장함
        return toDto(user, isOnline(user.getId())); // 업데이트된 유저정보 응답용 데이터로 반환
    }



    @Override
    public void delete(UUID id) {
        // 삭제하려는 유저정보 없으면 에러 발생 ~
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

        // 삭제하려는 유저정보와 같이 연관 도메인(프로필,유저상태)도 삭제 ~
        if (user.getProfileId() != null) { // 유저 프로필 아이디 있으면
            binaryContentRepository.delete(user.getProfileId()); // 파일저장소에 가서 유저프로필아이디 입력후 삭제
        }

        userStatusRepository.findAll().stream()
            .filter(status -> status.getUserId().equals(id))
            .findFirst()
            .ifPresent(status -> userStatusRepository.delete(status.getId()));


        userRepository.delete(id); // 유저정보 최종 삭제

    }

    // ===== 아래는 반복되는 로직을 모아둔 private 헬퍼 메소드 =====

    // isOnline은 userStatusRepository(필드)를 쓰니까 static 불가함
    private boolean isOnline(UUID userId) {
        return userStatusRepository.findAll().stream()
            .filter(status -> status.getUserId().equals(userId))
            .findFirst()
            // 아이디 동일한거 찾은것 중에서 값이 일치하는 것중 첫번째 객체하나 불러와
            // 혹시 한개도 없을 수있으니 UserStatus를 그냥 안 주고 Optional<UserStatus>로 감싸서줌.
            // 이 시점의 타입: Optional<UserStatus> (있을 수도, 없을 수도 있는 상자)
            .map(UserStatus::isOnline)
            // 여기서 Map의 역할은 Stream의 map이 아니라 Optional의 map으로 작동함
            // 상자 안에 값이 있으면: 그 값을 꺼내서, 괄호 안에 적은 함수(메소드)에 넣어 실행하고, 결과를 다시 새 상자에 담아줌
            // 상자가 비어있으면: 아무것도 안 하고 그냥 빈 상자를 그대로 넘김
            // .map(status -> status.isOnline())
            // : "Optional 안에 UserStatus 객체가 있으면, 그 객체의 .isOnline() 메소드를 호출해서, 그 결과값(boolean)을 새 Optional에 담아라"는 뜻
            // map 실행 전: Optional<UserStatus> (UserStatus 객체가 든 상자)
            // map 실행 후: Optional<Boolean> (true/false가 든 상자)
            .orElse(false);
            // Optional<Boolean>의 값을 옵셔널 벗겨서 Boolean값(true/false)을
            // 꺼내는 과정이 필요함 그게 .orElse임
            // 상자 안에 값이 있으면 그값을 그대로 꺼내줌 (true 또는 false)
            // 상자가 비어있으면 기본값 false를 반환 (애초에 그 유저의 UserStatus가 존재안했으면)
            // 	Optional<Boolean>을 진짜 boolean으로 꺼내면서, 값이 없을 때 쓸 기본값을 정함.

    }
        // Entity -> DTO 변환. 여기서 password를 절대 넣지 않는 게 핵심!
        // toDto 메소드는 해당 클래스 필드 값 쓰는게 없어서 static 가능함.
        // 엔티티로 전달하는게 아니라 응답용 데이터 전달하려고 변환용 메서드 추가
        private static UserDto toDto(User user, boolean online) {
            return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getNickName(),
                user.getProfileId(),
                online,
                user.getCreatedAt()
            );
        }

    }





