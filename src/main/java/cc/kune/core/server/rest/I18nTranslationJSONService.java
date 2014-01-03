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

import java.util.List;

import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.server.rack.filters.rest.REST;
import cc.kune.core.shared.SearcherConstants;
import cc.kune.core.shared.dto.I18nTranslationDTO;
import cc.kune.core.shared.dto.SearchResultDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class I18nTranslationJSONService {
  private final I18nTranslationManager manager;

  @Inject
  public I18nTranslationJSONService(final I18nTranslationManager manager) {
    this.manager = manager;
  }

  @REST(params = { SearcherConstants.TO_LANGUAGE_PARAM, SearcherConstants.FROM_LANGUAGE_PARAM, })
  public List<I18nTranslationDTO> search(final String language, final String languageFrom) {
    final List<I18nTranslationDTO> results = manager.getUntranslatedLexicon(language, languageFrom);
    return results;
  }

  @REST(params = { SearcherConstants.TO_LANGUAGE_PARAM, SearcherConstants.FROM_LANGUAGE_PARAM,
      SearcherConstants.START_PARAM, SearcherConstants.LIMIT_PARAM })
  public SearchResultDTO<I18nTranslationDTO> search(final String language, final String languageFrom,
      final Integer firstResult, final Integer maxResults) {
    final SearchResultDTO<I18nTranslationDTO> results = manager.getUntranslatedLexicon(language,
        languageFrom, firstResult, maxResults);
    return results;
  }

  @REST(params = { SearcherConstants.TO_LANGUAGE_PARAM, SearcherConstants.FROM_LANGUAGE_PARAM })
  public List<I18nTranslationDTO> searchtranslated(final String language, final String languageFrom) {
    final List<I18nTranslationDTO> results = manager.getTranslatedLexicon(language, languageFrom);
    return results;
  }

  @REST(params = { SearcherConstants.TO_LANGUAGE_PARAM, SearcherConstants.FROM_LANGUAGE_PARAM,
      SearcherConstants.START_PARAM, SearcherConstants.LIMIT_PARAM })
  public SearchResultDTO<I18nTranslationDTO> searchtranslated(final String language,
      final String languageFrom, final Integer firstResult, final Integer maxResults) {
    final SearchResultDTO<I18nTranslationDTO> results = manager.getTranslatedLexicon(language,
        languageFrom, firstResult, maxResults);
    return results;
  }

}
