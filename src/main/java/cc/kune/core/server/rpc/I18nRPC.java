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
package cc.kune.core.server.rpc;

import java.util.HashMap;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import cc.kune.common.shared.utils.I18nBasicUtils;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.rpcservices.I18nService;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.ShouldBeMember;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.server.mapper.KuneMapper;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.I18nTranslationDTO;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.I18nTranslation;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nRPC.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nRPC implements RPC, I18nService {

  /** The i18n translation manager. */
  private final I18nTranslationManager i18nTranslationManager;

  /** The language manager. */
  private final I18nLanguageManager languageManager;

  /** The mapper. */
  private final KuneMapper mapper;

  /** The request provider. */
  private final Provider<HttpServletRequest> requestProvider;

  /** The user session manager. */
  private final UserSessionManager userSessionManager;

  /**
   * Instantiates a new i18n rpc.
   * 
   * @param requestProvider
   *          the request provider
   * @param userSessionManager
   *          the user session manager
   * @param i18nTranslationManager
   *          the i18n translation manager
   * @param languageManager
   *          the language manager
   * @param mapper
   *          the mapper
   */
  @Inject
  public I18nRPC(final Provider<HttpServletRequest> requestProvider,
      final UserSessionManager userSessionManager, final I18nTranslationManager i18nTranslationManager,
      final I18nLanguageManager languageManager, final KuneMapper mapper) {
    this.requestProvider = requestProvider;
    this.userSessionManager = userSessionManager;
    this.i18nTranslationManager = i18nTranslationManager;
    this.languageManager = languageManager;
    this.mapper = mapper;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.I18nService#getInitialLanguage(java.lang
   * .String)
   */
  @Override
  @KuneTransactional
  public I18nLanguageDTO getInitialLanguage(final String localeParam) {
    String initLanguage;
    I18nLanguage lang;
    if (localeParam != null) {
      initLanguage = localeParam;
    } else {
      if (userSessionManager.isUserLoggedIn()) {
        initLanguage = userSessionManager.getUser().getLanguage().getCode();
      } else {
        final String browserLang = requestProvider.get().getLocale().getLanguage();
        if (browserLang != null) {
          // Not logged, use browser language if possible
          final String country = requestProvider.get().getLocale().getCountry();
          initLanguage = browserLang + (country != null ? "_" + country : "");
        } else {
          initLanguage = I18nTranslation.DEFAULT_LANG;
        }
      }
    }
    try {
      lang = languageManager.findByCode(I18nBasicUtils.javaLocaleNormalize(initLanguage));
    } catch (final NoResultException e) {
      lang = languageManager.findByCode(I18nTranslation.DEFAULT_LANG);
    }
    return mapper.map(lang, I18nLanguageDTO.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.I18nService#getLexicon(java.lang.String)
   */
  @Override
  @KuneTransactional
  public HashMap<String, String> getLexicon(final String language) {
    return i18nTranslationManager.getLexicon(language);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.I18nService#getTranslatedLexicon(java.lang
   * .String, java.lang.String, java.lang.String, boolean)
   */
  @Override
  @Authenticated
  @KuneTransactional
  public List<I18nTranslationDTO> getTranslatedLexicon(final String userHash, final String language,
      final String languageFrom, final boolean toTranslate) {
    if (toTranslate) {
      return i18nTranslationManager.getUntranslatedLexicon(language, languageFrom);
    } else {
      return i18nTranslationManager.getTranslatedLexicon(language, languageFrom);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.I18nService#getTranslation(java.lang.String
   * , java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  @KuneTransactional
  public String getTranslation(final String userHash, final String language, final String text,
      final String noteForTranslators) {
    String translation = null;
    try {
      translation = getTranslationWrapper(language, text, noteForTranslators);
    } catch (final SessionExpiredException e) {
    }
    return translation;
  }

  /**
   * Gets the translation wrapper.
   * 
   * @param language
   *          the language
   * @param text
   *          the text
   * @param noteForTranslators
   *          the note for translators
   * @return the translation wrapper
   */
  @Authenticated(mandatory = false)
  private String getTranslationWrapper(final String language, final String text,
      final String noteForTranslators) {
    return i18nTranslationManager.getTranslation(language, text, noteForTranslators);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.I18nService#setTranslation(java.lang.String
   * , java.lang.Long, java.lang.String)
   */
  @Override
  @Authenticated
  @KuneTransactional
  @ShouldBeMember(groupKuneProperty = KuneProperties.UI_TRANSLATOR_GROUP, rol = AccessRol.Editor)
  public String setTranslation(final String userHash, final Long id, final String translation)
      throws DefaultException {
    return i18nTranslationManager.setTranslation(id, translation);
  }

}
