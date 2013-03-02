/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

public abstract class DefaultManager<T, K> {
  protected final static Version LUCENE_VERSION = Version.LUCENE_35;
  private final Class<T> entityClass;
  protected final Log log;
  private final Provider<EntityManager> provider;

  public DefaultManager(final Provider<EntityManager> provider, final Class<T> entityClass) {
    this.provider = provider;
    this.entityClass = entityClass;
    log = LogFactory.getLog(entityClass);
  }

  /**
   * use carefully!!!
   */
  protected <X> X find(final Class<X> entityClass, final K primaryKey) {
    return getEntityManager().find(entityClass, primaryKey);
  }

  public T find(final K primaryKey) {
    return getEntityManager().find(entityClass, primaryKey);
  }

  public void flush() {
    getEntityManager().flush();
  }

  private EntityManager getEntityManager() {
    return provider.get();
  }

  protected javax.persistence.Query getQuery(final String qlString) {
    return getEntityManager().createQuery(qlString);
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

  public void remove(final T entity) {
    getEntityManager().remove(entity);
  }

  public SearchResult<T> search(final Query query) {
    return search(query, null, null);
  }

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

  @SuppressWarnings("unchecked")
  @Deprecated
  public int sizeDeprecated() {
    final FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(getEntityManager());
    final List<T> entities = fullTextEm.createQuery(
        "SELECT e FROM " + entityClass.getSimpleName() + " AS e").getResultList();
    return entities.size();
  }

}
