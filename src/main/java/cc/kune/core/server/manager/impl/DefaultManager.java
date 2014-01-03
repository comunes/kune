/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.core.server.manager.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import cc.kune.core.server.manager.SearchResult;

import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultManager.
 * 
 * @param <T>
 *          the generic type
 * @param <K>
 *          the key type
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class DefaultManager<T, K> {

  /** The Constant LUCENE_VERSION. */
  protected final static Version LUCENE_VERSION = Version.LUCENE_35;

  /** The entity class. */
  private final Class<T> entityClass;

  /** The log. */
  protected final Log log;

  /** The provider. */
  private final Provider<EntityManager> provider;

  /**
   * Instantiates a new default manager.
   * 
   * @param provider
   *          the provider
   * @param entityClass
   *          the entity class
   */
  public DefaultManager(final Provider<EntityManager> provider, final Class<T> entityClass) {
    this.provider = provider;
    this.entityClass = entityClass;
    log = LogFactory.getLog(entityClass);
  }

  /**
   * use carefully!!!.
   * 
   * @param <X>
   *          the generic type
   * @param entityClass
   *          the entity class
   * @param primaryKey
   *          the primary key
   * @return the x
   */
  protected <X> X find(final Class<X> entityClass, final K primaryKey) {
    return getEntityManager().find(entityClass, primaryKey);
  }

  /**
   * Find.
   * 
   * @param primaryKey
   *          the primary key
   * @return the t
   */
  public T find(final K primaryKey) {
    return getEntityManager().find(entityClass, primaryKey);
  }

  /**
   * Flush.
   */
  public void flush() {
    getEntityManager().flush();
  }

  /**
   * Gets the entity manager.
   * 
   * @return the entity manager
   */
  private EntityManager getEntityManager() {
    return provider.get();
  }

  /**
   * Gets the query.
   * 
   * @param qlString
   *          the ql string
   * @return the query
   */
  protected javax.persistence.Query getQuery(final String qlString) {
    return getEntityManager().createQuery(qlString);
  }

  /**
   * Merge.
   * 
   * @param <E>
   *          the element type
   * @param entity
   *          the entity
   * @param entityClass
   *          the entity class
   * @return the e
   */
  public <E> E merge(final E entity, final Class<E> entityClass) {
    getEntityManager().merge(entity);
    return entity;
  }

  /**
   * Merge.
   * 
   * @param entity
   *          the entity
   * @return the t
   */
  public T merge(final T entity) {
    return getEntityManager().merge(entity);
  }

  /**
   * Persist.
   * 
   * @param <E>
   *          the element type
   * @param entity
   *          the entity
   * @param entityClass
   *          the entity class
   * @return the e
   */
  public <E> E persist(final E entity, final Class<E> entityClass) {
    getEntityManager().persist(entity);
    return entity;
  }

  /**
   * Persist.
   * 
   * @param entity
   *          the entity
   * @return the t
   */
  public T persist(final T entity) {
    return persist(entity, entityClass);
  }

  /**
   * Re index.
   */
  public void reIndex() {
    // http://docs.jboss.org/hibernate/search/4.1/reference/en-US/html_single/#search-batchindex
    final FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(getEntityManager().getEntityManagerFactory().createEntityManager());
    try {
      fullTextEm.createIndexer().startAndWait();
    } catch (final InterruptedException e) {
      throw new ServerManagerException("Error reindexing", e);
    }

    // final Session fullTextSession = (org.hibernate.Session)
    // fullTextEm.getDelegate();
    // fullTextSession.setFlushMode(FlushMode.AUTO);
    // fullTextSession.setCacheMode(CacheMode.IGNORE);
    // // final Transaction transaction = fullTextSession.beginTransaction();
    // // Scrollable results will avoid loading too many objects in memory
    // final ScrollableResults results =
    // fullTextSession.createCriteria(entityClass).setFetchSize(
    // BATCH_SIZE).scroll(ScrollMode.FORWARD_ONLY);
    // int index = 0;
    // while (results.next()) {
    // index++;
    //
    // fullTextEm.index(results.get(0)); // index each element
    // if (index % BATCH_SIZE == 0) {
    // // fullTextSession.flushToIndexes(); //apply changes to indexes
    // fullTextSession.flush(); // apply changes to indexes
    // fullTextSession.clear(); // free memory since the queue is processed
    // }
    // }
    // http://stackoverflow.com/questions/10248598/clearing-locks-between-junit-tests-in-hibernate-search-4-1
    // transaction.commit();
    fullTextEm.close();
  }

  /**
   * Removes the.
   * 
   * @param entity
   *          the entity
   */
  public void remove(final T entity) {
    getEntityManager().remove(entity);
  }

  /**
   * Search.
   * 
   * @param query
   *          the query
   * @return the search result
   */
  public SearchResult<T> search(final Query query) {
    return search(query, null, null);
  }

  /**
   * Search.
   * 
   * @param query
   *          the query
   * @param firstResult
   *          the first result
   * @param maxResults
   *          the max results
   * @return the search result
   */
  @SuppressWarnings("unchecked")
  public SearchResult<T> search(final Query query, final Integer firstResult, final Integer maxResults) {
    final FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(getEntityManager().getEntityManagerFactory().createEntityManager());
    final FullTextQuery emQuery = fullTextEm.createFullTextQuery(query, entityClass);
    if (firstResult != null && maxResults != null) {
      emQuery.setFirstResult(firstResult);
      emQuery.setMaxResults(maxResults);
    }
    final SearchResult<T> searchResult = new SearchResult<T>(emQuery.getResultSize(),
        emQuery.getResultList());
    return searchResult;
  }

  /**
   * Search.
   * 
   * @param query
   *          the query
   * @param fields
   *          the fields
   * @param flags
   *          the flags
   * @param firstResult
   *          the first result
   * @param maxResults
   *          the max results
   * @return the search result
   */
  public SearchResult<T> search(final String query, final String[] fields,
      final BooleanClause.Occur[] flags, final Integer firstResult, final Integer maxResults) {
    Query queryQ;
    try {
      queryQ = MultiFieldQueryParser.parse(LUCENE_VERSION, query, fields, flags, new StandardAnalyzer(
          LUCENE_VERSION));
    } catch (final ParseException e) {
      throw new ServerManagerException("Error parsing search", e);
    }
    return search(queryQ, firstResult, maxResults);
  }

  /**
   * Search.
   * 
   * @param queries
   *          the queries
   * @param fields
   *          the fields
   * @param flags
   *          the flags
   * @param firstResult
   *          the first result
   * @param maxResults
   *          the max results
   * @return the search result
   */
  public SearchResult<T> search(final String[] queries, final String[] fields,
      final BooleanClause.Occur[] flags, final Integer firstResult, final Integer maxResults) {
    Query query;
    try {
      query = MultiFieldQueryParser.parse(LUCENE_VERSION, queries, fields, flags, new StandardAnalyzer(
          LUCENE_VERSION));
    } catch (final ParseException e) {
      throw new ServerManagerException("Error parsing search", e);
    }
    return search(query, firstResult, maxResults);
  }

  /**
   * Search.
   * 
   * @param queries
   *          the queries
   * @param fields
   *          the fields
   * @param firstResult
   *          the first result
   * @param maxResults
   *          the max results
   * @return the search result
   */
  public SearchResult<T> search(final String[] queries, final String[] fields,
      final Integer firstResult, final Integer maxResults) {
    Query query;
    try {
      query = MultiFieldQueryParser.parse(LUCENE_VERSION, queries, fields, new StandardAnalyzer(
          LUCENE_VERSION));
    } catch (final ParseException e) {
      throw new ServerManagerException("Error parsing search", e);
    }
    return search(query, firstResult, maxResults);
  }

  /**
   * Size deprecated.
   * 
   * @return the int
   */
  @SuppressWarnings("unchecked")
  @Deprecated
  public int sizeDeprecated() {
    final FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(getEntityManager());
    final List<T> entities = fullTextEm.createQuery(
        "SELECT e FROM " + entityClass.getSimpleName() + " AS e").getResultList();
    return entities.size();
  }

}
