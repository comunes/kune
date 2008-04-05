package org.ourproject.kune.platf.server.users;

import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;

public interface UserInfoService {

    UserInfo buildInfo(User user, String userHash) throws SerializableException;

}
