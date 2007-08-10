package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Group;

public interface GroupManager {

    Group find(String groupName);

}
