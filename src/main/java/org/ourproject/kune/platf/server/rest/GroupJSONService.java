package org.ourproject.kune.platf.server.rest;

import java.util.List;

import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.GroupManager;
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
    public List<LinkDTO> search(final String search) {
        List<Group> results = manager.search(search);
        return mapper.mapList(results, LinkDTO.class);
    }

    @REST(params = { "query", "first", "max" })
    public List<LinkDTO> search(final String search, final Integer firstResult, final Integer maxResults) {
        List<Group> results = manager.search(search, firstResult, maxResults);
        return mapper.mapList(results, LinkDTO.class);
    }
}