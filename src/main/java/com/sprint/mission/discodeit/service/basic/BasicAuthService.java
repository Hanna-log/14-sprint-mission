package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.auth.LoginRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.InvalidCredentialException;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BasicAuthService implements AuthService {

    // 유저정보 데이터가지고 인증,검증 확인 거치는거라
    // 다른 서비스 연결 없이 UserRepository만 연결함.
    UserRepository userRepository;

    @Override
    public UserDto login(LoginRequest request) {

        User user = userRepository.findByUserName(request.userName())
            .filter(u->u.getPassword().equals(request.password()))
            .orElseThrow(InvalidCredentialException::new);
        // 로그인 요청에서 들어온 아이디값으로 유저 정보 찾고 거기나온거에서 비밀번호 대조해보고 다르면 예외 던져

        return new UserDto(
            user.getId(),
            user.getUserName(),
            user.getEmail(),
            user.getNickName(),
            user.getProfileId(),
            false,
            // UserDto로 변환 (비밀번호 제외)
            //DTO 만들 때 online 값을 넣어야 하는데,
            // online 상태는 AuthService 책임이 아니므로 기본값 false
            // 로그인 성공 = 온라인 접속 true ❌ 의미 일치하는 게 아님
            user.getCreatedAt()
        );
    }
}
