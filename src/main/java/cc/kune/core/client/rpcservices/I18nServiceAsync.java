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

import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.I18nTranslationDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface I18nServiceAsync.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface I18nServiceAsync {

  /**
   * Gets the initial language.
   * 
   * @param localeParam
   *          the locale param
   * @param callback
   *          the callback
   * @return the initial language
   */
  void getInitialLanguage(String localeParam, AsyncCallback<I18nLanguageDTO> callback);

  /**
   * Gets the lexicon.
   * 
   * @param language
   *          the language
   * @param callback
   *          the callback
   * @return the lexicon
   */
  void getLexicon(String language, AsyncCallback<HashMap<String, String>> callback);

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
   * @param callback
   *          the callback
   * @return the translated lexicon
   */
  void getTranslatedLexicon(String userHash, String language, String languageFrom, boolean toTranslate,
      AsyncCallback<List<I18nTranslationDTO>> callback);

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
   * @param callback
   *          the callback
   * @return the translation
   */
  void getTranslation(String userHash, String language, String text, String noteForTranslators,
      AsyncCallback<String> callback);

  /**
   * Sets the translation.
   * 
   * @param userHash
   *          the user hash
   * @param id
   *          the id
   * @param translation
   *          the translation
   * @param asyncCallback
   *          the async callback
   */
  void setTranslation(String userHash, Long id, String translation, AsyncCallback<String> asyncCallback);

}
