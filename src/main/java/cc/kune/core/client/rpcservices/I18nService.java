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
package cc.kune.core.client.rpcservices;

import java.util.HashMap;
import java.util.List;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.I18nTranslationDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// TODO: Auto-generated Javadoc
/**
 * The Interface I18nService.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@RemoteServiceRelativePath("I18nService")
public interface I18nService extends RemoteService {

  /**
   * Gets the initial language.
   * 
   * @param localeParam
   *          the locale param
   * @return the initial language
   */
  I18nLanguageDTO getInitialLanguage(String localeParam);

  /**
   * Gets the lexicon.
   * 
   * @param language
   *          the language
   * @return the lexicon
   */
  HashMap<String, String> getLexicon(String language);

  /**
   * Gets the translated lexicon.
   * 
   * @param userHash
   *          the user hash
   * @param language
   *          the language
   * @param languageFrom
   *          the language from
   * @param toTranslate
   *          the to translate
   * @return the translated lexicon
   */
  List<I18nTranslationDTO> getTranslatedLexicon(String userHash, String language, String languageFrom,
      boolean toTranslate);

  /**
   * Gets the translation.
   * 
   * @param userHash
   *          the user hash
   * @param language
   *          the language
   * @param text
   *          the text
   * @param noteForTranslators
   *          the note for translators
   * @return the translation
   */
  String getTranslation(String userHash, String language, String text, String noteForTranslators);

  /**
   * Sets the translation.
   * 
   * @param userHash
   *          the user hash
   * @param id
   *          the id
   * @param translation
   *          the translation
   * @return the string
   * @throws DefaultException
   *           the default exception
   */
  String setTranslation(String userHash, Long id, String translation) throws DefaultException;

}
