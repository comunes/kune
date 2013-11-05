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

// TODO: Auto-generated Javadoc
/**
 * Adaptation from Wave RichTextTokenizer.
 * 
 * Not used yet, so deprecated
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Deprecated
public class UnrenderUtils {

  /**
   * Checks if is block element.
   *
   * @param tagName the tag name
   * @return true, if is block element
   */
  public static boolean isBlockElement(final String tagName) {
    return "p".equalsIgnoreCase(tagName) || "div".equalsIgnoreCase(tagName) || isListItem(tagName);
  }

  /**
   * Checks if is heading.
   *
   * @param tagName the tag name
   * @return true, if is heading
   */
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

  /**
   * Checks if is link.
   *
   * @param tagName the tag name
   * @return true, if is link
   */
  public static boolean isLink(final String tagName) {
    return "a".equalsIgnoreCase(tagName);
  }

  /**
   * Checks if is list.
   *
   * @param tagName the tag name
   * @return true, if is list
   */
  public static boolean isList(final String tagName) {
    return isOrderedList(tagName) || isUnorderedList(tagName);
  }

  /**
   * Checks if is list item.
   *
   * @param tagName the tag name
   * @return true, if is list item
   */
  public static boolean isListItem(final String tagName) {
    return "li".equalsIgnoreCase(tagName);
  }

  /**
   * Checks if is newline.
   *
   * @param tagName the tag name
   * @return true, if is newline
   */
  public static boolean isNewline(final String tagName) {
    return "br".equalsIgnoreCase(tagName);
  }

  /**
   * Checks if is ordered list.
   *
   * @param tagName the tag name
   * @return true, if is ordered list
   */
  public static boolean isOrderedList(final String tagName) {
    return "ol".equalsIgnoreCase(tagName);
  }

  /**
   * Checks if is table.
   *
   * @param tagName the tag name
   * @return true, if is table
   */
  public static boolean isTable(final String tagName) {
    return "table".equalsIgnoreCase(tagName);
  }

  /**
   * Checks if is table cell.
   *
   * @param tagName the tag name
   * @return true, if is table cell
   */
  public static boolean isTableCell(final String tagName) {
    return "th".equalsIgnoreCase(tagName) || "td".equalsIgnoreCase(tagName);
  }

  /**
   * Checks if is table related.
   *
   * @param tagName the tag name
   * @return true, if is table related
   */
  public static boolean isTableRelated(final String tagName) {
    // TODO(patcoleman): fix up table implementation once tables supported in
    // the editor.
    // When this happens, also extract out strings into symbolic constants.
    return isTable(tagName) || isTableRow(tagName) || isTableCell(tagName)
        || "thead".equalsIgnoreCase(tagName) || "tbody".equalsIgnoreCase(tagName);
  }

  /**
   * Checks if is table row.
   *
   * @param tagName the tag name
   * @return true, if is table row
   */
  public static boolean isTableRow(final String tagName) {
    return "tr".equalsIgnoreCase(tagName);
  }

  /**
   * Checks if is unordered list.
   *
   * @param tagName the tag name
   * @return true, if is unordered list
   */
  public static boolean isUnorderedList(final String tagName) {
    return "ul".equalsIgnoreCase(tagName);
  }

  /**
   * Gets the style property value.
   *
   * @param styles the styles
   * @param name the name
   * @return the style property value
   */
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

  /**
   * Checks if is background color.
   *
   * @param el the el
   * @return true, if is background color
   */
  public boolean isBackgroundColor(final Element el) {
    return isStylePropertySet(el, "backgroundColor");
  }

  /**
   * Checks if is color.
   *
   * @param el the el
   * @return true, if is color
   */
  public boolean isColor(final Element el) {
    return isStylePropertySet(el, "color");
  }

  /**
   * Checks if is font family.
   *
   * @param el the el
   * @return true, if is font family
   */
  public boolean isFontFamily(final Element el) {
    return isStylePropertySet(el, "fontFamily");
  }

  /**
   * Checks if is style property set.
   *
   * @param el the el
   * @param property the property
   * @return true, if is style property set
   */
  public boolean isStylePropertySet(final Element el, final String property) {
    final String value = getStylePropertyValue(el.attr("style"), property);
    return value != null && !value.isEmpty();
  }

}
