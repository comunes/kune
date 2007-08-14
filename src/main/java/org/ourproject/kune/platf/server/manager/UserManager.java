package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;

public interface UserManager extends Manager<User, Long> {

    User createUser(User user) throws SerializableException;

    User getByShortName(String string);

    User login(String nick, String pass);

}
