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
package cc.kune.core.server.i18n.impl;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.Pair;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.i18n.I18nTranslationServiceMultiLang;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.I18nTranslation;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nTranslationServiceDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class I18nTranslationServiceDefault extends I18nTranslationService implements
    I18nTranslationServiceMultiLang {

  /** The initiliazied. */
  private boolean initiliazied = false;

  /** The language manager. */
  private final I18nLanguageManager languageManager;

  private final String siteName;

  /** The translation manager. */
  private final I18nTranslationManager translationManager;

  /** The user session manager. */
  private final Provider<UserSessionManager> userSessionManager;

  /**
   * Instantiates a new i18n translation service default.
   * 
   * @param translationManager
   *          the translation manager
   * @param userSessionManager
   *          the user session manager
   * @param languageManager
   *          the language manager
   */
  @Inject
  public I18nTranslationServiceDefault(final I18nTranslationManager translationManager,
      final Provider<UserSessionManager> userSessionManager, final I18nLanguageManager languageManager,
      final KuneProperties kuneProp) {
    this.translationManager = translationManager;
    this.userSessionManager = userSessionManager;
    this.languageManager = languageManager;
    this.siteName = kuneProp.get(KuneProperties.SITE_COMMON_NAME);
  }

  /**
   * Def lang.
   * 
   * @return the i18n language
   */
  private I18nLanguage defLang() {
    return languageManager.findByCode(I18nTranslation.DEFAULT_LANG);
  }

  @Override
  public String getSiteCommonName() {
    return siteName;
  }

  /**
   * Inits the.
   */
  public void init() {
    initiliazied = true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.shared.i18n.I18nTranslationService#isRTL()
   */
  @Override
  public boolean isRTL() {
    return userSessionManager.get().isUserLoggedIn() ? userSessionManager.get().getUser().getLanguage().getDirection().equals(
        RTL)
        : false;
  }

  /**
   * Use [%s] to reference the string parameter.
   * 
   * @param lang
   *          the lang
   * @param pair
   *          the pair
   * @param args
   *          the args
   * @return the string
   */
  // @PMD:REVIEWED:ShortMethodName: by vjrj on 21/05/09 13:50
  private String t(final I18nLanguage lang, final Pair<String, String> pair, final String... args) {
    String translation = tWithNT(lang, pair.getLeft(), pair.getRight());
    for (final String arg : args) {
      translation = translation.replaceFirst("\\[%s\\]", arg);
    }
    return decodeHtml(translation);
  }

  /**
   * If the text is not in the db, it stores the text pending for translation.
   * 
   * Warning: text is escaped as html before insert in the db. Don't use html
   * here (o user this method with params).
   * 
   * @param text
   *          the text
   * @return text translated in the current language
   */
  @Override
  public String t(final String text) {
    return tWithNT(text, "");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.i18n.I18nTranslationServiceMultiLang#tWithNT(cc.kune
   * .domain.I18nLanguage, java.lang.String, java.lang.String)
   */
  @Override
  public String tWithNT(final I18nLanguage language, final String text, final String noteForTranslators) {
    final String encodeText = TextUtils.escapeHtmlLight(text);
    String translation = translationManager.getTranslation(language.getCode(), text, noteForTranslators);
    if (translation == UNTRANSLATED_VALUE) {
      // Not translated but in db, return text
      translation = encodeText;
    }
    return decodeHtml(translation);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.i18n.I18nTranslationServiceMultiLang#tWithNT(cc.kune
   * .domain.I18nLanguage, java.lang.String, java.lang.String,
   * java.lang.String[])
   */
  @Override
  public String tWithNT(final I18nLanguage lang, final String text, final String noteForTranslators,
      final String... args) {
    return t(lang, Pair.create(text, noteForTranslators), args);
  }

  /**
   * If the text is not in the db, it stores the text pending for translation.
   * 
   * Warning: text is escaped as html before insert in the db. Don't use html
   * here (o user this method with params).
   * 
   * @param text
   *          the text
   * @param noteForTranslators
   *          some note for facilitate the translation
   * @return text translated in the current language
   */
  @Override
  public String tWithNT(final String text, final String noteForTranslators) {
    I18nLanguage language;
    if (initiliazied && userSessionManager.get().isUserLoggedIn()) {
      language = userSessionManager.get().getUser().getLanguage();
    } else {
      language = defLang();
    }
    return tWithNT(language, text, noteForTranslators);
  }
}
