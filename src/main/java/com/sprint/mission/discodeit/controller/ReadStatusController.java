package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.ReadStatusService;
import com.sprint.mission.discodeit.service.UserService;
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
@RequestMapping("/api/readstatus")
@RequiredArgsConstructor
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    // 1. 특정 '채널'의 메세지 수신 정보를 '생성'
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReadStatusDto> channelReadStatusCreate(
        @Valid @RequestBody ReadStatusCreateRequest request) {
        log.info("channelReadStatusCreate 정상 작동. Id:{}", request.channelId());

        ReadStatusDto created = readStatusService.create(request);

        return ResponseEntity.ok(created);
    }


    // 2. 특정 '채널'의 메시지 수신 정보를 '수정'
    @RequestMapping(method = RequestMethod.PATCH, value = "/{readStatusId}")
    public ResponseEntity<ReadStatusDto> channelReadStatusUpdate(
        @PathVariable UUID readStatusId,
        @RequestBody ReadStatusUpdateRequest request) {

        log.info("channelReadStatusUpdate 정상 작동.");
        ReadStatusDto updated = readStatusService.update(readStatusId, request);

        return ResponseEntity.ok(updated);
    }

    // 3. 특정 '사용자'의 메시지 수신 정보를 '조회'
    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    public ResponseEntity<List<ReadStatusDto>> userReadStatusfindAll(
        @PathVariable UUID userId) {
        log.info("userReadStatusfindAll 정상 작동.Id:{}", userId);

        List<ReadStatusDto> findUserReadStatus = readStatusService.findAllByUserId(userId);

        return ResponseEntity.ok(findUserReadStatus);
    }
}
