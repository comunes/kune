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
package cc.kune.common.shared.i18n;

import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class I18n.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class I18n {

  /** The i18n. */
  @Inject
  private static I18nTranslationService i18n;

  /**
   * Gets the direction.
   * 
   * @return the direction
   */
  public static Direction getDirection() {
    return i18n.getDirection();
  }

  public static String getSiteCommonName() {
    return i18n.getSiteCommonName();
  }

  /**
   * Checks if is rtl.
   * 
   * @return true, if is rtl
   */
  public static boolean isRTL() {
    return i18n.isRTL();
  }

  /**
   * T.
   * 
   * @param text
   *          the text
   * @return the string
   */
  public static String t(final String text) {
    return i18n.t(text);
  }

  /**
   * T.
   * 
   * @param text
   *          the text
   * @param args
   *          the args
   * @return the string
   */
  public static String t(final String text, final Integer... args) {
    return i18n.t(text, args);
  }

  /**
   * T.
   * 
   * @param text
   *          the text
   * @param args
   *          the args
   * @return the string
   */
  public static String t(final String text, final Long... args) {
    return i18n.t(text, args);
  }

  /**
   * T.
   * 
   * @param text
   *          the text
   * @param args
   *          the args
   * @return the string
   */
  public static String t(final String text, final String... args) {
    return i18n.t(text, args);
  }

  /**
   * T with nt.
   * 
   * @param text
   *          the text
   * @param noteForTranslators
   *          the note for translators
   * @return the string
   */
  public static String tWithNT(final String text, final String noteForTranslators) {
    return i18n.tWithNT(text, noteForTranslators);
  }

  /**
   * T with nt.
   * 
   * @param text
   *          the text
   * @param noteForTranslators
   *          the note for translators
   * @param args
   *          the args
   * @return the string
   */
  public static String tWithNT(final String text, final String noteForTranslators, final Integer... args) {
    return i18n.tWithNT(text, noteForTranslators, args);
  }

  /**
   * T with nt.
   * 
   * @param text
   *          the text
   * @param noteForTranslators
   *          the note for translators
   * @param args
   *          the args
   * @return the string
   */
  public static String tWithNT(final String text, final String noteForTranslators, final Long... args) {
    return i18n.tWithNT(text, noteForTranslators, args);
  }

  /**
   * T with nt.
   * 
   * @param text
   *          the text
   * @param noteForTranslators
   *          the note for translators
   * @param args
   *          the args
   * @return the string
   */
  public static String tWithNT(final String text, final String noteForTranslators, final String... args) {
    return i18n.tWithNT(text, noteForTranslators, args);
  }
}
