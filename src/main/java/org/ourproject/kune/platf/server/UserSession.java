package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.domain.User;

public class UserSession {
    private User user;

    public User getUser() {
	return user;
    }

    public void setUser(final User user) {
	this.user = user;
    }

}
