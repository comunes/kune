/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.server.manager;

import java.util.HashMap;
import java.util.List;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.dto.I18nTranslationDTO;
import cc.kune.core.shared.dto.SearchResultDTO;
import cc.kune.domain.I18nTranslation;

public interface I18nTranslationManager extends Manager<I18nTranslation, Long> {

  HashMap<String, String> getLexicon(String language);

  List<I18nTranslationDTO> getTranslatedLexicon(String language, String languageFrom);

  SearchResultDTO<I18nTranslationDTO> getTranslatedLexicon(String language, String languageFrom,
      Integer firstResult, Integer maxResults);

  String getTranslation(String language, String text, String noteForTranslators);

  List<I18nTranslationDTO> getUntranslatedLexicon(String language, String languageFrom);

  SearchResultDTO<I18nTranslationDTO> getUntranslatedLexicon(String language, String languageFrom,
      Integer firstResult, Integer maxResults);

  String setTranslation(Long id, String translation) throws DefaultException;

}
