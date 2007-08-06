package org.ourproject.kune.platf.server.manager;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

public class UserManager {
    @Inject
    public UserManager(final Provider<EntityManager> provider) {
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<User> getAll() {
	return null;
    }

    public User createUser(final User user) {
	return null;
    }
}
