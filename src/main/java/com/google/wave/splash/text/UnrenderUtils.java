/**
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.google.wave.splash.text;

import org.jsoup.nodes.Element;

/**
 * Adaptation from Wave RichTextTokenizer.
 * 
 * Not used yet, so deprecated
 */
@Deprecated
public class UnrenderUtils {

  public static boolean isBlockElement(final String tagName) {
    return "p".equalsIgnoreCase(tagName) || "div".equalsIgnoreCase(tagName) || isListItem(tagName);
  }

  public static boolean isHeading(final String tagName) {
    if (tagName.length() == 2) {
      if (tagName.charAt(0) == 'h' || tagName.charAt(0) == 'H') {
        final int size = tagName.charAt(1) - '0';
        if (size >= 1 && size <= 4) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean isLink(final String tagName) {
    return "a".equalsIgnoreCase(tagName);
  }

  public static boolean isList(final String tagName) {
    return isOrderedList(tagName) || isUnorderedList(tagName);
  }

  public static boolean isListItem(final String tagName) {
    return "li".equalsIgnoreCase(tagName);
  }

  public static boolean isNewline(final String tagName) {
    return "br".equalsIgnoreCase(tagName);
  }

  public static boolean isOrderedList(final String tagName) {
    return "ol".equalsIgnoreCase(tagName);
  }

  public static boolean isTable(final String tagName) {
    return "table".equalsIgnoreCase(tagName);
  }

  public static boolean isTableCell(final String tagName) {
    return "th".equalsIgnoreCase(tagName) || "td".equalsIgnoreCase(tagName);
  }

  public static boolean isTableRelated(final String tagName) {
    // TODO(patcoleman): fix up table implementation once tables supported in
    // the editor.
    // When this happens, also extract out strings into symbolic constants.
    return isTable(tagName) || isTableRow(tagName) || isTableCell(tagName)
        || "thead".equalsIgnoreCase(tagName) || "tbody".equalsIgnoreCase(tagName);
  }

  public static boolean isTableRow(final String tagName) {
    return "tr".equalsIgnoreCase(tagName);
  }

  public static boolean isUnorderedList(final String tagName) {
    return "ul".equalsIgnoreCase(tagName);
  }

  private String getStylePropertyValue(final String styles, final String name) {
    if (styles != null && styles.contains(name)) {
      for (final String stylePair : styles.split(";")) {
        final int index = stylePair.indexOf(':');
        if (index >= 0 && index < stylePair.length() - 1) {
          final String key = stylePair.substring(0, index).trim();
          final String value = stylePair.substring(index + 1);
          if (key.equalsIgnoreCase(name)) {
            return value.trim();
          }
        }
      }
    }
    return null;
  }

  public boolean isBackgroundColor(final Element el) {
    return isStylePropertySet(el, "backgroundColor");
  }

  public boolean isColor(final Element el) {
    return isStylePropertySet(el, "color");
  }

  public boolean isFontFamily(final Element el) {
    return isStylePropertySet(el, "fontFamily");
  }

  public boolean isStylePropertySet(final Element el, final String property) {
    final String value = getStylePropertyValue(el.attr("style"), property);
    return value != null && !value.isEmpty();
  }

}
