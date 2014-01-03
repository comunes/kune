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
package cc.kune.common.shared.utils;

import java.util.ArrayList;

import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.common.client.errors.UIException;

import com.google.gwt.safehtml.shared.UriUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class TextUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TextUtils {

  // Original regexp from http://www.regular-expressions.info/email.html
  /** The Constant EMAIL_REGEXP. */
  public static final String EMAIL_REGEXP = "[-!#$%&\'*+/=?_`{|}~a-z0-9^]+(\\.[-!#$%&\'*+/=?_`{|}~a-z0-9^]+)*@(localhost|([a-z0-9]([-a-z0-9]*[a-z0-9])?\\.)+[a-z0-9]([-a-z0-9]*[a-z0-9]))?";

  /** The Constant EMAIL_REGEXP_LIST. */
  public static final String EMAIL_REGEXP_LIST = "(" + EMAIL_REGEXP + "|,| |\\s)+";

  /** The Constant IN_DEVELOPMENT. */
  public static final String IN_DEVELOPMENT = "In development";

  /** The Constant IN_DEVELOPMENT_P. */
  public static final String IN_DEVELOPMENT_P = " (in development)";

  /** The Constant NUM_REGEXP. */
  public static final String NUM_REGEXP = "^[0-9]+$";

  /**
   * Contains a list of all the characters that map one to one for UNICODE.
   */
  private static final String PLAIN_ASCII = "AaEeIiOoUu" // grave
      + "AaEeIiOoUuYy" // acute
      + "AaEeIiOoUuYy" // circumflex
      + "AaEeIiOoUuYy" // tilde
      + "AaEeIiOoUuYy" // umlaut
      + "Aa" // ring
      + "Cc" // cedilla
      + "Nn" // n tilde (spanish)
  ;

  /** The Constant SHORTNAME_REGEXP. */
  public static final String SHORTNAME_REGEXP = "^[a-z0-9]+$";

  /** The Constant SHORTNAME_UPPER_REGEXP. */
  public static final String SHORTNAME_UPPER_REGEXP = "^[a-zA-Z0-9]+$";

  /** Actual accented values, corresponds one to one with ASCII. */
  private static final String UNICODE = "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9"
      + "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD"
      + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
      + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
      + "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF" + "\u00C5\u00E5"
      + "\u00C7\u00E7" + "\u00D1\u00F1";

  /** The Constant UNIX_NAME. */
  public static final String UNIX_NAME = "^[a-z0-9_\\-]+$";

  // Original regexp from http://snippets.dzone.com/posts/show/452
  /** The Constant URL_REGEXP. */
  public static final String URL_REGEXP = "((ftp|http|https|mailto):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?)";

  /**
   * see WordUtils#abbreviate.
   * 
   * @param str
   *          the str
   * @param lower
   *          the lower
   * @param upper
   *          the upper
   * @param appendToEnd
   *          the append to end
   * @return the string
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

  /**
   * Br.
   * 
   * @return the string
   */
  public static String br() {
    return "<br/>";
  }

  /**
   * Brbr.
   * 
   * @return the string
   */
  public static String brbr() {
    return "<br/><br/>";
  }

  /**
   * see WordUtils#capitalize(String, char[]).
   * 
   * @param str
   *          the str
   * @param delimiters
   *          the delimiters
   * @return the string
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
   * see WordUtils#capitalizeFully(String).
   * 
   * @param str
   *          the str
   * @param delimiters
   *          the delimiters
   * @return the string
   */
  public static String capitalizeFully(String str, final char[] delimiters) {
    final int delimLen = (delimiters == null ? -1 : delimiters.length);
    if (str == null || str.length() == 0 || delimLen == 0) {
      return str;
    }
    str = str.toLowerCase();
    return capitalize(str, delimiters);
  }

  /**
   * Removes accents from a string and replace it with ASCII equivalent (á =>
   * a).
   * 
   * @param s
   *          The string to englishify
   * @return The string without the accents.
   */
  public static String deAccent(final String s) {
    final StringBuilder b = new StringBuilder();
    final int n = s.length();
    for (int i = 0; i < n; i++) {
      final char c = s.charAt(i);
      final int pos = UNICODE.indexOf(c);
      if (pos > -1) {
        b.append(PLAIN_ASCII.charAt(pos));
      } else {
        b.append(c);
      }
    }
    return b.toString();
  }

  /**
   * Default string.
   * 
   * @param str
   *          the str
   * @return the string
   */
  private static String defaultString(final String str) {
    return str == null ? "" : str;
  }

  /**
   * Ellipsis.
   * 
   * @param text
   *          the text
   * @param length
   *          the length
   * @return the string
   */
  public static String ellipsis(final String text, final int length) {
    return text == null ? "" : length <= 0 ? text : text.length() > length ? text.substring(0,
        length - 3) + "..." : text;
  }

  /**
   * To email list to array.
   * 
   * @param textFieldValue
   *          the text field value
   * @return the string[]
   */
  public static String[] emailStringToArray(final String emailList) {
    final String[] splitted = emailList.split("[, \\s]");
    final ArrayList<String> withoutSpaces = new ArrayList<String>();
    for (final String element : splitted) {
      final String trim = element.trim();
      if (trim.length() > 0) {
        withoutSpaces.add(trim);
      }
    }
    return withoutSpaces.toArray(new String[withoutSpaces.size()]);
  }

  /**
   * Empty.
   * 
   * @param string
   *          the string
   * @return true, if successful
   */
  public static boolean empty(final String string) {
    return !notEmpty(string);
  }

  /**
   * This method escape only some dangerous html chars
   * 
   * Try to use SimpleHtmlSanitizer better.
   * 
   * @param source
   *          the source
   * @return the string
   */
  public static String escapeHtmlLight(final String source) {
    String result = source;
    if (source != null) {
      // This replacement sort is important
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
   * Generates a href link (with target="_blank").
   * 
   * @param href
   *          the href
   * @param text
   *          the text
   * @return the string
   */
  public static String generateHtmlLink(final String href, final String text) {
    return generateHtmlLink(href, text, true);
  }

  /**
   * Generates a href link.
   * 
   * @param href
   *          the href
   * @param text
   *          the text
   * @param targetBlank
   *          the target blank
   * @return the string
   */
  public static String generateHtmlLink(final String href, final String text, final boolean targetBlank) {
    if (!UriUtils.isSafeUri(href)) {
      throw new UIException("Unsafe href");
    }
    return "<a href=\"" + UriUtils.sanitizeUri(href) + "\"" + (targetBlank ? "target=\"_blank\"" : "")
        + ">" + text + "</a>";
  }

  /**
   * Checks if is delimiter.
   * 
   * @param ch
   *          the ch
   * @param delimiters
   *          the delimiters
   * @return true, if is delimiter
   */
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

  /**
   * Not empty.
   * 
   * @param string
   *          the string
   * @return true, if successful
   */
  public static boolean notEmpty(final String string) {
    return string != null && string.length() > 0;
  }

  /**
   * Removes the http.
   * 
   * @param text
   *          the text
   * @return the string
   */
  public static String removeHttp(final String text) {
    return removeLastSlash(text.replaceFirst("(http|https):\\/\\/", ""));
  }

  /**
   * Removes the last slash.
   * 
   * @param text
   *          the text
   * @return the string
   */
  public static String removeLastSlash(final String text) {
    return text.replaceFirst("/$", "");
  }

  /**
   * Split tags.
   * 
   * @param tagsString
   *          the tags string
   * @return the array list
   */
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
  /**
   * Unescape.
   * 
   * @param source
   *          the source
   * @return the string
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

  /**
   * Instantiates a new text utils.
   */
  public TextUtils() {
  }
}
