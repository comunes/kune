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

package cc.kune.common.client.utils;

import cc.kune.common.shared.utils.AbstractFormattedString;

import com.google.gwt.core.client.GWT;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;

public class ClientFormattedString extends AbstractFormattedString {

  public static ClientFormattedString build(final boolean shouldBeTranslated, final String template,
      final Object... args) {
    return new ClientFormattedString(shouldBeTranslated, template, args);
  }

  /**
   * Builds with only a message without args.
   * 
   * @param plainMsg
   *          the plain msg
   * @return the formated string
   */
  public static ClientFormattedString build(final String plainMsg) {
    return new ClientFormattedString(plainMsg);
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
  public static ClientFormattedString build(final String template, final Object... args) {
    return new ClientFormattedString(template, args);
  }

  public ClientFormattedString(final boolean shouldBeTranslated, final String template,
      final Object... args) {
    super(shouldBeTranslated, template, args);
  }

  /**
   * Instantiates a new formated string.
   * 
   * @param plainMsg
   *          the plain msg
   */
  public ClientFormattedString(final String plainMsg) {
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
  public ClientFormattedString(final String template, final Object... args) {
    this(true, template, args);
  }

  public ClientFormattedString copy() {
    return new ClientFormattedString(shouldBeTranslated(), getTemplate(), getArgs());
  }

  /**
   * String format based in
   * http://stackoverflow.com/questions/3126232/string-formatter-in-gwt
   * 
   * @param format
   *          the format
   * @param args
   *          the args
   * @return the string
   */
  @Override
  public String format(final String format, final Object... args) {
    final RegExp regex = RegExp.compile("%[a-z]");
    final SplitResult split = regex.split(format);
    final StringBuffer msg = new StringBuffer();
    for (int pos = 0; pos < split.length() - 1; pos += 1) {
      msg.append(split.get(pos));
      msg.append(args[pos].toString());
    }
    msg.append(split.get(split.length() - 1));
    GWT.log("FORMATTER: " + msg.toString());
    return msg.toString();
  }

}
