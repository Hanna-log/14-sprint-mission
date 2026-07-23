package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

public class JCFChannelRepository extends JCFRepository<Channel> implements ChannelRepository {
    private static final JCFChannelRepository instance = new JCFChannelRepository();

    private JCFChannelRepository() {
        super();
    }

    public static JCFChannelRepository getInstance() {
        return instance;
    }


}
