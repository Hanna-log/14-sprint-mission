package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.exception.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.UserNotFoundException;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

public class FileMessageService extends FileService<Message> implements MessageService {

    private final UserService userService;
    private final ChannelService channelService;


    public FileMessageService(UserService userService, ChannelService channelService) {
        super("message");
        this.userService = userService;
        this.channelService = channelService;
    }

    private void testMethod(Message message) {
        userService.findById(message.getAuthorId())
            .orElseThrow(()->new UserNotFoundException(message.getAuthorId()));
        channelService.findById(message.getChannelId())
            .orElseThrow(()->new ChannelNotFoundException(message.getChannelId()));
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
