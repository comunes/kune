package org.ourproject.kune.platf.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class UserSession {
    private User user;

    @Inject
    public UserSession(final Logger logger) {
	logger.log(Level.INFO, "new session!");
    }

    public User getUser() {
	return user;
    }

    public void setUser(final User user) {
	this.user = user;
    }

}
