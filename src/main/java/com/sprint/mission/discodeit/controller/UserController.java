package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequest;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserStatusService userStatusService;

    /* 1. 사용자 등록 --- BinaryContentCreateRequest때문에 오류발생됨.
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> create(
        @Valid @RequestBody UserCreateRequest request,
        BinaryContentCreateRequest contentRequest
    ) {
        log.info("create 정상 작동. 새로 생성될 userEmail:{}", request.email());
        UserDto created = userService.create(request, contentRequest);
        return ResponseEntity.ok(created);
    }

    // 2. 사용자 정보 수정 --- BinaryContentCreateRequest 때문에 오류발생됨.
    @RequestMapping(method = RequestMethod.PATCH, value = "/{userId}")
    public ResponseEntity<UserDto> update(
        @PathVariable UUID userId,
        @Valid @RequestBody UserUpdateRequest request,
        BinaryContentCreateRequest contentRequest) {
        log.info("update 정상 작동. 수정할 userId:{}", userId);

        UserDto updated = userService.update(userId, request, contentRequest);
        return ResponseEntity.ok(updated);
    }
     */

    // [수정] 1. 사용자 등록 (오류 발생 안나게 하려고 BinaryContentCreateRequest 없앤 버전)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> create(
        @Valid @RequestBody UserCreateRequest request
    ) {
        log.info("create 정상 작동. 새로 생성될 userEmail:{}", request.email());
        UserDto created = userService.create(request, null);
        return ResponseEntity.ok(created);
    }



    // [수정] 2. 사용자 정보 수정 (오류 발생 안나게 하려고 BinaryContentCreateRequest 없앤 버전)
    @RequestMapping(method = RequestMethod.PATCH, value = "/{userId}")
    public ResponseEntity<UserDto> update(
        @PathVariable UUID userId,
        @Valid @RequestBody UserUpdateRequest request) {
        log.info("update 정상 작동. 수정할 userId:{}", userId);

        UserDto updated = userService.update(userId, request, null);
        return ResponseEntity.ok(updated);
    }

    // 3. 사용자 정보 삭제
    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    public void delete(@PathVariable UUID userId) {
        log.info("delete 정상 작동. 삭제할 userId:{}", userId);
        userService.delete(userId);
    }

    // 4. 모든 사용자 조회
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> findAll() {
        log.info("findAll 정상 작동.");
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // 5. 사용자 온라인 상태 업데이트
    @RequestMapping(method = RequestMethod.PATCH, value = "/status/{userId}")
    public ResponseEntity<UserStatusDto> userStatusUpdate(
        @PathVariable UUID userId,
        @Valid @RequestBody UserStatusUpdateRequest request) {
        log.info("userStatusUpdate 정상 작동.");

        UserStatusDto userStatus = userStatusService.updateByUserId(userId, request);
        return ResponseEntity.ok(userStatus);

    }


}
