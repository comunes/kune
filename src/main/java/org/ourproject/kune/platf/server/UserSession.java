package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class UserSession {
    private User user;

    public UserSession() {
    }

    public User getUser() {
	return user;
    }

    public User setUser(final User user) {
	this.user = user;
	return user;
    }

}
