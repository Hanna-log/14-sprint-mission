package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

/*
스프링이 @Controller, @Repository, @Service, @Component 달린 클래스를 찾아서
객체를 만들고 빈 컨테이너에 등록해둔다.
그 객체가 필요한 다른 곳(생성자 등)에 자동으로 넣어주는 걸 "주입"이라고 한다.
*/

// @ConditionalOnProperty : discodeit.repository.type 값이 "file"일 때만 이 클래스를 빈으로 등록
@Repository
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
public class FileUserRepository extends FileRepository<User> implements UserRepository {

    /*
 원래 싱글톤으로 했을 때 객체 1개만 만들고 외부에서 접근하지말라고 필드,생성자를 private로 막아놨는데
 스프링으로 변경시 private로 막아두면 객체 못만들어줘서 등록 못함.public으로 열어서 다시 만들기.

FileUserRepository 생성자는 매개변수 없음(빈 생성자).
대신 부모 클래스 FileRepository의 생성자가 파일 이름(String)을 필요로 하니까,
super("user")로 "user"라는 고정값을 부모한테 넘겨준다.
    */

    // @Value: application.yaml의 설정값을 그대로 생성자 파라미터로 꽂아주는 어노테이션
    // @Value : application.yaml의 discodeit.repository.file-directory 값을 그대로 받아옴.
    // 설정을 안 했으면 ":" 뒤에 있는 ".discodeit"을 기본값으로 사용.

    public FileUserRepository(
        @Value("${discodeit.repository.file-directory:.discodeit}") String fileDirectory) {
        super(fileDirectory, "user");
    }

    @Override // userName으로 유저 한 명 찾기 (중복 검사용)
    public Optional<User> findByUserName(String userName) {
        return findAll().stream()
            .filter(user -> user.getUserName().equals(userName))
            .findFirst();
    }

    @Override // email로 유저 한 명 찾기 (중복 검사용)
    public Optional<User> findByEmail(String email) {
        return findAll().stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst();
    }

    /* 싱글톤 쓰던 시절 옛날 코드 참고용으로 남김
    private static final FileUserRepository instance = new FileUserRepository();
    private FileUserRepository() {
        super("user");
    }
    public static FileUserRepository getInstance() {
        return instance;
    }
    */




}
