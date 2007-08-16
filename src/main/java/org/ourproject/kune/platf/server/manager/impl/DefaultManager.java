package org.ourproject.kune.platf.server.manager.impl;

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
	return persist(entity, entityClass);
    }

    public T find(final Long primaryKey) {
	return getEntityManager().find(entityClass, primaryKey);
    }

    public <E> E persist(final E entity, final Class<E> entityClass) {
	getEntityManager().persist(entity);
	return entity;
    }

    public T merge(final T entity) {
	return getEntityManager().merge(entity);
    }

}
