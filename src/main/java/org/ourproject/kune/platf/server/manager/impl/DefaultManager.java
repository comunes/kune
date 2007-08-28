/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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

    /**
     * use carefully!!!
     */
    protected <X> X find(final Class<X> entityClass, final Long primaryKey) {
	return getEntityManager().find(entityClass, primaryKey);
    }

}
