package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.UserManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserManagerDefault implements UserManager {
    private final User finder;

    @Inject
    public UserManagerDefault(final User finder) {
	this.finder = finder;
    }

    public List<User> getAll() {
	return finder.getAll();
    }

    public User getByShortName(final String shortName) {
	return finder.getByShortName(shortName);
    }

    public User login(final String nickOrEmail, final String passwd) {
	// TODO: integrate a existing Auth manager
	User user;
	try {
	    user = finder.getByShortName(nickOrEmail);
	} catch (NoResultException e) {
	    try {
		user = finder.getByEmail(nickOrEmail);
	    } catch (NoResultException e2) {
		return null;
	    }
	}
	if (user.getPassword().equals(passwd)) {
	    return user;
	} else {
	    return null;
	}
    }

}
