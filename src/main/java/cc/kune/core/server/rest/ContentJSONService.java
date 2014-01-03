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

import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.SearchResult;
import cc.kune.core.server.mapper.KuneMapper;
import cc.kune.core.server.rack.filters.rest.REST;
import cc.kune.core.shared.SearcherConstants;
import cc.kune.core.shared.dto.LinkDTO;
import cc.kune.core.shared.dto.SearchResultDTO;
import cc.kune.domain.Container;
import cc.kune.domain.Content;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ContentJSONService {
  private final ContainerManager containerManager;
  private final ContentManager contentManager;
  private final KuneMapper mapper;

  @Inject
  public ContentJSONService(final ContentManager contentManager,
      final ContainerManager containerManager, final KuneMapper mapper) {
    this.containerManager = containerManager;
    this.contentManager = contentManager;
    this.mapper = mapper;
  }

  private SearchResultDTO<LinkDTO> map(final SearchResult<?> results) {
    return mapper.mapSearchResult(results, LinkDTO.class);
  }

  @REST(params = { SearcherConstants.QUERY_PARAM })
  public SearchResultDTO<LinkDTO> search(final String search) {
    return search(search, null, null);
  }

  @REST(params = { SearcherConstants.QUERY_PARAM, SearcherConstants.START_PARAM,
      SearcherConstants.LIMIT_PARAM })
  public SearchResultDTO<LinkDTO> search(final String search, final Integer firstResult,
      final Integer maxResults) {
    final SearchResult<Content> results = contentManager.search(search, firstResult, maxResults);
    final SearchResult<Container> resultsContainer = containerManager.search(search, firstResult,
        maxResults);
    final SearchResultDTO<LinkDTO> resultMapped = map(results);
    final SearchResultDTO<LinkDTO> containersMapped = map(resultsContainer);
    resultMapped.getList().addAll(containersMapped.getList());
    resultMapped.setSize(resultMapped.getSize() + containersMapped.getSize());
    return resultMapped;
  }

  @REST(params = { SearcherConstants.QUERY_PARAM, SearcherConstants.START_PARAM,
      SearcherConstants.LIMIT_PARAM, SearcherConstants.GROUP_PARAM, SearcherConstants.MIMETYPE_PARAM })
  public SearchResultDTO<LinkDTO> search(final String search, final Integer firstResult,
      final Integer maxResults, final String group, final String mimetype) {
    final SearchResult<Content> results = contentManager.searchMime(search, firstResult, maxResults,
        group, mimetype);
    return map(results);
  }

  @REST(params = { SearcherConstants.QUERY_PARAM, SearcherConstants.START_PARAM,
      SearcherConstants.LIMIT_PARAM, SearcherConstants.GROUP_PARAM, SearcherConstants.MIMETYPE_PARAM,
      SearcherConstants.MIMETYPE2_PARAM })
  public SearchResultDTO<LinkDTO> search(final String search, final Integer firstResult,
      final Integer maxResults, final String group, final String mimetype, final String mimetype2) {
    return map(contentManager.searchMime(search, firstResult, maxResults, group, mimetype, mimetype2));
  }
}
