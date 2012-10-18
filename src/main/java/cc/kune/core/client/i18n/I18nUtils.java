/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.i18n;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;
import cc.kune.common.shared.utils.TextUtils;

/**
 * Copied some methods from org.apache.commons.lang.WordUtils
 * 
 */
public class I18nUtils {

  public static String convertMethodName(final String name) {
    String result = name;
    result = result.replaceAll("\\\n", " ");
    result = result.replaceAll("\\[%d\\]", "N");
    result = result.replaceAll("\\[%s\\]", "Param");
    final char[] delimiters = { ' ', '.', '?', ',', ';', '&', '(', ')', '"', '$', '!', '/', '\'', '-',
        '%', ':', '{', '}', '[', ']', '©', '«', '»' };
    result = TextUtils.capitalizeFully(result, delimiters);
    // result = result.replaceAll("[ \\.\\?,;&\\(\\)\"\\$!\\/\\'\\-%:{}\\[\\]]",
    // "");
    result = result.replaceAll("[^a-zA-Z0-9]", "");
    if (result.isEmpty()) {
      return "";
    }
    result = result.replaceFirst("^([0-9])", "_$1");
    try {
      return TextUtils.abbreviate(
          new StringBuffer(result.length()).append(Character.toLowerCase(result.charAt(0))).append(
              result.substring(1)).toString(), 0, 100, "");
    } catch (final Exception e) {
      final String message = "Error trying to get i18n func-name of: " + name;
      Log.error(message);
      throw new UIException(message);
    }

  }

}