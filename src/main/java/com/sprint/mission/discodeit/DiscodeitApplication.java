package com.sprint.mission.discodeit;


import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.UserNotFoundException;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/*
			스프링부트 프로젝트를 처음에 만들면 기본으로 @SpringBootApplication +
		   SpringApplication.run(DiscodeitApplication.class, args);이렇게 생성되는데
		   이게 있어야 Spring 컨테이너가 켜짐. JavaApplication은 이 두 개(@SpringBootApplication,
			SpringApplication.run)가 없어서 그냥 순수 자바 프로그램이고,
			DiscodeitApplication은 이게 있어서 Spring이 관리하는 프로그램이 되는 것.
			(클래스 이름이 달라서 차이나는 게 아니라, 이 코드 유무가 진짜 차이)

		   기본 SpringApplication.run(...) : 서버를 켜기만 하고 그 결과(컨테이너) 안씀.
		   main() 메소드 안에서 userService.save(...) 이런 코드를 실행 하고싶다면
		   Spring이 만들어놓은 Bean 저장소(컨테이너)에 직접 접근해서 Bean을 하나 꺼내기 위해
		   SpringApplication.run(...)은 실행되고 나면 그 컨테이너 객체를 리턴해줌.
			이 리턴값을 안 받고 버리면 컨테이너에 접근할 방법이 없으니까,
			ConfigurableApplicationContext context = 로 받아서 변수에 담아두는 것.

		   ConfigurableApplicationContext 타입이란? Bean들이 보관돼있는 저장소(컨테이너)"의 타입을
		   말함.우리 프로젝트의 모든 Bean(FileUserRepository, BasicUserService 등)이 다 들어있음.
		   .getBean(UserService.class)를 호출해서 이 객체가 갖고있는 기능(메소드) 활용 가능!

	 */

@SpringBootApplication
public class DiscodeitApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context =
		SpringApplication.run(DiscodeitApplication.class, args);

		// new BasicUserService(userRepository) 하던 걸
		// Spring 컨테이너에서 이미 만들어둔 Bean을 꺼내오는 방식으로 대체

		UserService userService = context.getBean(UserService.class);
		ChannelService channelService = context.getBean(ChannelService.class);
		MessageService messageService = context.getBean(MessageService.class);

		// 유저, 채널 셋업
		User user = setupUser(userService);
		Channel channel = setupChannel(channelService);

		// 테스트
		messageCreateTest(messageService,channel,user);
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

	// 메세지 만드는 부분을 공통 메소드로 뽑음 (재사용 위해)
	private static Message creatMessage(
		MessageService messageService,
		Channel channel,User user, String contents) {

		Message message = Message.builder()
			.contents(contents)
			.authorId(user.getId())
			.channelId(channel.getId())
			.build();

		messageService.save(message);
		return message;
	}


	// messageCreateTest가 이제 Message를 return 하도록 바뀜 (뒤에서 재사용해야 하니까)
	private static Message messageCreateTest(MessageService messageService, Channel channel, User user) {
		Message message = creatMessage(messageService, channel, user,"안녕하세요! 첫 메세지입니다.");
		System.out.println("\n=== 메세지 등록 ===");
		System.out.println(message);
		System.out.println("\n=== 전체 메세지 목록 ===");
		messageService.findAll().forEach(System.out::println);
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
		Message trueTest = creatMessage(messageService, channel, user, "정상 테스트 결과입니다.");
		System.out.println(messageService.findIdAsString(trueTest.getId()));

		System.out.println("\n=== 실패출력 테스트 ===");
		try {
			Message falseTest = Message.builder()
				.contents("실패 테스트 결과입니다.")
				.authorId(UUID.randomUUID())
				.channelId(channel.getId())
				.build();
			messageService.save(falseTest);
		} catch (UserNotFoundException | ChannelNotFoundException exception) {
			System.out.println("실패 테스트 결과입니다." + exception.getMessage());
		}

	}

}
