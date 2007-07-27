package org.ourproject.kune.platf.server.manager;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.finders.UserFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

public class UserManager {
    private final UserFinder finder;

    @Inject public UserManager(Provider<EntityManager> provider, UserFinder finder) {
        this.finder = finder;
    }
    
    @Transactional(type=TransactionType.READ_ONLY)
    public List<User> getAll() {
        return null;
    }
}
