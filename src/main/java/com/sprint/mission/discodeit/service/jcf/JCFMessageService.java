package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

public class JCFMessageService extends JCFService<Message> implements MessageService {
    private final UserService userService;
    private final ChannelService channelService;


    public JCFMessageService(UserService userService, ChannelService channelService) {
        super();
        this.userService = userService;
        this.channelService = channelService;
    }

    private void testMethod (Message message) {
        if(userService.findById(message.getAuthorId()).isEmpty()) {
            throw new IllegalArgumentException("해당 유저ID는 확인되지않습니다. 유저ID(" + message.getAuthorId() + ")");
        }
        if(channelService.findById(message.getChannelId()).isEmpty()) {
            throw new IllegalArgumentException("해당 유저ID는 확인되지않습니다. 유저ID(" + message.getChannelId() + ")");
        }
    }

    @Override
    public Message save(Message message) {
        testMethod(message);
        return super.save(message);
    }

    @Override
    public Message update(Message message) {
        testMethod(message);
        return super.update(message);
    }
}