package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.user.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequest;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
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
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /* 1. 메세지를 보낼수있음 --- List<BinaryContentCreateRequest>때문에 오류발생됨.
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MessageDto> create(
        @Valid @RequestBody MessageCreateRequest request,
        List<BinaryContentCreateRequest> contentRequest
    ) {
        log.info("Message create 적상 작동");
        MessageDto created = messageService.create(request, contentRequest);
        return ResponseEntity.ok(created);
    }
*/

    // [수정] 1. 메세지를 보낼수있음 (오류 발생 안나게 하려고 List<BinaryContentCreateRequest> 없앤 버전)
    // List<BinaryContentCreateRequest> 자리에 List.of()로 채우기(빈리스트) --- [] 안에 값이 없다지 null값 아님.
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MessageDto> create(
        @Valid @RequestBody MessageCreateRequest request
    ) {
        log.info("Message create 정상 작동");
        MessageDto created = messageService.create(request, List.of());
        return ResponseEntity.ok(created);
    }


    // 2. 메세지 수정
    @RequestMapping(method = RequestMethod.PATCH, value = "/{messageId}")
    public ResponseEntity<MessageDto> update(
        @PathVariable UUID messageId,
        @Valid @RequestBody MessageUpdateRequest request) {

        log.info("update 정상 작동. 수정할 메세지id:{}", messageId);
        MessageDto updated = messageService.update(messageId, request);
        return ResponseEntity.ok(updated);

    }

    // 3. 메세지 삭제
    @RequestMapping(method = RequestMethod.DELETE, value = "/{messageId}")
    public void delete(@PathVariable UUID messageId) {
        log.info("delete 정상 작동. 삭제할 messageId:{}", messageId);
        messageService.delete(messageId);
    }

    // 4. 특정 채널 메세지 목록 조회
    @RequestMapping(method = RequestMethod.GET, value = "/{channelId}")
    public ResponseEntity<List<MessageDto>> findAllByChannelId(@PathVariable UUID channelId) {
        log.info("findAllByChannelId 정상 작동. 조회할 channelId:{}", channelId);
        List<MessageDto> messages
            = messageService.findAllByChannelId(channelId);
        return ResponseEntity.ok(messages);
    }

}
