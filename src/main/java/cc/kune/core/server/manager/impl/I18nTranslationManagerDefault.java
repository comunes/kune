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
package cc.kune.core.server.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.shared.dto.I18nTranslationDTO;
import cc.kune.core.shared.dto.SearchResultDTO;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.I18nTranslation;
import cc.kune.domain.finders.I18nTranslationFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nTranslationManagerDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class I18nTranslationManagerDefault extends DefaultManager<I18nTranslation, Long> implements
    I18nTranslationManager {

  /** The finder. */
  private final I18nTranslationFinder finder;

  /** The lang cache. */
  private final ConcurrentHashMap<String, HashMap<String, String>> langCache;

  /** The language manager. */
  private final I18nLanguageManager languageManager;

  /**
   * Instantiates a new i18n translation manager default.
   * 
   * @param provider
   *          the provider
   * @param finder
   *          the finder
   * @param languageManager
   *          the language manager
   */
  @Inject
  public I18nTranslationManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final I18nTranslationFinder finder, final I18nLanguageManager languageManager) {
    super(provider, I18nTranslation.class);
    this.finder = finder;
    this.languageManager = languageManager;
    langCache = new ConcurrentHashMap<String, HashMap<String, String>>();
  }

  /**
   * Def lang.
   * 
   * @return the i18n language
   */
  private I18nLanguage defLang() {
    return languageManager.getDefaultLanguage();
  }

  /**
   * Gets the language.
   * 
   * @param languageId
   *          the language id
   * @return the language
   */
  private I18nLanguage getLanguage(final String languageId) {
    return languageManager.findByCode(languageId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.I18nTranslationManager#getLexicon(java.lang
   * .String)
   */
  @Override
  public HashMap<String, String> getLexicon(final String languageId) {
    getLanguage(languageId);
    HashMap<String, String> map = langCache.get(languageId);
    if (map == null) {
      map = getLexiconFromDb(languageId);
    }
    return map;
  }

  /**
   * Gets the lexicon from db.
   * 
   * @param language
   *          the language
   * @return the lexicon from db
   */
  @SuppressWarnings("unchecked")
  private HashMap<String, String> getLexiconFromDb(final String language) {
    // FIXME
    // 1) Generate English
    // 2) Don't permit to translate English
    HashMap<String, String> map = new HashMap<String, String>();

    final List<I18nTranslation> set = finder.findByLanguage(language);
    if (!language.equals(I18nTranslation.DEFAULT_LANG)) {
      map = (HashMap<String, String>) getLexicon(I18nTranslation.DEFAULT_LANG).clone();
    }
    for (final I18nTranslation trans : set) {
      map.put(trans.getTrKey(), trans.getText());
    }
    langCache.put(language, map);
    return map;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.I18nTranslationManager#getTranslatedLexicon
   * (java.lang.String, java.lang.String)
   */
  @Override
  public List<I18nTranslationDTO> getTranslatedLexicon(final String languageCode,
      final String languageFrom) {
    if (languageFrom == null) {
      return finder.getTranslatedLexicon(getLanguage(languageCode));
    } else {
      return finder.getTranslatedLexiconWithFrom(getLanguage(languageCode), getLanguage(languageFrom));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.I18nTranslationManager#getTranslatedLexicon
   * (java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
   */
  @Override
  public SearchResultDTO<I18nTranslationDTO> getTranslatedLexicon(final String languageCode,
      final String languageCodeFrom, final Integer firstResult, final Integer maxResults) {
    final I18nLanguage language = getLanguage(languageCode);
    List<I18nTranslationDTO> list;
    if (languageCodeFrom == null) {
      list = finder.getTranslatedLexicon(language, firstResult, maxResults);
    } else {
      final I18nLanguage languageFrom = getLanguage(languageCodeFrom);
      list = finder.getTranslatedLexiconWithFrom(language, languageFrom, firstResult, maxResults);
    }
    return new SearchResultDTO<I18nTranslationDTO>(list.size(), list);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.I18nTranslationManager#getTranslation(java.
   * lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public String getTranslation(final String language, final String text, final String noteForTranslators) {
    if (TextUtils.empty(text)) {
      return text;
    }
    final HashMap<String, String> lexicon = getLexicon(language);
    final String escapedText = TextUtils.escapeHtmlLight(text);
    if (lexicon.containsKey(escapedText)) {
      final String translation = lexicon.get(escapedText);
      return translation;
    } else {
      // new key, add to language and default language and let
      // untranslated
      if (finder.findCount(defLang().getCode(), escapedText) == 0) {
        final I18nTranslation newTranslation = new I18nTranslation("", null,
            I18nTranslation.DEF_PLUR_INDEX, "", I18nTranslation.UNTRANSLATED_VALUE, escapedText,
            I18nTranslation.DEF_NAMESPACE, defLang(), null, noteForTranslators);
        try {
          persist(newTranslation);
          langCache.clear();
        } catch (final Exception e) {
          // TODO: handle exception
          throw new RuntimeException("Error persisting '" + text + "' to language '" + language + "'", e);
        }
      }
      return I18nTranslation.UNTRANSLATED_VALUE;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.I18nTranslationManager#getUntranslatedLexicon
   * (java.lang.String, java.lang.String)
   */
  @Override
  public List<I18nTranslationDTO> getUntranslatedLexicon(final String languageCode,
      final String languageFrom) {
    final I18nLanguage language = initUnstranlated(languageCode);
    if (languageFrom == null) {
      return finder.getUntranslatedLexicon(language);
    } else {
      return finder.getUntranslatedLexiconWithFrom(language, getLanguage(languageFrom));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.I18nTranslationManager#getUntranslatedLexicon
   * (java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
   */
  @Override
  public SearchResultDTO<I18nTranslationDTO> getUntranslatedLexicon(final String languageCode,
      final String languageCodeFrom, final Integer firstResult, final Integer maxResults) {
    final I18nLanguage language = initUnstranlated(languageCode);
    List<I18nTranslationDTO> list;
    if (languageCodeFrom == null) {
      list = finder.getUnstranslatedLexicon(language, firstResult, maxResults);
    } else {
      final I18nLanguage languageFrom = getLanguage(languageCodeFrom);
      list = finder.getUntranslatedLexiconWithFrom(language, languageFrom, firstResult, maxResults);
    }
    return new SearchResultDTO<I18nTranslationDTO>(list.size(), list);
  }

  /**
   * Inits the unstranlated.
   * 
   * @param languageCode
   *          the language code
   * @return the i18n language
   */
  private I18nLanguage initUnstranlated(final String languageCode) {
    final I18nLanguage defLanguage = defLang();
    I18nLanguage language;
    if (languageCode.equals(I18nTranslation.DEFAULT_LANG)) {
      language = defLanguage;
    } else {
      language = getLanguage(languageCode);
      final List<I18nTranslation> list = finder.getNonExistentFromDefault(defLanguage, language);
      for (final I18nTranslation defTrans : list) {
        final I18nTranslation newTrans = defTrans.cloneForNewLanguage();
        newTrans.setLanguage(language);
        persist(newTrans);
      }
    }
    return language;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.I18nTranslationManager#setTranslation(java.
   * lang.Long, java.lang.String)
   */
  @Override
  public String setTranslation(final Long id, final String translation) throws DefaultException {
    final I18nTranslation trans = super.find(id);
    if (trans != null) {
      // Don't permit to translate the def language
      final String lang = trans.getLanguage().getCode();
      assert lang != I18nTranslation.DEFAULT_LANG;
      final String escapedTranslation = TextUtils.escapeHtmlLight(translation);
      trans.setText(escapedTranslation);
      persist(trans);
      // reset cache for this lang
      langCache.remove(lang);
      return escapedTranslation;
    } else {
      throw new DefaultException("Trying to translate a unknown item");
    }
  }

}
