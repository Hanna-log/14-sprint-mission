package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

public class FileUserRepository extends FileRepository<User> implements UserRepository {

    private static final FileUserRepository instance = new FileUserRepository();

    private FileUserRepository() {
        super("user");
    }

    public static FileUserRepository getInstance() {
        return instance;
    }
}
