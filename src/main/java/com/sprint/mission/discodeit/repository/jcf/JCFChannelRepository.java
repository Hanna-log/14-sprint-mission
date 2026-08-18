package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf", matchIfMissing = true)
public class JCFChannelRepository extends JCFRepository<Channel> implements ChannelRepository {

    private JCFChannelRepository() {
        super();
    }

    //  타입(PUBLIC/PRIVATE)별로 채널 목록 찾기
    @Override
    public List<Channel> findAllByType(ChannelType type) {
        return findAll().stream().
            filter(channel -> channel.getType() == type)
            .toList();

    }
    /*
    map 메서드(내용물 값 변환)나 dto 변환은 원본 리스트가 불변이든 가변이든 상관없이 잘 동작함.
    .stream().map(...)은 원본 리스트를 건드는게 아니라 원본은 읽기만 하고 새로운 스트림을 만들어 처리해서
    반환 리스트 타입이 수정가능하든 안되는 상관없음.
     */

}
