package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

public interface GroupManager {
    Group get(String groupName);

    void initGroup(User user, Group userGroup);

}
