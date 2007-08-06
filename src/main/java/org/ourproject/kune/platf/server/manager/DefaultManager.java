package org.ourproject.kune.platf.server.manager;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DefaultManager {
    private final Provider<EntityManager> provider;

    @Inject
    public DefaultManager(final Provider<EntityManager> provider) {
	this.provider = provider;
    }

    public EntityManager getEntityManager() {
	return provider.get();
    }

}
