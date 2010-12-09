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
package cc.kune.core.client.utils;

import java.util.ArrayList;

public class TextUtils {

    public static final String IN_DEVELOPMENT_P = " (in development)";
    public static final String IN_DEVELOPMENT = "In development";

    // Original regexp from http://snippets.dzone.com/posts/show/452
    public static final String URL_REGEXP = "((ftp|http|https|mailto):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?)";

    // Original regexp from http://www.regular-expressions.info/email.html
    public static final String EMAIL_REGEXP = "[-!#$%&\'*+/=?_`{|}~a-z0-9^]+(\\.[-!#$%&\'*+/=?_`{|}~a-z0-9^]+)*@(localhost|([a-z0-9]([-a-z0-9]*[a-z0-9])?\\.)+[a-z0-9]([-a-z0-9]*[a-z0-9]))?";

    public static final String UNIX_NAME = "^[a-z0-9_\\-]+$";

    public static final String NUM_REGEXP = "^[0-9]+$";

    /*
     * This method escape only some dangerous html chars
     */
    public static String escapeHtmlLight(final String source) {
        if (source == null) {
            return null;
        }
        String result = source;
        result = result.replaceAll("&", "&amp;");
        result = result.replaceAll("\"", "&quot;");
        // text = text.replaceAll("\'", "&#039;");
        result = result.replaceAll("<", "&lt;");
        result = result.replaceAll(">", "&gt;");
        return result;
    }

    public static String generateHtmlLink(final String href, final String text) {
        return "<a href=\"" + href + "\" target=\"_blank\">" + text + "</a>";
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
        return result;
    }

    public TextUtils() {
    }

}
