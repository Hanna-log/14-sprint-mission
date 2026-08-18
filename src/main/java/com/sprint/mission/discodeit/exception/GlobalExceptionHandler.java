package com.sprint.mission.discodeit.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> AllExceptionhandler(
        Exception exception
    ) {
        return ResponseEntity.internalServerError().body("서버 오류");
    }


    @ExceptionHandler(BinaryContentNotFoundException.class)
    public ResponseEntity<String> BinaryContentNotFoundhandler(
        BinaryContentNotFoundException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }


    @ExceptionHandler(ChannelNotFoundException.class)
    public ResponseEntity<String> ChannelNotFoundhandler(
        ChannelNotFoundException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> DuplicateEmailhandler(
        DuplicateEmailException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }


    @ExceptionHandler(DuplicateReadStatusException.class)
    public ResponseEntity<String> DuplicateReadStatushandler(
        DuplicateReadStatusException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateUserNameException.class)
    public ResponseEntity<String> DuplicateUserNamehandler(
        DuplicateUserNameException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateUserStatusException.class)
    public ResponseEntity<String> DuplicateUserStatushandler(
        DuplicateUserStatusException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(InvalidChannelNameException.class)
    public ResponseEntity<String> InvalidChannelNamehandler(
        InvalidChannelNameException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<String> InvalidCredentialhandler(
        InvalidCredentialException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(InvalidMessageContentException.class)
    public ResponseEntity<String> InvalidMessageContenthandler(
        InvalidMessageContentException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(InvalidNicknameException.class)
    public ResponseEntity<String> InvalidNicknamehandler(
        InvalidNicknameException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<String> MessageNotFoundhandler(
        MessageNotFoundException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(PrivateChannelUpdateException.class)
    public ResponseEntity<String> PrivateChannelUpdatehandler(
        PrivateChannelUpdateException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(ReadStatusNotFoundException.class)
    public ResponseEntity<String> ReadStatusNotFoundhandler(
        ReadStatusNotFoundException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> UserNotFoundhandler(
        UserNotFoundException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(UserStatusNotFoundException.class)
    public ResponseEntity<String> UserStatusNotFoundhandler(
        UserStatusNotFoundException exception
    ) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }


}
