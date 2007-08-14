package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;

public interface GroupManager {

    Group get(String groupName);

    void initGroup(User user, Group userGroup);

    void create(User user, Group group) throws SerializableException;

}
