/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import com.google.inject.Provider;

public abstract class DefaultManager<T, K> {
    private final Provider<EntityManager> provider;
    private final Class<T> entityClass;

    public DefaultManager(final Provider<EntityManager> provider, final Class<T> entityClass) {
        this.provider = provider;
        this.entityClass = entityClass;
    }

    public T find(final Long primaryKey) {
        return getEntityManager().find(entityClass, primaryKey);
    }

    public <E> E merge(final E entity, final Class<E> entityClass) {
        getEntityManager().merge(entity);
        return entity;
    }

    public T merge(final T entity) {
        return getEntityManager().merge(entity);
    }

    public <E> E persist(final E entity, final Class<E> entityClass) {
        getEntityManager().persist(entity);
        return entity;
    }

    public T persist(final T entity) {
        return persist(entity, entityClass);
    }

    @SuppressWarnings("unchecked")
    public void reIndex() {
        // Inject this?
        final FullTextEntityManager fullTextEm = Search.createFullTextEntityManager(getEntityManager());
        fullTextEm.purgeAll(entityClass);
        fullTextEm.getTransaction().commit();
        fullTextEm.getTransaction().begin();
        final List<T> entities = fullTextEm.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " AS e").getResultList();
        for (final T e : entities) {
            fullTextEm.index(e);
        }
        fullTextEm.getSearchFactory().optimize(entityClass);
    }

    public SearchResult<T> search(final Query query) {
        return search(query, null, null);
    }

    @SuppressWarnings("unchecked")
    public SearchResult<T> search(final Query query, final Integer firstResult, final Integer maxResults) {
        final FullTextQuery emQuery = Search.createFullTextEntityManager(getEntityManager()).createFullTextQuery(query,
                entityClass);
        if (firstResult != null && maxResults != null) {
            emQuery.setFirstResult(firstResult);
            emQuery.setMaxResults(maxResults);
        }
        return new SearchResult<T>(new Long(emQuery.getResultSize()), emQuery.getResultList());
    }

    public SearchResult<T> search(final String query, final String[] fields, final BooleanClause.Occur[] flags,
            final Integer firstResult, final Integer maxResults) {
        Query queryQ;
        try {
            queryQ = MultiFieldQueryParser.parse(query, fields, flags, new StandardAnalyzer());
        } catch (final ParseException e) {
            throw new RuntimeException("Error parsing search");
        }
        return search(queryQ, firstResult, maxResults);
    }

    public SearchResult<T> search(final String[] queries, final String[] fields, final BooleanClause.Occur[] flags,
            final Integer firstResult, final Integer maxResults) {
        Query query;
        try {
            query = MultiFieldQueryParser.parse(queries, fields, flags, new StandardAnalyzer());
        } catch (final ParseException e) {
            throw new RuntimeException("Error parsing search");
        }
        return search(query, firstResult, maxResults);
    }

    public SearchResult<T> search(final String[] queries, final String[] fields, final Integer firstResult,
            final Integer maxResults) {
        Query query;
        try {
            query = MultiFieldQueryParser.parse(queries, fields, new StandardAnalyzer());
        } catch (final ParseException e) {
            throw new RuntimeException("Error parsing search");
        }
        return search(query, firstResult, maxResults);
    }

    /**
     * use carefully!!!
     */
    protected <X> X find(final Class<X> entityClass, final Long primaryKey) {
        return getEntityManager().find(entityClass, primaryKey);
    }

    private EntityManager getEntityManager() {
        return provider.get();
    }

}
