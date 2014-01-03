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

package cc.kune.core.server.utils;

import java.util.UnknownFormatConversionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.common.shared.utils.AbstractFormattedString;

// TODO: Auto-generated Javadoc
/**
 * The Class FormattedString.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FormattedString extends AbstractFormattedString {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(FormattedString.class);

  /**
   * Builds the.
   * 
   * @param shouldBeTranslated
   *          the should be translated
   * @param template
   *          the template
   * @param args
   *          the args
   * @return the formatted string
   */
  public static FormattedString build(final boolean shouldBeTranslated, final String template,
      final Object... args) {
    return new FormattedString(shouldBeTranslated, template, args);
  }

  /**
   * Builds with only a message without args.
   * 
   * @param plainMsg
   *          the plain msg
   * @return the formated string
   */
  public static FormattedString build(final String plainMsg) {
    return new FormattedString(plainMsg);
  }

  /**
   * Builds the.
   * 
   * @param template
   *          the template
   * @param args
   *          the args
   * @return the formated string
   */
  public static FormattedString build(final String template, final Object... args) {
    return new FormattedString(template, args);
  }

  /**
   * Instantiates a new formatted string.
   * 
   * @param shouldBeTranslated
   *          the should be translated
   * @param template
   *          the template
   * @param args
   *          the args
   */
  public FormattedString(final boolean shouldBeTranslated, final String template, final Object... args) {
    super(shouldBeTranslated, template, args);
  }

  /**
   * Instantiates a new formated string.
   * 
   * @param plainMsg
   *          the plain msg
   */
  public FormattedString(final String plainMsg) {
    this(true, plainMsg);
  }

  /**
   * Instantiates a new formated string.
   * 
   * @param template
   *          the template
   * @param args
   *          the args that will be formatted inside the template (%s, etc)
   */
  public FormattedString(final String template, final Object... args) {
    this(true, template, args);
  }

  /**
   * Copy.
   * 
   * @return the formatted string
   */
  public FormattedString copy() {
    return new FormattedString(shouldBeTranslated(), getTemplate(), getArgs());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.shared.utils.AbstractFormattedString#format(java.lang.String
   * , java.lang.Object[])
   */
  @Override
  public String format(final String template, final Object... args) {
    try {
      return String.format(template, args);
    } catch (final UnknownFormatConversionException e) {
      LOG.error("Formating error, template: " + template + ", args.length: " + args.length);
      throw e;
    }
  }

}
