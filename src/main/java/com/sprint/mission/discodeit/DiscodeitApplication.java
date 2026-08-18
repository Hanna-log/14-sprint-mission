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

			new BasicUserService(userRepository) 하던 걸
			Spring 컨테이너에서 이미 만들어둔 Bean을 꺼내오는 방식으로 대체
	 */

@SpringBootApplication
public class DiscodeitApplication {
	public static void main(String[] args) {
		SpringApplication.run(DiscodeitApplication.class, args);

	}
}
