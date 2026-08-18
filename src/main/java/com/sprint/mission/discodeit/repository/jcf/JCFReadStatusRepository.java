package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;


@Repository
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf", matchIfMissing = true)
public class JCFReadStatusRepository extends JCFRepository<ReadStatus>
implements ReadStatusRepository {

    public JCFReadStatusRepository() {
        super();
    }

    /*
    static이 없으면 getInstance()는 인스턴스 메서드가 됨.
    인스턴스 메서드란? 그 메서드가 속하는 클래스의 객체가 있어야지만 접근 가능.

    클래스 이름 점찍고 접근하려면 static이어야함. static이 아니면 객체를 만들고 접근 가능.

     */


    // 특정 유저의 읽음 상태 전체 찾기 (유저가 속한 채널마다 하나씩 있음)
    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        return findAll().stream().filter(readStatus ->
                readStatus.getUserId().equals(userId))
            .toList();
    }

    // 특정 채널의 읽음 상태 전체 찾기 (채널에 속한 유저마다 하나씩 있음)
    @Override
    public List<ReadStatus> findAllByChannelId(UUID channelId) {
        return findAll().stream()
            .filter(readStatus -> readStatus.getChannelId().equals(channelId))
            .toList();
    }
}
