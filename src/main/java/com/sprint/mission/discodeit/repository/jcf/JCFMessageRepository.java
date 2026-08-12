package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

public class JCFMessageRepository extends JCFRepository<Message> implements MessageRepository {
    private static final JCFMessageRepository instance = new JCFMessageRepository();

    private JCFMessageRepository() {
        super();
    }
    public static JCFMessageRepository getInstance() {
        return instance;
    }


}
