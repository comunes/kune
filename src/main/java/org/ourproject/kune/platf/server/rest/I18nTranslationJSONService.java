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

import java.util.List;

import org.ourproject.kune.platf.client.dto.I18nTranslationDTO;
import org.ourproject.kune.platf.client.dto.SearchResultDTO;
import org.ourproject.kune.platf.server.domain.I18nTranslation;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.rack.filters.rest.REST;
import org.ourproject.kune.workspace.client.search.SearcherContants;

import com.google.inject.Inject;

public class I18nTranslationJSONService {
    private final I18nTranslationManager manager;
    private final Mapper mapper;

    @Inject
    public I18nTranslationJSONService(final I18nTranslationManager manager, final Mapper mapper) {
        this.manager = manager;
        this.mapper = mapper;
    }

    @REST(params = { SearcherContants.QUERY_PARAM })
    public List<I18nTranslationDTO> search(final String language) {
        List<I18nTranslation> results = manager.getUntranslatedLexicon(language);
        return mapper.mapList(results, I18nTranslationDTO.class);
    }

    @REST(params = { SearcherContants.QUERY_PARAM, SearcherContants.START_PARAM, SearcherContants.LIMIT_PARAM })
    public SearchResultDTO<I18nTranslationDTO> search(final String language, final Integer firstResult,
            final Integer maxResults) {
        SearchResult<I18nTranslation> results = manager.getUntranslatedLexicon(language, firstResult, maxResults);
        return mapper.mapSearchResult(results, I18nTranslationDTO.class);
    }

    @REST(params = { SearcherContants.QUERY_PARAM })
    public List<I18nTranslationDTO> searchtranslated(final String language) {
        List<I18nTranslation> results = manager.getTranslatedLexicon(language);
        return mapper.mapList(results, I18nTranslationDTO.class);
    }

    @REST(params = { SearcherContants.QUERY_PARAM, SearcherContants.START_PARAM, SearcherContants.LIMIT_PARAM })
    public SearchResultDTO<I18nTranslationDTO> searchtranslated(final String language, final Integer firstResult,
            final Integer maxResults) {
        SearchResult<I18nTranslation> results = manager.getTranslatedLexicon(language, firstResult, maxResults);
        return mapper.mapSearchResult(results, I18nTranslationDTO.class);
    }

}