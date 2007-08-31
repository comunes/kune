package org.ourproject.kune.platf.server.users;

import org.ourproject.kune.platf.server.domain.User;

public class UserInfoServiceDefault implements UserInfoService {

    public UserInfo buildInfo(final User user) {
	UserInfo info = new UserInfo();

	info.setName(user.getName());
	info.setChatName(user.getShortName());
	info.setChatPassword(user.getPassword());
	return info;
    }

}
