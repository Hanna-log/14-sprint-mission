package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequest;
import java.util.List;
import java.util.UUID;

public interface MessageService {

    MessageDto create(MessageCreateRequest request,
        List<BinaryContentCreateRequest> attachmentRequest);

    List<MessageDto> findAllByChannelId(UUID channelId);
    // 채널별로 메세지 전체 조회 (유저별 메세지 전체 찾기는 요구사항에 없고
    // 채널별 전체 메세지는 자연스러운데 유저별 전체 메세지 조회는 사실상 잘 없기도함)

    MessageDto update(UUID id, MessageUpdateRequest request);

    void delete(UUID id);

    // 여기서 쓰인 UUID id는 각 메세지 객체별로 만들어질때 이 메시지 자체를 가리키는 고유 번호 값을 말함.
}
