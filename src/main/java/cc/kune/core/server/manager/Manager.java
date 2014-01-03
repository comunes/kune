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
package cc.kune.core.server.manager;

import org.apache.lucene.search.BooleanClause;

// TODO: Auto-generated Javadoc
/**
 * The Interface Manager.
 * 
 * @param <T>
 *          the generic type
 * @param <X>
 *          the generic type
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface Manager<T, X> {

  /**
   * Find.
   * 
   * @param id
   *          the id
   * @return the t
   */
  T find(X id);

  /**
   * Merge.
   * 
   * @param entity
   *          the entity
   * @return the t
   */
  T merge(T entity);

  /**
   * Persist.
   * 
   * @param entity
   *          the entity
   * @return the t
   */
  T persist(T entity);

  /**
   * Re index.
   */
  void reIndex();

  /**
   * Removes the.
   * 
   * @param entity
   *          the entity
   */
  void remove(T entity);

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
  SearchResult<T> search(final String query, final String[] fields, final BooleanClause.Occur[] flags,
      final Integer firstResult, final Integer maxResults);

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
  SearchResult<T> search(final String[] queries, final String[] fields,
      final BooleanClause.Occur[] flags, final Integer firstResult, final Integer maxResults);

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
  SearchResult<T> search(final String[] queries, final String[] fields, final Integer firstResult,
      final Integer maxResults);

}
