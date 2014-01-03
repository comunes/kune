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
package cc.kune.domain.finders;

import java.util.ArrayList;
import java.util.List;

import cc.kune.domain.I18nLanguage;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

// TODO: Auto-generated Javadoc
/**
 * The Interface I18nLanguageFinder.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface I18nLanguageFinder {

  /**
   * Find by code.
   * 
   * @param language
   *          the language
   * @return the i18n language
   */
  @Finder(query = "FROM I18nLanguage l WHERE code = :language")
  public I18nLanguage findByCode(@Named("language") final String language);

  /**
   * Find by codes.
   * 
   * @param languages
   *          the languages
   * @return the list
   */
  @Finder(query = "FROM I18nLanguage l WHERE code in (:languages)", returnAs = ArrayList.class)
  public List<I18nLanguage> findByCodes(@Named("languages") final List<String> languages);

  /**
   * Gets the all.
   * 
   * @return the all
   */
  @Finder(query = "FROM I18nLanguage ORDER BY englishName", returnAs = ArrayList.class)
  public List<I18nLanguage> getAll();

}
