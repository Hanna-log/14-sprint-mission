package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.util.Optional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

// @ConditionalOnProperty : application.yaml의 discodeit.repository.type 값을 확인해서
// 조건에 맞을 때만 이 클래스를 빈으로 등록해줌.
// - havingValue = "jcf" -> 값이 "jcf"일 때 등록
// - matchIfMissing = true -> 값 자체가 없으면(설정 안 했으면) 그래도 등록(기본값 취급)

@Repository
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf", matchIfMissing = true)
public class JCFUserRepository extends JCFRepository<User> implements UserRepository {

    private JCFUserRepository() {
       super();
    };

    // userName으로 유저 한 명 찾기 (중복 검사용)
    @Override
    public Optional<User> findByUserName(String userName) {
        return findAll().stream().filter(user -> user.getUserName().equals(userName))
            .findFirst();
    }

    /*
.findFirst()란? : 조건에 맞는 결과값중 첫번째 하나 반환.
값이 있으면 Optional<User>이렇게 주고 없으면 Optional.empity로 나옴
     */

// email로 유저 한 명 찾기 (중복 검사용)
    @Override
    public Optional<User> findByEmail(String email) {
        return findAll().stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst();
    }
}
/*
findAll()은 JCFRepository의 메서드 (부모클래스)
상속받아서 바로 쓸수있음.

여기서 this 자기자신 객체인데 생략되어서 표현됨
this.findAll()가 원래 맞는 표현.
자기 자신의 기능을 쓸때는 객체 변수 이름 앞에 붙일 필요가 없음. (부모클래스에서 상속받은 메서드도 포함)
 */