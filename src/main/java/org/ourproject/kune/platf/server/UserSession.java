package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class UserSession {
    private User user;
    private String hash;

    public UserSession() {
    }

    public User getUser() {
	return user;
    }

    public User setUser(final User user) {
	this.user = user;
	return user;
    }

    public String getHash() {
	return hash;
    }

    public void setHash(final String hash) {
	this.hash = hash;
    }

    public Group getGroup() {
	return user != null ? user.getUserGroup() : null;
    }

}
