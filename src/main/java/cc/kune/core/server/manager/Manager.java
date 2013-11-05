/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

public interface Manager<T, X> {

  T find(X id);

  T merge(T entity);

  T persist(T entity);

  void reIndex();

  void remove(T entity);

  SearchResult<T> search(final String query, final String[] fields, final BooleanClause.Occur[] flags,
      final Integer firstResult, final Integer maxResults);

  SearchResult<T> search(final String[] queries, final String[] fields,
      final BooleanClause.Occur[] flags, final Integer firstResult, final Integer maxResults);

  SearchResult<T> search(final String[] queries, final String[] fields, final Integer firstResult,
      final Integer maxResults);

}
