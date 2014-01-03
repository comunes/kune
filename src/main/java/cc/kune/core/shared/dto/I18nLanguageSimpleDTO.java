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
package cc.kune.core.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nLanguageSimpleDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nLanguageSimpleDTO implements IsSerializable {

  /**
   * Creates the.
   * 
   * @param language
   *          the language
   * @return the i18n language simple dto
   */
  public static I18nLanguageSimpleDTO create(final I18nLanguageDTO language) {
    return new I18nLanguageSimpleDTO(language.getCode(), language.getEnglishName());
  }

  /** The code. */
  private String code;

  /** The english name. */
  private String englishName;

  /**
   * Instantiates a new i18n language simple dto.
   */
  public I18nLanguageSimpleDTO() {
  }

  /**
   * Instantiates a new i18n language simple dto.
   * 
   * @param code
   *          the code
   * @param englishName
   *          the english name
   */
  public I18nLanguageSimpleDTO(final String code, final String englishName) {
    this.code = code;
    this.englishName = englishName;
  }

  /**
   * Gets the code.
   * 
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Gets the english name.
   * 
   * @return the english name
   */
  public String getEnglishName() {
    return englishName;
  }

  /**
   * Sets the code.
   * 
   * @param code
   *          the new code
   */
  public void setCode(final String code) {
    this.code = code;
  }

  /**
   * Sets the english name.
   * 
   * @param englishName
   *          the new english name
   */
  public void setEnglishName(final String englishName) {
    this.englishName = englishName;
  }

}
