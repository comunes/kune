/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.rest;

import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.SearchResultDTO;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager.SearchResult;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.rack.filters.rest.REST;

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
        return mapper.mapSearchResult(results, LinkDTO.class);
    }

    @REST(params = { "query", "first", "max" })
    public SearchResultDTO search(final String search, final Integer firstResult, final Integer maxResults) {
        SearchResult results = manager.search(search, firstResult, maxResults);
        return mapper.mapSearchResult(results, LinkDTO.class);
    }

}