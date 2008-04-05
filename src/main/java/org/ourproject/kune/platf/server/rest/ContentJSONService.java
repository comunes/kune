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

    @REST(params = { "query" })
    public SearchResultDTO<LinkDTO> search(final String search) {
        return search(search, null, null);
    }

    @REST(params = { "query", "start", "limit" })
    public SearchResultDTO<LinkDTO> search(final String search, final Integer firstResult, final Integer maxResults) {
        SearchResult<Content> results = contentManager.search(search, firstResult, maxResults);
        SearchResult<Container> resultsContainer = containerManager.search(search, firstResult, maxResults);
        results.setSize(results.getSize() + resultsContainer.getSize());
        results.getList().addAll(results.getList());
        return mapper.mapSearchResult(results, LinkDTO.class);
    }

}