package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.exception.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.MessageNotFoundException;
import com.sprint.mission.discodeit.exception.UserNotFoundException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.ArrayList;
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
public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository; // 이 메세지가 해당 유저가 작성한게 맞는지 검증 필요해서 끌어옴
    private final ChannelRepository channelRepository; // 이 메세지가 해당 채널에서 작성한게 맞는지 검증 필요해서 끌어옴

    private final BinaryContentRepository binaryContentRepository; // 이 메세지에 붙은 첨부파일 확인하려고 끌어옴


    @Override // 메세지 생성 create 메소드 만들기
    public MessageDto create(MessageCreateRequest request,
        List<BinaryContentCreateRequest> attachmentRequests) {

        // 1. 작성자(userId), 채널이 실제로 존재하는지 확인하기
        userRepository.findById(request.authorId())
            .orElseThrow(() -> new UserNotFoundException(request.authorId()));
        /*
        .orElseThrow(() -> new UserNotFoundException(id)); 값이 없으면 이 예외를 만들어서 던져
         orElseThrow()는 나중에 필요한 예외를 만들어주는 함수기 때문에
        .orElseThrow(new UserNotFoundException(...)) 이건 안되고
        () -> new UserNotFoundException(...) 꼭 앞에 괄호 붙여줘야함.
        () -> 는 지금 만들지 말고 필요할때 만들라는 소리임.
         */
        channelRepository.findById(request.channelId())
            .orElseThrow(() -> new ChannelNotFoundException(request.channelId()));

        // 2. 첨부파일이 같이 왔으면 하나씩 BinaryContent로 저장하고, id만 모아두기
        List<UUID> attachmentIds = new ArrayList<>();
        if (attachmentRequests != null) {
            for (BinaryContentCreateRequest attachmentRequest : attachmentRequests) {

                /*
                for문은 저장하기 전의 리스트 요소를 꺼내는 단계라서 BinaryContentCreateRequest가 맞음.
                attachmentRequests에서 하나씩 꺼내면 attachmentRequest가 여러개
                그 개별 객체 타입이 BinaryContentCreateRequest임.
                for (타입 변수 : 리스트)에서 타입은 리스트 안에 들어있는 요소 타입 말함.
                List<String> names면 for (String name : names)가 되고
                List<User> users면 for (User user : users)가 됨
                BinaryContentCreateRequest -> BinaryContent로 타입변환함.
                */

                BinaryContent binaryContent = BinaryContent.builder()
                    .fileName(attachmentRequest.fileName())
                    .contentType(attachmentRequest.contentType())
                    .bytes(attachmentRequest.bytes())
                    .build();
                binaryContentRepository.save(binaryContent); // 첨부파일 객체 만들고 첨부파일 저장소에 저장
                attachmentIds.add(binaryContent.getId()); // 첨부파일 아이디만 따로 모아놓기 for문 반복문 - 한개씩 저장
            }
        }

        // 3. Message 생성
        Message message = Message.builder()
            .contents(request.contents())
            .authorId(request.authorId())
            .channelId(request.channelId())
            .attachmentIds(attachmentIds)
            .build();
        messageRepository.save(message);
        return toDto(message);
    }


    @Override // 채널 아이디 입력해서 그 채널에 있는 메세지 다 뽑기
    public List<MessageDto> findAllByChannelId(UUID channelId) {
        List<Message> messages
            = messageRepository.findAllByChannelId(channelId);

        List<MessageDto> result = new ArrayList<>();
        for (Message message : messages) {
            result.add(toDto(message));
        }
        return result;
        // 채널 아이디로 뽑은 messages를 향상된
        // for문으로 MessageDto타입 변경해서 result에 담고 반환!
    }

    @Override
    public MessageDto update(UUID id, MessageUpdateRequest request) {
        Message message = messageRepository.findById(id)
            .orElseThrow(() -> new MessageNotFoundException(id));

        message.update(request.contents());
        messageRepository.update(message);

        return toDto(message);
    }

    @Override
    public void delete(UUID id) {
        // 메세지 저장소에 해당 메세지 아이디를 넣어서 조회했을 때 없으면 그 메세지 없다고 오류던짐.
        Message message = messageRepository.findById(id)
            .orElseThrow(()-> new MessageNotFoundException(id));

        // 첨부파일(BinaryContent)도 같이 삭제
        for (UUID attachmentId : message.getAttachmentIds()) {
            binaryContentRepository.delete(attachmentId);
        }
        messageRepository.delete(id);
    }

    /*
     ======== MessageDto 공통적으로 만드는 메소드 별도 추출 ========

     ⭐️ 공통 추출 메소드 만들때 static이 붙는 기준은? ⭐️
        "이 메소드가 자기 자신(클래스)의 필드를 쓰는가, 안 쓰는가"
        : 안 쓴다 → static 가능 (권장)
        : 쓴다 → static 불가능

        MessageDto는 파라미터로 message만 씀
        messageRepository나 다른 필드는 전혀 안쓰므로 → static 가능 ✅

        ‼️ 앞으로 toDto류 메소드를 만들 때마다,
        **"이 메소드 안에서 this.무언가(필드)를 쓰고 있나?"**만 확인하면 됨! ‼️

        - 파라미터로 받은 값만 조립해서 DTO 만들면 → static
        - 저장소(~Repository)를 추가로 호출해서 뭔가 더 가져와야 하면 → static 불가

         */
    private static MessageDto toDto(Message message) {
        return new MessageDto(
            message.getId(),
            message.getContents(),
            message.getChannelId(),
            message.getAuthorId(),
            message.getAttachmentIds(),
            message.getCreatedAt()
        );
    }
}