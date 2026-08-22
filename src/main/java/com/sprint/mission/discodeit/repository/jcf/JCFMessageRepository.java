package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf", matchIfMissing = true)
public class JCFMessageRepository extends JCFRepository<Message> implements MessageRepository {

    private JCFMessageRepository() {
        super();
    }

    @Override  // 채널 하나에 속한 메시지 전체 찾기
    public List<Message> findAllByChannelId(UUID channelId) {
        return findAll().stream()
            .filter(message -> message.getChannelId().equals(channelId))
            .toList();
    }
}
