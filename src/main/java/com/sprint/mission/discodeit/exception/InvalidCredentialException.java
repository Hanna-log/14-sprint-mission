package com.sprint.mission.discodeit.exception;

/*
 "잘못된 인증 정보 예외"
Invalid + Credential + Exception
    Invalid = 유효하지 않은, 잘못된
    Credential = 인증 정보 (아이디, 비밀번호, 토큰 등)
    Exception = 예외

"로그인할 때 입력한 인증 정보가 올바르지 않다" 라는 상황에서 던지는 예외
*/

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException() {
        super("Username 또는 password가 일치하지 않습니다.");
    }

}
