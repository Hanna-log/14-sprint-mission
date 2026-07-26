package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.UserNotFoundException;
import com.sprint.mission.discodeit.factory.RepositoryFactory;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import java.util.List;
import java.util.UUID;

public class JavaApplication {

    public static void main(String[] args) {

        // 0. 의존성 주입(객체 생성)

        /* === 1차 요구사항건 (JCF Service 버전) - 참고용으로 남김 ===
        UserService userService = new JCFUserService();
        ChannelService channelService = new JCFChannelService();
        MessageService messageService = new JCFMessageService(userService, channelService);
        */

        // === 2차 요구사항건 (Basic + Repository + Factory 버전) ===
        String type = "jcf"; // ---> type을 "file"로 입력할 경우 File 버전으로 교체됨.

        UserRepository userRepository = RepositoryFactory.createUserRepository(type);
        ChannelRepository channelRepository = RepositoryFactory.createChannelRepository(type);
        MessageRepository messageRepository = RepositoryFactory.createMessageRepository(type);

        UserService userService = new BasicUserService(userRepository);
        ChannelService channelService = new BasicChannelService(channelRepository);
        MessageService messageService = new BasicMessageService(messageRepository, userRepository, channelRepository);


        // 1-1. 유저 등록
        User user1 = User.builder().nickName("리키").build();
        User user2 = User.builder().nickName("태현").build();

        userService.save(user1);
        userService.save(user2);

        // 1-2. 채널 등록
        Channel ch1 = Channel.builder().channelName("자유게시판").build();
        Channel ch2 = Channel.builder().channelName("건의게시판").build();

        channelService.save(ch1);
        channelService.save(ch2);

        // 1-3. 메세지 등록
        Message msg1 = Message.builder().contents("1. 리키입니다~!").userId(user1.getId())
            .channelId(ch1.getId()).build();
        Message msg2 = Message.builder().contents("2. 태현이라고 합니다.").userId(user2.getId())
            .channelId(ch1.getId()).build();
        Message msg3 = Message.builder().contents("3. [수정 전] 공지사항 바꿔주세요!").userId(user1.getId())
            .channelId(ch2.getId()).build();
        Message msg4 = Message.builder().contents("4. [삭제 전] 네, 알겠습니다!").userId(user2.getId())
            .channelId(ch2.getId()).build();

        messageService.save(msg1);
        messageService.save(msg2);
        messageService.save(msg3);
        messageService.save(msg4);

        // 1-4. 등록된 유저, 채널, 메세지 총 출력
        System.out.println("\n === 등록 유저 전체 목록 (" + userService.findAll().size() + "건) ===");
        userService.findAll().forEach(System.out::println);

        System.out.println("\n === 등록 채널 전체 목록 (" + channelService.findAll().size() + "건) ===");
        channelService.findAll().forEach(System.out::println);

        System.out.println("\n === 등록 메세지 전체 목록 (" + messageService.findAll().size() + "건) ===");
        messageService.findAll().forEach(System.out::println);

        // 2-1. 특정 유저의 메세지만 출력
        List<Message> userMessage = messageService.findAll().stream()
            .filter(m->m.getUserId().equals(user1.getId()))
            .toList();

        System.out.printf("\n=== 유저 '%s'의 메세지만 출력 (%s건) ===\n", user1.getNickName(),userMessage.size());
      userMessage.forEach(System.out::println);

        // 2-2. 특정 채널의 메세지만 출력
        List<Message> channelMessage = messageService.findAll().stream()
                .filter(m->m.getChannelId().equals(ch1.getId()))
                    .toList();

        System.out.printf("\n=== 채널 '%s'의 메세지만 출력 (%s건) ===\n", ch1.getChannelName(), channelMessage.size());
        channelMessage.forEach(System.out::println);

        // 3. 메세지 수정 및 확인
        System.out.println("\n=== 메세지 수정 전 출력 ===");
        System.out.println(msg3.getContents());

        System.out.println("\n=== 메세지 수정 후 출력 ===");
        msg3.update("3. [수정 후] 건의게시판에 올릴게요~");
        messageService.update(msg3);
        // messageService.findById(msg3.getId()).map(Message::toString).ifPresent(System.out::println);
        System.out.println(messageService.findIdAsString(msg3.getId()));


        // 4. 메세지 삭제 및 확인
        System.out.println("\n=== 메세지 삭제 전 출력 ===");
        System.out.println(msg4.getContents());
        messageService.delete(msg4.getId());

        System.out.println("\n=== 메세지 삭제 후 출력 (" + messageService.findAll().size() + "건) ===");
        messageService.findAll().forEach(System.out::println);

        // 5. 유효성 검사 테스트 (정상, 실패)
        System.out.println("\n=== 정상출력 테스트 ===");
        Message trueTest = Message.builder().contents("정상 테스트 결과입니다.").userId(user1.getId())
            .channelId(ch1.getId()).build();
        messageService.save(trueTest);
        //messageService.findById(trueTest.getId()).map(Message::toString).ifPresent(System.out::println);
        System.out.println(messageService.findIdAsString(trueTest.getId()));

        System.out.println("\n=== 실패출력 테스트 ===");
        try {
            Message falseTest = Message.builder().contents("실패 테스트 결과입니다.")
                .userId(UUID.randomUUID()).channelId(ch1.getId()).build();
            messageService.save(falseTest);
            messageService.findById(falseTest.getId());
        } catch (UserNotFoundException | ChannelNotFoundException exception) {
            System.out.println("실패 테스트 결과입니다. " + exception.getMessage());
        }


    }
}

