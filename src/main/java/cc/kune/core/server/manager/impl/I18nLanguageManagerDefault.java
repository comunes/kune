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

import java.util.List;

import javax.persistence.EntityManager;

import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.I18nTranslation;
import cc.kune.domain.finders.I18nLanguageFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nLanguageManagerDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class I18nLanguageManagerDefault extends DefaultManager<I18nLanguage, Long> implements
    I18nLanguageManager {

  /** The finder. */
  private final I18nLanguageFinder finder;

  /**
   * Instantiates a new i18n language manager default.
   * 
   * @param provider
   *          the provider
   * @param finder
   *          the finder
   */
  @Inject
  public I18nLanguageManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final I18nLanguageFinder finder) {
    super(provider, I18nLanguage.class);
    this.finder = finder;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.I18nLanguageManager#findByCode(java.lang.String
   * )
   */
  @Override
  public I18nLanguage findByCode(final String language) {
    return finder.findByCode(language);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.I18nLanguageManager#findByCodes(java.util.List)
   */
  @Override
  public List<I18nLanguage> findByCodes(final List<String> languages) {
    return finder.findByCodes(languages);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.I18nLanguageManager#getAll()
   */
  @Override
  public List<I18nLanguage> getAll() {
    return finder.getAll();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.I18nLanguageManager#getDefaultLanguage()
   */
  @Override
  public I18nLanguage getDefaultLanguage() {
    return findByCode(I18nTranslation.DEFAULT_LANG);
  }
}
