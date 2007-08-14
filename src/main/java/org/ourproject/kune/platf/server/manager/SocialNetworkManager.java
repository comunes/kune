package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

public interface SocialNetworkManager {
    void addAdmin(Group userGroup, User user);
}
