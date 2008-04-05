package org.ourproject.kune.platf.server.rest;

import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.SearchResultDTO;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.rack.filters.rest.REST;

import com.google.inject.Inject;

public class UserJSONService {
    private final UserManager manager;
    private final Mapper mapper;

    @Inject
    public UserJSONService(final UserManager manager, final Mapper mapper) {
        this.manager = manager;
        this.mapper = mapper;
    }

    @REST(params = { "query" })
    public SearchResultDTO<LinkDTO> search(final String search) {
        return search(search, null, null);
    }

    @REST(params = { "query", "start", "limit" })
    public SearchResultDTO<LinkDTO> search(final String search, final Integer firstResult, final Integer maxResults) {
        SearchResult<User> results = manager.search(search, firstResult, maxResults);
        return mapper.mapSearchResult(results, LinkDTO.class);
    }

}