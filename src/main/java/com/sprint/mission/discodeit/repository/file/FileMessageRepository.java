package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

public class FileMessageRepository extends FileRepository<Message> implements MessageRepository {

    private static final FileMessageRepository instance = new FileMessageRepository();

    private FileMessageRepository() {
        super("message");
    }

    public static FileMessageRepository getInstance() {
        return instance;
    }

}
