package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.service.ChannelService;
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
@RequestMapping("/api/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    // 1. 공개 채널 생성
    @RequestMapping(method = RequestMethod.POST, value = "/public")
    public ResponseEntity<ChannelDto> publicChannelCreate(
        @Valid @RequestBody PublicChannelCreateRequest request
    ) {
        log.info("publicChannelCreate 정상 작동. 채널 이름:{}", request.name());
        ChannelDto created = channelService.createPublic(request);
        return ResponseEntity.ok(created);
    }

    // 2. 비밀 채널 생성
    @RequestMapping(method = RequestMethod.POST, value = "/private")
    public ResponseEntity<ChannelDto> privateChannelCreate(
        @Valid @RequestBody PrivateChannelCreateRequest request
    ) {
        log.info("privateChannelCreate 정상 작동.");
        ChannelDto created = channelService.createPrivate(request);
        return ResponseEntity.ok(created);
    }

    // 3. 공개채널 정보 수정하기
    @RequestMapping(method = RequestMethod.PATCH, value = "/{channelId}")
    public ResponseEntity<ChannelDto> update(
        @PathVariable UUID channelId,
        @Valid @RequestBody ChannelUpdateRequest request
    ) {
        log.info("공개채널 수정 정상 작동.");
        ChannelDto created = channelService.update(channelId, request);
        return ResponseEntity.ok(created);
    }

    // 4. 채널을 삭제하기
    @RequestMapping(method = RequestMethod.DELETE, value = "/{channelId}")
    public void delete(@PathVariable UUID channelId) {
        log.info("delete 정상 작동. 삭제할 channelId:{}", channelId);
        channelService.delete(channelId);
    }

    // 5. 특정 사용자가 볼 수있는 모든 채널 목록 조회
    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    public ResponseEntity<List<ChannelDto>> findAllByUserId(@PathVariable UUID userId) {
        log.info("findAllByUserId 정상 작동. 조회할 유저 아이디:{}", userId);

        List<ChannelDto> channelDtoList = channelService.findAllByUserId(userId);
        return ResponseEntity.ok(channelDtoList);

    }

}
