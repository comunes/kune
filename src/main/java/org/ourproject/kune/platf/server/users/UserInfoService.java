package org.ourproject.kune.platf.server.users;

import org.ourproject.kune.platf.server.domain.User;

public interface UserInfoService {
    UserInfo buildInfo(User user);
}
