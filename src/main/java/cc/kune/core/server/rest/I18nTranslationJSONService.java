/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.rest;

import java.util.List;

import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.rack.filters.rest.REST;
import cc.kune.core.shared.SearcherConstants;
import cc.kune.core.shared.dto.I18nTranslationDTO;
import cc.kune.core.shared.dto.SearchResultDTO;

import com.google.inject.Inject;

public class I18nTranslationJSONService {
  private final I18nTranslationManager manager;
  private final Mapper mapper;

  @Inject
  public I18nTranslationJSONService(final I18nTranslationManager manager, final Mapper mapper) {
    this.manager = manager;
    this.mapper = mapper;
  }

  @REST(params = { SearcherConstants.QUERY_PARAM })
  public List<I18nTranslationDTO> search(final String language) {
    final List<I18nTranslationDTO> results = manager.getUntranslatedLexicon(language);
    return results;
  }

  @REST(params = { SearcherConstants.QUERY_PARAM, SearcherConstants.START_PARAM,
      SearcherConstants.LIMIT_PARAM })
  public SearchResultDTO<I18nTranslationDTO> search(final String language, final Integer firstResult,
      final Integer maxResults) {
    final SearchResultDTO<I18nTranslationDTO> results = manager.getUntranslatedLexicon(language,
        firstResult, maxResults);
    return results;
  }

  @REST(params = { SearcherConstants.QUERY_PARAM })
  public List<I18nTranslationDTO> searchtranslated(final String language) {
    final List<I18nTranslationDTO> results = manager.getTranslatedLexicon(language);
    return results;
  }

  @REST(params = { SearcherConstants.QUERY_PARAM, SearcherConstants.START_PARAM,
      SearcherConstants.LIMIT_PARAM })
  public SearchResultDTO<I18nTranslationDTO> searchtranslated(final String language,
      final Integer firstResult, final Integer maxResults) {
    final SearchResultDTO<I18nTranslationDTO> results = manager.getTranslatedLexicon(language,
        firstResult, maxResults);
    return results;
  }

}
