/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.shared.dto.I18nTranslationDTO;
import cc.kune.core.shared.dto.SearchResultDTO;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.I18nTranslation;
import cc.kune.domain.finders.I18nTranslationFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class I18nTranslationManagerDefault extends DefaultManager<I18nTranslation, Long> implements
    I18nTranslationManager {

  private I18nLanguage defLang;
  private final I18nTranslationFinder finder;
  private final ConcurrentHashMap<String, HashMap<String, String>> langCache;
  private final I18nLanguageManager languageManager;

  @Inject
  public I18nTranslationManagerDefault(final Provider<EntityManager> provider,
      final I18nTranslationFinder finder, final I18nLanguageManager languageManager) {
    super(provider, I18nTranslation.class);
    this.finder = finder;
    this.languageManager = languageManager;
    langCache = new ConcurrentHashMap<String, HashMap<String, String>>();
  }

  private I18nLanguage defLang() {
    if (defLang == null) {
      defLang = languageManager.findByCode(I18nTranslation.DEFAULT_LANG);
    }
    return defLang;
  }

  private I18nLanguage getLanguage(final String languageId) {
    return languageManager.findByCode(languageId);
  }

  @Override
  public HashMap<String, String> getLexicon(final String languageId) {
    getLanguage(languageId);
    HashMap<String, String> map = langCache.get(languageId);
    if (map == null) {
      map = getLexiconFromDb(languageId);
    }
    return map;
  }

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

  @Override
  public List<I18nTranslationDTO> getTranslatedLexicon(final String languageCode) {
    return finder.getTranslatedLexicon(getLanguage(languageCode));
  }

  @Override
  public SearchResultDTO<I18nTranslationDTO> getTranslatedLexicon(final String languageCode,
      final Integer firstResult, final Integer maxResults) {
    final I18nLanguage language = getLanguage(languageCode);
    final List<I18nTranslationDTO> list = finder.getTranslatedLexicon(language, firstResult, maxResults);
    return new SearchResultDTO<I18nTranslationDTO>(list.size(), list);
  }

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
      if (finder.findCount(defLang().getCode(), text) == 0) {
        final I18nTranslation newTranslation = new I18nTranslation("", null,
            I18nTranslation.DEF_PLUR_INDEX, "", I18nTranslation.UNTRANSLATED_VALUE, escapedText,
            I18nTranslation.DEF_NAMESPACE, defLang(), null, noteForTranslators);
        try {
          persist(newTranslation);
          langCache.clear();
        } catch (final Exception e) {
          // TODO: handle exception
          throw new RuntimeException("Error persisting '" + text + "' to language '" + language + "'");
        }
      }
      return I18nTranslation.UNTRANSLATED_VALUE;
    }
  }

  @Override
  public List<I18nTranslationDTO> getUntranslatedLexicon(final String languageCode) {
    return finder.getUntranslatedLexicon(initUnstranlated(languageCode));
  }

  @Override
  public SearchResultDTO<I18nTranslationDTO> getUntranslatedLexicon(final String languageCode,
      final Integer firstResult, final Integer maxResults) {
    final I18nLanguage language = initUnstranlated(languageCode);
    final List<I18nTranslationDTO> list = finder.getUnstranslatedLexicon(language, firstResult,
        maxResults);
    return new SearchResultDTO<I18nTranslationDTO>(list.size(), list);
  }

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
