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
package cc.kune.core.server.rest;

import cc.kune.core.server.manager.SearchResult;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.mapper.KuneMapper;
import cc.kune.core.server.rack.filters.rest.REST;
import cc.kune.core.shared.SearcherConstants;
import cc.kune.core.shared.dto.GroupResultDTO;
import cc.kune.core.shared.dto.SearchResultDTO;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserJSONService {
  private final UserManager manager;
  private final KuneMapper mapper;

  @Inject
  public UserJSONService(final UserManager manager, final KuneMapper mapper) {
    this.manager = manager;
    this.mapper = mapper;
  }

  @REST(params = { SearcherConstants.QUERY_PARAM })
  public SearchResultDTO<GroupResultDTO> search(final String search) {
    return search(search, null, null);
  }

  @REST(params = { SearcherConstants.QUERY_PARAM, SearcherConstants.START_PARAM,
      SearcherConstants.LIMIT_PARAM })
  public SearchResultDTO<GroupResultDTO> search(final String search, final Integer firstResult,
      final Integer maxResults) {
    final SearchResult<User> results = manager.search(search, firstResult, maxResults);
    return mapper.mapSearchResult(results, GroupResultDTO.class);
  }

}
