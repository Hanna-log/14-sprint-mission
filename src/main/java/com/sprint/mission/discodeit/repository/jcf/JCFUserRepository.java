package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

public class JCFUserRepository extends JCFRepository<User> implements UserRepository {
    private static final JCFUserRepository instance = new JCFUserRepository();

    private JCFUserRepository() {
        super();
    };

    public static JCFUserRepository getInstance() {
        return instance;
    }


}
