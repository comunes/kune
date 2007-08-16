package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.User;

public interface UserManager {
    User login(String nickOrEmail, String passwd);
}
