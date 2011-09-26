/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import java.util.ArrayList;

import org.apache.commons.lang.WordUtils;

import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.common.client.errors.UIException;

import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.safehtml.shared.UriUtils;

public class TextUtils {

  // Original regexp from http://www.regular-expressions.info/email.html
  public static final String EMAIL_REGEXP = "[-!#$%&\'*+/=?_`{|}~a-z0-9^]+(\\.[-!#$%&\'*+/=?_`{|}~a-z0-9^]+)*@(localhost|([a-z0-9]([-a-z0-9]*[a-z0-9])?\\.)+[a-z0-9]([-a-z0-9]*[a-z0-9]))?";
  public static final String IN_DEVELOPMENT = "In development";

  public static final String IN_DEVELOPMENT_P = " (in development)";

  public static final String NUM_REGEXP = "^[0-9]+$";

  public static final String UNIX_NAME = "^[a-z0-9_\\-]+$";

  // Original regexp from http://snippets.dzone.com/posts/show/452
  public static final String URL_REGEXP = "((ftp|http|https|mailto):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?)";

  /**
   * @see WordUtils#abbreviate
   */
  public static String abbreviate(final String str, int lower, int upper, final String appendToEnd) {
    // initial parameter checks
    if (str == null) {
      return null;
    }
    if (str.length() == 0) {
      return "";
    }

    // if the lower value is greater than the length of the string,
    // set to the length of the string
    if (lower > str.length()) {
      lower = str.length();
    }
    // if the upper value is -1 (i.e. no limit) or is greater
    // than the length of the string, set to the length of the string
    if (upper == -1 || upper > str.length()) {
      upper = str.length();
    }
    // if upper is less than lower, raise it to lower
    if (upper < lower) {
      upper = lower;
    }

    final StringBuffer result = new StringBuffer();
    final int index = str.indexOf(" ", lower);
    if (index == -1) {
      result.append(str.substring(0, upper));
      // only if abbreviation has occured do we append the appendToEnd value
      if (upper != str.length()) {
        result.append(defaultString(appendToEnd));
      }
    } else if (index > upper) {
      result.append(str.substring(0, upper));
      result.append(defaultString(appendToEnd));
    } else {
      result.append(str.substring(0, index));
      result.append(defaultString(appendToEnd));
    }
    return result.toString();
  }

  public static String br() {
    return "<br/>";
  }

  public static String brbr() {
    return "<br/><br/>";
  }

  /**
   * @see WordUtils#capitalize(String, char[])
   * @return
   */
  public static String capitalize(final String str, final char[] delimiters) {
    final int delimLen = (delimiters == null ? -1 : delimiters.length);
    if (str == null || str.length() == 0 || delimLen == 0) {
      return str;
    }
    final int strLen = str.length();
    final StringBuffer buffer = new StringBuffer(strLen);
    boolean capitalizeNext = true;
    for (int i = 0; i < strLen; i++) {
      final char ch = str.charAt(i);

      if (isDelimiter(ch, delimiters)) {
        buffer.append(ch);
        capitalizeNext = true;
      } else if (capitalizeNext) {
        // Original:in WordUtils buffer.append(Character.toTitleCase(ch));
        buffer.append(String.valueOf(ch).toUpperCase());
        capitalizeNext = false;
      } else {
        buffer.append(ch);
      }
    }
    return buffer.toString();
  }

  /**
   * @see WordUtils#capitalizeFully(String)
   */
  public static String capitalizeFully(String str, final char[] delimiters) {
    final int delimLen = (delimiters == null ? -1 : delimiters.length);
    if (str == null || str.length() == 0 || delimLen == 0) {
      return str;
    }
    str = str.toLowerCase();
    return capitalize(str, delimiters);
  }

  private static String defaultString(final String str) {
    return str == null ? "" : str;
  }

  public static String ellipsis(final String text, final int length) {
    return text == null ? "" : length <= 0 ? text : text.length() > length ? text.substring(0,
        length - 3) + "..." : text;
  }

  public static boolean empty(final String string) {
    return !notEmpty(string);
  }

  /**
   * This method escape only some dangerous html chars
   * 
   * Try to use {@link SimpleHtmlSanitizer} better
   * 
   */
  @Deprecated
  public static String escapeHtmlLight(final String source) {
    String result = source;
    if (source != null) {
      result = result.replaceAll("&", "&amp;");
      result = result.replaceAll("\"", "&quot;");
      // text = text.replaceAll("\'", "&#039;");
      result = result.replaceAll("<", "&lt;");
      result = result.replaceAll(">", "&gt;");
      result = result.replaceAll("—", "&#8212;");
    }
    return result;
  }

  /**
   * Generates a href link
   * 
   */
  public static String generateHtmlLink(final String href, final String text) {
    return generateHtmlLink(href, text, true);
  }

  /**
   * Generates a href link
   * 
   */
  public static String generateHtmlLink(final String href, final String text, final boolean targetBlank) {
    if (!UriUtils.isSafeUri(href)) {
      throw new UIException("Unsafe href");
    }
    return "<a href=\"" + UriUtils.sanitizeUri(href) + "\"" + (targetBlank ? "target=\"_blank\"" : "")
        + ">" + text + "</a>";
  }

  private static boolean isDelimiter(final char ch, final char[] delimiters) {
    if (delimiters == null) {
      throw new NotImplementedException();
      // return Character.isWhitespace(ch);
    }
    for (final char delimiter : delimiters) {
      if (ch == delimiter) {
        return true;
      }
    }
    return false;
  }

  public static boolean notEmpty(final String string) {
    return string != null && string.length() > 0;
  }

  public static String removeLastSlash(final String text) {
    return text.replaceFirst("/$", "");
  }

  public static ArrayList<String> splitTags(final String tagsString) {
    final ArrayList<String> tagsList = new ArrayList<String>();
    String tagsCopy = tagsString;
    // remove commas and quotes
    if (tagsString == null) {
      return tagsList;
    }
    tagsCopy = tagsCopy.replaceAll(",", " ");
    tagsCopy = tagsCopy.replaceAll("\"", "");
    tagsCopy = tagsCopy.replaceAll("\'", "");
    final String[] splitted = tagsCopy.split("\\s+");
    for (String tag : splitted) {
      tag = tag.replaceAll("\\s$", "");
      if (tag.length() > 0) {
        tagsList.add(tag);
      }
    }
    return tagsList;
  }

  /*
   * This method unescape only some dangerous html chars for use in GWT Html
   * widget for instance
   */
  public static String unescape(final String source) {
    if (source == null) {
      return null;
    }
    String result = source;
    result = result.replaceAll("&amp;", "&");
    result = result.replaceAll("&quot;", "\"");
    result = result.replaceAll("&#039;", "\'");
    result = result.replaceAll("&lt;", "<");
    result = result.replaceAll("&gt;", ">");
    result = result.replaceAll("&#8212;", "—");
    return result;
  }

  public TextUtils() {
  }
}
