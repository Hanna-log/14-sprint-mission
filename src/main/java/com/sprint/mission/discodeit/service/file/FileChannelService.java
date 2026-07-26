package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

public class FileChannelService extends FileService<Channel> implements ChannelService {

    public FileChannelService() {
        super("channel");
    }
}
