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

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;

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

    public <E> E merge(final E entity, final Class<E> entityClass) {
        getEntityManager().merge(entity);
        return entity;
    }

    public List<T> search(final Query query) {
        return search(query, null, null);
    }

    public List<T> search(final Query query, final Integer firstResult, final Integer maxResults) {
        // TODO: Inject this
        FullTextEntityManager fullTextEm = org.hibernate.search.jpa.Search
                .createFullTextEntityManager(getEntityManager());
        FullTextQuery emQuery = fullTextEm.createFullTextQuery(query, entityClass);
        if (firstResult != null && maxResults != null) {
            emQuery.setFirstResult(firstResult);
            emQuery.setMaxResults(maxResults);
        }
        return emQuery.getResultList();

    }

    public void reIndex() {
        // TODO: Inject this
        FullTextEntityManager fullTextEm = org.hibernate.search.jpa.Search
                .createFullTextEntityManager(getEntityManager());
        fullTextEm.purgeAll(entityClass);
        fullTextEm.getTransaction().commit();
        fullTextEm.getTransaction().begin();
        List<T> entities = fullTextEm.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " AS e")
                .getResultList();
        for (T e : entities) {
            fullTextEm.index(e);
        }
        fullTextEm.getSearchFactory().optimize(entityClass);
    }

    /**
     * use carefully!!!
     */
    protected <X> X find(final Class<X> entityClass, final Long primaryKey) {
        return getEntityManager().find(entityClass, primaryKey);
    }

}
