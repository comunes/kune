/*
 * Copyright 1999,2004,2012 The Apache Software Foundation.
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
 */
// Copied from GenerationJava Core Library.
//package com.generationjava.web;
// Copied in kune from:
// http://svn.apache.org/repos/asf/jakarta/taglibs/proper/string/trunk/src/org/apache/taglibs/string/util/XmlW.java
package cc.kune.core.server.utils;

import org.apache.commons.lang.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * XML helping static methods.
 * 
 * @author bayard@generationjava.com
 * @version 0.4 20010812
 */
final public class XmlW {

  // @PMD:REVIEWED:AvoidReassigningParameters: by vjrj on 21/05/09 14:13
  /**
   * Escape xml.
   * 
   * @param str
   *          the str
   * @return the string
   */
  static public String escapeXml(String str) {
    str = StringUtils.replace(str, "&", "&amp;");
    str = StringUtils.replace(str, "<", "&lt;");
    str = StringUtils.replace(str, ">", "&gt;");
    str = StringUtils.replace(str, "\"", "&quot;");
    str = StringUtils.replace(str, "'", "&apos;");
    return str;
  }

  /**
   * Gets the attribute.
   * 
   * @param attribute
   *          the attribute
   * @param text
   *          the text
   * @return the attribute
   */
  static public String getAttribute(final String attribute, final String text) {
    return getAttribute(attribute, text, 0);
  }

  /**
   * Gets the attribute.
   * 
   * @param attribute
   *          the attribute
   * @param text
   *          the text
   * @param idx
   *          the idx
   * @return the attribute
   */
  static public String getAttribute(final String attribute, final String text, final int idx) {
    final int close = text.indexOf(">", idx);
    final int attrIdx = text.indexOf(attribute + "=\"", idx);
    if (attrIdx == -1) {
      return null;
    }
    if (attrIdx > close) {
      return null;
    }
    final int attrStartIdx = attrIdx + attribute.length() + 2;
    final int attrCloseIdx = text.indexOf("\"", attrStartIdx);
    if (attrCloseIdx > close) {
      return null;
    }
    return unescapeXml(text.substring(attrStartIdx, attrCloseIdx));
  }

  // @PMD:REVIEWED:AvoidReassigningParameters: by vjrj on 21/05/09 14:13
  /**
   * Gets the content.
   * 
   * @param tag
   *          the tag
   * @param text
   *          the text
   * @return the content
   */
  static public String getContent(final String tag, String text) {
    int idx = XmlW.getIndexOpeningTag(tag, text);
    if (idx == -1) {
      return "";
    }
    text = text.substring(idx);
    final int end = XmlW.getIndexClosingTag(tag, text);
    idx = text.indexOf('>');
    if (idx == -1) {
      return "";
    }
    return text.substring(idx + 1, end);
  }

  // Pass in "para" and a string that starts with
  // <para> and it will return the index of the matching </para>
  // It assumes well-formed xml. Or well enough.
  /**
   * Gets the index closing tag.
   * 
   * @param tag
   *          the tag
   * @param text
   *          the text
   * @return the index closing tag
   */
  static public int getIndexClosingTag(final String tag, final String text) {
    return getIndexClosingTag(tag, text, 0);
  }

  /**
   * Gets the index closing tag.
   * 
   * @param tag
   *          the tag
   * @param text
   *          the text
   * @param start
   *          the start
   * @return the index closing tag
   */
  static public int getIndexClosingTag(final String tag, final String text, final int start) {
    final String open = "<" + tag;
    final String close = "</" + tag + ">";
    // System.err.println("OPEN: "+open);
    // System.err.println("CLOSE: "+close);
    final int closeSz = close.length();
    int nextCloseIdx = text.indexOf(close, start);
    // System.err.println("first close: "+nextCloseIdx);
    if (nextCloseIdx == -1) {
      return -1;
    }
    int count = StringUtils.countMatches(text.substring(start, nextCloseIdx), open);
    // System.err.println("count: "+count);
    if (count == 0) {
      return -1; // tag is never opened
    }
    int expected = 1;
    while (count != expected) {
      nextCloseIdx = text.indexOf(close, nextCloseIdx + closeSz);
      if (nextCloseIdx == -1) {
        return -1;
      }
      count = StringUtils.countMatches(text.substring(start, nextCloseIdx), open);
      expected++;
    }
    return nextCloseIdx;
  }

  /**
   * Gets the index opening tag.
   * 
   * @param tag
   *          the tag
   * @param text
   *          the text
   * @return the index opening tag
   */
  static public int getIndexOpeningTag(final String tag, final String text) {
    return getIndexOpeningTag(tag, text, 0);
  }

  /**
   * Gets the index opening tag.
   * 
   * @param tag
   *          the tag
   * @param text
   *          the text
   * @param start
   *          the start
   * @return the index opening tag
   */
  static private int getIndexOpeningTag(final String tag, final String text, final int start) {
    // consider whitespace?
    final int idx = text.indexOf("<" + tag, start);
    if (idx == -1) {
      return -1;
    }
    final char next = text.charAt(idx + 1 + tag.length());
    if ((next == '>') || Character.isWhitespace(next)) {
      return idx;
    } else {
      return getIndexOpeningTag(tag, text, idx + 1);
    }
  }

  /**
   * Remove any xml tags from a String. Same as HtmlW's method.
   * 
   * @param str
   *          the str
   * @return the string
   */
  static public String removeXml(final String str) {
    final int sz = str.length();
    final StringBuffer buffer = new StringBuffer(sz);
    // boolean inString = false;
    boolean inTag = false;
    for (int i = 0; i < sz; i++) {
      final char ch = str.charAt(i);
      if (ch == '<') {
        inTag = true;
      } else if (ch == '>') {
        inTag = false;
        continue;
      }
      if (!inTag) {
        buffer.append(ch);
      }
    }
    return buffer.toString();
  }

  // @PMD:REVIEWED:AvoidReassigningParameters: by vjrj on 21/05/09 14:13
  /**
   * Unescape xml.
   * 
   * @param str
   *          the str
   * @return the string
   */
  static public String unescapeXml(String str) {
    str = StringUtils.replace(str, "&amp;", "&");
    str = StringUtils.replace(str, "&lt;", "<");
    str = StringUtils.replace(str, "&gt;", ">");
    str = StringUtils.replace(str, "&quot;", "\"");
    str = StringUtils.replace(str, "&apos;", "'");
    return str;
  }

}
