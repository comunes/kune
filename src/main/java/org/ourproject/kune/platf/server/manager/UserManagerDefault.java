package org.ourproject.kune.platf.server.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class UserManagerDefault extends DefaultManager<User, Long> implements UserManager {
    private User userFinder;
    private final GroupManager groupManager;

    @Inject
    public UserManagerDefault(final Provider<EntityManager> provider, final GroupManager groupManager) {
	super(provider, User.class);
	this.groupManager = groupManager;
    }

    public List<User> getAll() {
	return userFinder.getAll();
    }

    public User createUser(final User user) {
	persist(user);
	groupManager.initGroup(user, user.getUserGroup());
	return user;
    }

    public User getByShortName(final String shortName) {
	try {
	    return userFinder.getByShortName(shortName);
	} catch (NoResultException e) {
	    return null;
	}
    }

    @Inject
    public void setUserFinder(final User userFinder) {
	this.userFinder = userFinder;
    }

}
