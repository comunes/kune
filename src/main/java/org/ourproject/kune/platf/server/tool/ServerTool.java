package org.ourproject.kune.platf.server.tool;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

public interface ServerTool {
    String getName();

    Group initGroup(User user, Group group);
}
