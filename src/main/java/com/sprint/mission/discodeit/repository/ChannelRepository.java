package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import java.util.List;

public interface ChannelRepository extends Repository<Channel> {
    List<Channel> findAllByType(ChannelType type);

}
