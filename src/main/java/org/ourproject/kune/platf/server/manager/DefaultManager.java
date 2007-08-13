package org.ourproject.kune.platf.server.manager;

import javax.persistence.EntityManager;

import com.google.inject.Provider;

public abstract class DefaultManager<T, K> {
    private final Provider<EntityManager> provider;
    private final Class<T> entityClass;

    public DefaultManager(final Provider<EntityManager> provider, final Class<T> entityClass) {
	this.provider = provider;
	this.entityClass = entityClass;
    }

    private EntityManager getEntityManager() {
	return provider.get();
    }

    public T persist(final T entity) {
	getEntityManager().persist(entity);
	return entity;
    }

    public T find(final Long primaryKey) {
	return getEntityManager().find(entityClass, primaryKey);
    }

}
