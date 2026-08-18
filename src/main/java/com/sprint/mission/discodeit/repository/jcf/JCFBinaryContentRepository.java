package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

/*
@ConditionalOnProperty : 조건 맞을 때마 이 클래스를 스프링 빈으로 등록해달라는 어노테이션
JCF 클래스들 → havingValue = "jcf", matchIfMissing = true (값 없으면 기본으로 jcf)
File 클래스들 → havingValue = "file"
이렇게 하면 application.yaml의 type 값 하나로 자바 코드 수정 없이 어느 쪽이 빈으로 뜰지 결정됨.

JCF 클래스들은 원래 싱글턴으로 객체 생성했는데 스프링 방식으로 변환

 */

@Repository
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf", matchIfMissing = true)
public class JCFBinaryContentRepository extends JCFRepository<BinaryContent>
    implements BinaryContentRepository {

    public JCFBinaryContentRepository() {
        super();
    }

    @Override // id 목록(ids)을 받아서, 그 중에 실제로 저장돼 있는 것들만 찾아서 반환
    public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
        return findAll().stream()
            .filter(binaryContent -> ids.contains(binaryContent.getId()))
            .toList();
    }
}
