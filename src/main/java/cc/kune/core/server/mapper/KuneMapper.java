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
package cc.kune.core.server.mapper;

import java.util.List;

import cc.kune.core.server.manager.SearchResult;
import cc.kune.core.shared.dto.SearchResultDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface KuneMapper.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface KuneMapper {

  /**
   * Map.
   * 
   * @param <T>
   *          the generic type
   * @param source
   *          the source
   * @param type
   *          the type
   * @return the t
   */
  <T> T map(Object source, Class<T> type);

  /**
   * Map list.
   * 
   * @param <T>
   *          the generic type
   * @param list
   *          the list
   * @param type
   *          the type
   * @return the list
   */
  <T> List<T> mapList(List<?> list, Class<T> type);

  /**
   * Map search result.
   * 
   * @param <K>
   *          the key type
   * @param <T>
   *          the generic type
   * @param result
   *          the result
   * @param type
   *          the type
   * @return the search result dto
   */
  <K, T> SearchResultDTO<T> mapSearchResult(SearchResult<K> result, Class<T> type);

}
