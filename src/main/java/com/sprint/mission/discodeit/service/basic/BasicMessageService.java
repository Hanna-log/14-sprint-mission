package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.exception.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.MessageNotFoundException;
import com.sprint.mission.discodeit.exception.UserNotFoundException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
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
public class BasicMessageService implements MessageService {

    MessageRepository messageRepository;
    UserRepository userRepository;
    ChannelRepository channelRepository;

/*
    private void testMethod(Message message) {
        if(userRepository.findById(message.getUserId()).isEmpty()) {
            throw new IllegalArgumentException("해당 유저ID는 확인되지않습니다. 유저ID(" + message.getUserId()+")" );
        }
        if(channelRepository.findById(message.getChannelId()).isEmpty()) {
            throw new IllegalArgumentException("해당 채널ID는 확인되지않습니다. 채널ID(" + message.getChannelId() + ")");
        }
    }

 */
    private void testMethod(Message message) {
        userRepository.findById(message.getAuthorId())
            .orElseThrow(()->new UserNotFoundException(message.getAuthorId()));
        channelRepository.findById(message.getChannelId())
            .orElseThrow(()->new ChannelNotFoundException(message.getChannelId()));
    }

    @Override
    public Message save(Message message) {
        testMethod(message);
        return messageRepository.save(message);
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message update(Message message) {
        testMethod(message);
        return messageRepository.update(message);
    }

    @Override
    public void delete(UUID id) {
        messageRepository.findById(id)
                .orElseThrow(()-> new MessageNotFoundException(id));
        messageRepository.delete(id);
    }
}
