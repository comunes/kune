package org.ourproject.kune.platf.server.manager;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

public class UserManager extends DefaultManager {
    private User userFinder;

    @Inject
    public UserManager(final Provider<EntityManager> provider) {
	super(provider);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<User> getAll() {
	return userFinder.getAll();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public User createUser(final User user) {
	getEntityManager().persist(user);
	return user;
    }

    @Inject
    public void setUserFinder(final User userFinder) {
	this.userFinder = userFinder;
    }

}
