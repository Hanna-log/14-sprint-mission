package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.exception.ChannelNotFoundException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

/*
스프링이 @Controller, @Repository, @Service, @Component 달린 클래스를 찾아서
객체를 만들고 빈 컨테이너에 등록해둔다.
그 객체가 필요한 다른 곳(생성자 등)에 자동으로 넣어주는 걸 "주입"이라고 한다.
*/

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Service
public class BasicChannelService implements ChannelService {
   ChannelRepository channelRepository;


    @Override
    public Channel save(Channel channel) {
        return channelRepository.save(channel);
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        return channelRepository.findById(id);
    }

    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public Channel update(Channel channel) {
        return channelRepository.update(channel);
    }

    @Override
    public void delete(UUID id) {
        channelRepository.findById(id)
                .orElseThrow(()->new ChannelNotFoundException(id));
        channelRepository.delete(id);
    }
}

