package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.auth.LoginRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.entity.User;

// 인증(Authentication)을 담당하는 서비스 계층 :
// 일반적인 비즈니스 로직 서비스(UserService, OrderService) 중에서도 로그인·회원 인증 관련 역할만 따로 분리한 서비스
// 이사람이 진짜 본인인가? 이메일로 사용자 찾기, 비밀번호 맞는지 확인, 로그인 성공하면 토큰 생성 등

public interface AuthService {
    UserDto login(LoginRequest request); // 로그인 요청 들어오면 유저정보 응답데이터 반환
}
