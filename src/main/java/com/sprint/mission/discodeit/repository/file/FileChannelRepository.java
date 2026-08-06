package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.stereotype.Repository;

/*
스프링이 @Controller, @Repository, @Service, @Component 달린 클래스를 찾아서
객체를 만들고 빈 컨테이너에 등록해둔다.
그 객체가 필요한 다른 곳(생성자 등)에 자동으로 넣어주는 걸 "주입"이라고 한다.
*/
@Repository
public class FileChannelRepository extends FileRepository<Channel> implements ChannelRepository {

      /*
    원래 싱글톤으로 했을 때 객체 1개만 만들고 외부에서 접근하지말라고 필드,생성자를 private로 막아놨는데
    스프링으로 변경시 private로 막아두면 객체 못만들어줘서 등록 못함. public으로 열어서 다시 만들기.

    FileUserRepository 생성자는 매개변수 없음(빈 생성자).
    대신 부모 클래스 FileRepository의 생성자가 파일 이름(String)을 필요로 하니까,
    super("channel")로 "channel"라는 고정값을 부모한테 넘겨준다.
    */

    public FileChannelRepository() {
        super("channel");
    }

    /*
    private static final FileChannelRepository instance = new FileChannelRepository();

    private FileChannelRepository() {
        super("channel");
    }

    public static FileChannelRepository getInstance() {
        return instance;
    }
    */

}
