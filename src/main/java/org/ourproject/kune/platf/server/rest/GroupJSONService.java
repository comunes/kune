package org.ourproject.kune.platf.server.rest;

import org.ourproject.kune.platf.client.dto.SearchResultDTO;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager.SearchResult;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.rack.filters.rest.REST;

import com.google.inject.Inject;

public class GroupJSONService {
    private final GroupManager manager;
    private final Mapper mapper;

    @Inject
    public GroupJSONService(final GroupManager manager, final Mapper mapper) {
        this.manager = manager;
        this.mapper = mapper;
    }

    @REST(params = { "query" })
    public SearchResultDTO search(final String search) {
        SearchResult results = manager.search(search);
        return mapper.map(results, SearchResultDTO.class);
    }

    @REST(params = { "query", "first", "max" })
    public SearchResultDTO search(final String search, final Integer firstResult, final Integer maxResults) {
        SearchResult results = manager.search(search, firstResult, maxResults);
        return mapper.map(results, SearchResultDTO.class);
    }

}