/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

package cc.kune.core.server.utils;

import cc.kune.common.shared.utils.AbstractFormattedString;

public class FormattedString extends AbstractFormattedString {

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

  public FormattedString copy() {
    return new FormattedString(shouldBeTranslated(), getTemplate(), getArgs());
  }

  @Override
  public String format(final String template, final Object... args) {
    return String.format(template, args);
  }

}
