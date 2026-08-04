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

        /* === 스프린트 미션 2-1차 요구사항건 (JCF Service 버전) - 참고용으로 남김 ===
        UserService userService = new JCFUserService();
        ChannelService channelService = new JCFChannelService();
        MessageService messageService = new JCFMessageService(userService, channelService);
        */

        // === 스프린트 미션 2-2차 요구사항건 (Basic + Repository + Factory 버전) ===
        String type = "jcf";

        UserRepository userRepository = RepositoryFactory.createUserRepository(type);
        ChannelRepository channelRepository = RepositoryFactory.createChannelRepository(type);
        MessageRepository messageRepository = RepositoryFactory.createMessageRepository(type);

        UserService userService = new BasicUserService(userRepository);
        ChannelService channelService = new BasicChannelService(channelRepository);
        MessageService messageService = new BasicMessageService(messageRepository,userRepository,channelRepository);

        // 스프린트 미션3 -> 요구사항건대로 setupUser,setupChannel 메소드로 변경!
        User user = setupUser(userService);
        Channel channel = setupChannel(channelService);

        // 테스트
    Message message = messageCreateTest(messageService,channel,user);
    updateMessageTest(messageService, message);

    Message deletableMessage = createMessage(messageService, channel,user,"이 메세지는 곧 삭제됩니다.");
   deleteMessageTest(messageService, deletableMessage);

   validationTest(messageService, user, channel);
    }

    private static User setupUser(UserService userService) {
        User user = User.builder().nickName("리키").build();
        userService.save(user);
        System.out.println("=== 유저 등록 ===");
        System.out.println(user);
        return user;
    }

    private static Channel setupChannel(ChannelService channelService) {
        Channel channel = Channel.builder().channelName("자유게시판").build();
        channelService.save(channel);
        System.out.println("\n=== 채널 등록 ===");
        System.out.println(channel);
        return channel;
    }


    // messageCreateTest가 이제 Message를 return 하도록 바뀜 (뒤에서 재사용해야 하니까)
    private static Message messageCreateTest(MessageService messageService, Channel channel, User user) {
        Message message = createMessage(messageService, channel, user,"안녕하세요! 첫 메세지입니다.");
        System.out.println("\n=== 메세지 등록 ===");
        System.out.println(message);
        System.out.println("\n=== 전체 메세지 목록 ===");
        messageService.findAll().forEach(System.out::println);
        return message;
    }

    // 메세지 만드는 부분을 공통 메소드로 뽑음 (재사용 위해)
    private static Message createMessage(
        MessageService messageService,
        Channel channel,User user, String contents) {

        Message message = Message.builder()
            .contents(contents)
            .userId(user.getId())
            .channelId(channel.getId())
            .build();

        messageService.save(message);
        return message;
    }

    private static void updateMessageTest(MessageService messageService, Message message) {
        System.out.println("\n=== 메세지 수정 전 출력 ===");
        System.out.println(message.getContents());
        message.update("[수정 후] 내용을 바꿔봤어요~");
        messageService.update(message);
        System.out.println("\n=== 메세지 수정 후 출력 ===");
        System.out.println(messageService.findIdAsString(message.getId()));
    }

    private static void deleteMessageTest(MessageService messageService,Message message) {
        System.out.println("\n=== 메세지 삭제 전 출력 ===");
        System.out.println(message.getContents());
        messageService.delete(message.getId());
        System.out.println("\n=== 메세지 삭제 후 전체 목록 (" + messageService.findAll().size()+ "건) ===");
        messageService.findAll().forEach(System.out::println);
    }

    private static void validationTest(MessageService messageService,User user,Channel channel){
        System.out.println("\n=== 정상출력 테스트 ===");
        Message trueTest = createMessage(messageService, channel, user, "정상 테스트 결과입니다.");
        System.out.println(messageService.findIdAsString(trueTest.getId()));

        System.out.println("\n=== 실패출력 테스트 ===");
        try {
            Message falseTest = Message.builder()
                .contents("실패 테스트 결과입니다.")
                .userId(UUID.randomUUID())
                .channelId(channel.getId())
                .build();
            messageService.save(falseTest);
        } catch (UserNotFoundException | ChannelNotFoundException exception) {
            System.out.println("실패 테스트 결과입니다." + exception.getMessage());
        }

    }
}

























