package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.User;

public interface UserManager {
    User createUser(User user);

    User getByShortName(String string);

    User login(String nick, String pass);

}
