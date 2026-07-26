package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

public class FileChannelRepository extends FileRepository<Channel> implements ChannelRepository {

    private static final FileChannelRepository instance = new FileChannelRepository();

    private FileChannelRepository() {
        super("channel");
    }

    public static FileChannelRepository getInstance() {
        return instance;
    }

}
