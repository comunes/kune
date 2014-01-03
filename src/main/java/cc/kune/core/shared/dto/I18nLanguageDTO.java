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
 * The Class I18nLanguageDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nLanguageDTO implements IsSerializable {

  /** The code. */
  private String code;

  /** The date format. */
  private String dateFormat;

  /** The date format short. */
  private String dateFormatShort;

  /** The direction. */
  private String direction;

  /** The english name. */
  private String englishName;

  /** The native name. */
  private String nativeName;

  /** The pluralization. */
  private String pluralization;

  /**
   * Gets the code.
   * 
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Gets the date format.
   * 
   * @return the date format
   */
  public String getDateFormat() {
    return dateFormat;
  }

  /**
   * Gets the date format short.
   * 
   * @return the date format short
   */
  public String getDateFormatShort() {
    return dateFormatShort;
  }

  /**
   * Gets the direction.
   * 
   * @return the direction
   */
  public String getDirection() {
    return direction;
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
   * Gets the native name.
   * 
   * @return the native name
   */
  public String getNativeName() {
    return nativeName;
  }

  /**
   * Gets the pluralization.
   * 
   * @return the pluralization
   */
  public String getPluralization() {
    return pluralization;
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
   * Sets the date format.
   * 
   * @param dateFormat
   *          the new date format
   */
  public void setDateFormat(final String dateFormat) {
    this.dateFormat = dateFormat;
  }

  /**
   * Sets the date format short.
   * 
   * @param dateFormatShort
   *          the new date format short
   */
  public void setDateFormatShort(final String dateFormatShort) {
    this.dateFormatShort = dateFormatShort;
  }

  /**
   * Sets the direction.
   * 
   * @param direction
   *          the new direction
   */
  public void setDirection(final String direction) {
    this.direction = direction;
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

  /**
   * Sets the native name.
   * 
   * @param nativeName
   *          the new native name
   */
  public void setNativeName(final String nativeName) {
    this.nativeName = nativeName;
  }

  /**
   * Sets the pluralization.
   * 
   * @param pluralization
   *          the new pluralization
   */
  public void setPluralization(final String pluralization) {
    this.pluralization = pluralization;
  }

}
