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

public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public BasicMessageService(MessageRepository messageRepository, UserRepository userRepository,
        ChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }
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
        userRepository.findById(message.getUserId())
            .orElseThrow(()->new UserNotFoundException(message.getUserId()));
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
