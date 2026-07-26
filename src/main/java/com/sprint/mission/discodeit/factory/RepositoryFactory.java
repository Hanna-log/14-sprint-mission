package com.sprint.mission.discodeit.factory;

import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;

public class RepositoryFactory {

    public static UserRepository createUserRepository(String type) {
        if (type.equals("file")) {
            return FileUserRepository.getInstance();
        }
        return JCFUserRepository.getInstance();
    }

    public static ChannelRepository createChannelRepository(String type) {
        if (type.equals("file")) {
            return FileChannelRepository.getInstance();
        }
        return JCFChannelRepository.getInstance();
    }

    public static MessageRepository createMessageRepository(String type) {
        if (type.equals("file")) {
            return FileMessageRepository.getInstance();
        }
    return JCFMessageRepository.getInstance();
    }




}
