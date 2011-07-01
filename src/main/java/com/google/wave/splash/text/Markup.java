/**
 * Copyright 2010 Google Inc.
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cc.kune.core.client.errors.DefaultException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A utility class that converts raw wave documents into html.
 * 
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
@Singleton
public class Markup {
  private static final DateFormat MONTH_DAY_FORMATTER = new SimpleDateFormat("MMM dd");
  private static final DateFormat MONTH_DAY_YEAR_FORMATTER = new SimpleDateFormat("MMM dd, yyyy");
  private static final DateFormat TIME_FORMATTER = new SimpleDateFormat("h:mm a");
  private static final DateFormat TIME_MILLIS_FORMATTER = new SimpleDateFormat("s.SSS");

  public static String embedSnippet(final String waveId) {
    // TODO(dhanji): Move to template
    return "&lt;script type=\"text/javascript\"&gt;" + "    function load() {"
        + "      var targetDiv = document.getElementById('waveframe');"
        + "      var wavePanel = new google.wave.WavePanel({"
        + "        rootUrl: \"http://localhost:8080/\"," + "        target: targetDiv,"
        + "        lite: true" + "      }).loadWave(\"" + waveId + "\");" + "    }"
        + "  &lt;/script&gt;";
  }

  private static String extractScheme(final String uri) {
    if (null == uri) {
      return null;
    }
    final int colonPos = uri.indexOf(':');
    if (colonPos < 0) {
      return null;
    }
    final String scheme = uri.substring(0, colonPos);
    if (scheme.indexOf('/') >= 0 || scheme.indexOf('#') >= 0) {
      // The URI's prefix up to the first ':' contains other URI special
      // chars, and won't be interpreted as a scheme.
      return null;
    }
    return scheme;
  }

  /**
   * Formats a given timestamp into a friendly date string. The output will look
   * differently depending on the day and year. If the time given is the same
   * day as "now", then it will only display the time (12:30 PM). If it's in the
   * same year, then it will display the month and day (Jun 01) otherwise it
   * will return the month, day and year (Jun 01, 2009).
   * 
   * @param timestamp
   * @return the formatted date time string.
   */
  public static String formatDateTime(final long timestamp) {
    final Date date = new Date(timestamp);
    final Date now = new Date();
    if (now.getDay() == date.getDay()) {
      return TIME_FORMATTER.format(date);
    } else if (now.getYear() == date.getYear()) {
      return MONTH_DAY_FORMATTER.format(date);
    } else {
      return MONTH_DAY_YEAR_FORMATTER.format(date);
    }
  }

  public static String formatMillis(final long millis) {
    return TIME_MILLIS_FORMATTER.format(new Date(millis)) + "s";
  }

  // TODO(dhanji): Should we allow more schemes? Like im:
  private static boolean isSafeUri(final String uri) {
    final String scheme = extractScheme(uri);
    return (scheme == null || "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)
        || "mailto".equalsIgnoreCase(scheme) || "ftp".equalsIgnoreCase(scheme));
  }

  /**
   * Checks if an image URL is safe (i.e. hosted on Google)
   * 
   * @param imageUrl
   *          A string URL
   * @return True if this image URL is safe to use in a google-hosted page.
   */
  public static boolean isTrustedImageUrl(final String imageUrl) {
    final String scheme = extractScheme(imageUrl);

    // NOTE(dhanji): the trailing slash is extremely important.
    return (scheme != null)
        && (imageUrl.startsWith(scheme + "://www.google.com/") || imageUrl.startsWith(scheme
            + "://google.com/"));
  }

  /**
   * Sanitizes untrusted text so that it does not emit HTML markup. This utility
   * is based on a similar one in GWT's SafeHtml.java. It eliminates all
   * predefined entities in HTML/XHTML, replacing them with escape codes:
   * http://
   * en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references
   * 
   * @param text
   *          The untrusted text to sanitize
   * @return Escaped text that can safely be rendered in a web page.
   */
  public static String sanitize(final String text) {
    final StringBuilder out = new StringBuilder(text.length());

    final char[] chars = text.toCharArray();
    for (final char c : chars) {
      switch (c) {
      case '&':
        out.append("&amp;");
        break;
      case '\'':
        out.append("&#39;");
        break;
      case '"':
        out.append("&quot;");
        break;
      case '<':
        out.append("&lt;");
        break;
      case '>':
        out.append("&gt;");
        break;
      default:
        // allow all other characters.
        out.append(c);
      }
    }

    return out.toString();
  }

  /**
   * First sanitizes the given URI and then encodes it using the UTF-8 character
   * set.
   * 
   * @param uri
   *          An untrusted URI string
   * @return Sanitized, URL-encoded URI for embedding in HTML
   */
  static String sanitizeAndEncode(final String uri) {
    try {
      return URLEncoder.encode(sanitizeUri(uri), "UTF-8");
    } catch (final UnsupportedEncodingException e) {
      throw new DefaultException(e);
    }
  }

  /**
   * Checks if the scheme is one of four simple types (see #isSafeUri), if not
   * disallows the URI by reducing it to '#'
   * 
   * @param uri
   *          An untrusted URI string to sanitize
   * @return Returns a safe URI.
   */
  private static String sanitizeUri(final String uri) {
    return isSafeUri(uri) ? uri : "#";
  }

  public static String toBlipId(final String id) {
    // HACK to make blip ids work as DOM ids
    return id.replace('-', '+');
  }

  //
  // public static ClientAction measure(String action, long searchTime) {
  // return new ClientAction("measure")
  // .html(action + " completed in " + Markup.formatMillis(searchTime));
  // }

  /**
   * Simply converts a lowerCamelCased symbol into a dashed one:
   * 
   * <pre>
   *   fontWeight -> font-weight
   * </pre>
   * 
   * TODO: perhaps we should intern all these strings to save memory and remove
   * the overhead of string allocation, they could also be perfectly hashed.
   */
  static String toDashedStyle(final String name) {
    final char[] nameChars = name.toCharArray();

    // Pre allocate buffer, +1 for '-' (2-dashes are uncommon)
    final StringBuilder builder = new StringBuilder(nameChars.length + 1);
    for (final char nameChar : nameChars) {
      if (Character.isUpperCase(nameChar)) {
        builder.append('-');
        builder.append(Character.toLowerCase(nameChar));
        continue;
      }

      builder.append(nameChar);
    }

    return builder.toString();
  }

  public static String toDomId(final String id) {
    // HACK to make blip ids work as DOM ids
    return id.replace('+', '-');
  }

  @Inject
  Markup() {
  }

  /**
   * @return the participant's display name.
   */
  // TODO: This doesn't belong here.
  public String getDisplayName(final String participantId) {
    return "FIXME";
  }

  public String getImageSize() {
    return "33px";
  }

  /**
   * @return the participant's image url.
   */
  // TODO: This doesn't belong here.
  public String getImageUrl(final String participantId) {
    // FIXME
    return "others/unknown.jpg";
  }

  /**
   * Helpful utility for templates.
   */
  public String sanitizeHtml(final String text) {
    return Markup.sanitize(text);
  }
}
