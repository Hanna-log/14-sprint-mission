package com.sprint.mission.discodeit.factory;

import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;

public class RepositoryFactory {

    public static UserRepository createUserRepository(String type) {
        if (type.equals("file")) {
          /*
          return FileUserRepository.getInstance();
          FileUserRepository에 @Repository 스프링 어노테이션 붙이면서 스프링용으로 변경!
          원래 싱글톤은 생성자가 private였는데 이러면 스프링이 생성자를 직접 호출 못하니까
          싱글톤 관련 private 생성자 + static instance 필드 + getInstance() 메소드 통째로
          지워버림. 그래서 getInstance() 메서드 활용 불가해서 컴파일 에러나니까 직접 객체 생성하는 코드로 변경.

          하단 new FileUserRepository()으로 만들어진 객체는 @Repository로 만들어진 (스프링이 관리하는)
          객체랑 다름!
          */
            return new FileUserRepository();
        }
        return JCFUserRepository.getInstance();
    }

    /*
    JCFUserRepository는 @Repository를 안 붙이는 이유?
UserRepository 인터페이스 구현체가 JCFUserRepository, FileUserRepository 두 개인데
둘 다 @Repository를 붙이면 스프링이 뭘 써야 할지 몰라서 에러남.
구분을 위해 일단 FileUserRepository에만 붙임.

이렇게 같은 인터페이스 구현체가 여러 개일 때는 @Primary, @Qualifier로 뭘 쓸지 골라줄 수 있음.
- @Primary: 여러 후보 중 "이게 기본값이야"라고 클래스 위에 붙여두는 것
- @Qualifier("이름"): 스프링 빈은 기본적으로 클래스명 그대로(소문자 시작)로 저장되는데,
  주입받는 쪽(생성자 등)에서 "이 이름 가진 빈으로 넣어줘"라고 콕 찍어서 지정하는 것

  자동이름은 내가 원하는걸로 바꿀수있음 예시) @Repository("myCustomName")
  @Qualifier로 부를때도
  public BasicUserService(@Qualifier("myCustomName") UserRepository userRepository)
     */

    public static ChannelRepository createChannelRepository(String type) {
        if (type.equals("file")) {
           // return FileChannelRepository.getInstance(); ---> 위 이유와 동일
            return new FileChannelRepository();
        }
        return JCFChannelRepository.getInstance();
    }

    public static MessageRepository createMessageRepository(String type) {
        if (type.equals("file")) {
            // return FileMessageRepository.getInstance(); ---> 위 이유와 동일
            return new FileMessageRepository();
        }
    return JCFMessageRepository.getInstance();
    }




}
