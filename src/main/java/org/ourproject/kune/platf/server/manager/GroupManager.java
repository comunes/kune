package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;

public interface GroupManager extends Manager<Group, Long> {

    Group findByShortName(String groupName);

    Group createGroup(String shortName, String longName, User user) throws SerializableException;

    Group createGroup(Group group, User user) throws SerializableException;

    Group createUserGroup(User user);

    Group getDefaultGroup();

    // User createUser(User user) throws SerializableException;

}
