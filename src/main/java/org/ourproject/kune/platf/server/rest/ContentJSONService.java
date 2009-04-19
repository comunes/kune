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
package org.ourproject.kune.platf.server.rest;

import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.SearchResultDTO;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.rack.filters.rest.REST;
import org.ourproject.kune.workspace.client.search.SearcherContants;

import com.google.inject.Inject;

public class ContentJSONService {
    private final ContentManager contentManager;
    private final Mapper mapper;
    private final ContainerManager containerManager;

    @Inject
    public ContentJSONService(final ContentManager contentManager, final ContainerManager containerManager,
            final Mapper mapper) {
        this.containerManager = containerManager;
        this.contentManager = contentManager;
        this.mapper = mapper;
    }

    @REST(params = { SearcherContants.QUERY_PARAM })
    public SearchResultDTO<LinkDTO> search(final String search) {
        return search(search, null, null);
    }

    @REST(params = { SearcherContants.QUERY_PARAM, SearcherContants.START_PARAM, SearcherContants.LIMIT_PARAM })
    public SearchResultDTO<LinkDTO> search(final String search, final Integer firstResult, final Integer maxResults) {
        SearchResult<Content> results = contentManager.search(search, firstResult, maxResults);
        SearchResult<Container> resultsContainer = containerManager.search(search, firstResult, maxResults);
        SearchResultDTO<LinkDTO> resultMapped = map(results);
        SearchResultDTO<LinkDTO> containersMapped = map(resultsContainer);
        resultMapped.getList().addAll(containersMapped.getList());
        resultMapped.setSize(resultMapped.getSize() + containersMapped.getSize());
        return resultMapped;
    }

    @REST(params = { SearcherContants.QUERY_PARAM, SearcherContants.START_PARAM, SearcherContants.LIMIT_PARAM,
            SearcherContants.MIMETYPE_PARAM })
    public SearchResultDTO<LinkDTO> search(final String search, final Integer firstResult, final Integer maxResults,
            final String mimetype) {
        SearchResult<Content> results = contentManager.searchMime(search, firstResult, maxResults, mimetype);
        return map(results);
    }

    @REST(params = { SearcherContants.QUERY_PARAM, SearcherContants.START_PARAM, SearcherContants.LIMIT_PARAM,
            SearcherContants.MIMETYPE_PARAM, SearcherContants.MIMETYPE2_PARAM })
    public SearchResultDTO<LinkDTO> search(final String search, final Integer firstResult, final Integer maxResults,
            final String mimetype, final String mimetype2) {
        SearchResult<Content> results1st = contentManager.searchMime(search, firstResult, maxResults, mimetype);
        SearchResult<Content> results2nd = contentManager.searchMime(search, firstResult, maxResults, mimetype2);
        results1st.getList().addAll(results2nd.getList());
        return map(results1st);
    }

    private SearchResultDTO<LinkDTO> map(final SearchResult<?> results) {
        return mapper.mapSearchResult(results, LinkDTO.class);
    }
}