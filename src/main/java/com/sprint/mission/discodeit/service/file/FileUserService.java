package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

public class FileUserService extends FileService<User> implements UserService {

    public FileUserService() {
        super("user");
    }
}
