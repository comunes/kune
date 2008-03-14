/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.ui;

import java.util.ArrayList;

public class KuneStringUtils {
    public KuneStringUtils() {
    }

    public static String generateHtmlLink(final String href, final String text) {
        return "<a href=\"" + href + "\" target=\"_blank\">" + text + "</a>";
    }

    /*
     * This method escape only some dangerous html chars
     */
    public static String escapeHtmlLight(final String textOrig) {
        String text = textOrig;
        text = text.replaceAll("&", "&amp;");
        text = text.replaceAll("\"", "&quot;");
        // text = text.replaceAll("\'", "&#039;");
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        return text;
    }

    /*
     * This method unescape only some dangerous html chars for use in GWT Html
     * widget for instance
     */
    public static String unescapeHtmlLight(final String textOrig) {
        String text = textOrig;
        text = text.replaceAll("&amp;", "&");
        text = text.replaceAll("&quot;", "&quot;");
        text = text.replaceAll("&#039;", "\'");
        text = text.replaceAll("&lt;", "<");
        text = text.replaceAll("&gt;", ">");
        return text;
    }

    public static ArrayList<String> splitTags(final String tagsString) {
        ArrayList<String> tagsList = new ArrayList<String>();
        String tagsCopy = tagsString;
        // remove commas and quotes
        tagsCopy = tagsCopy.replaceAll(",", " ");
        tagsCopy = tagsCopy.replaceAll("\"", "");
        tagsCopy = tagsCopy.replaceAll("\'", "");
        String[] splitted = tagsCopy.split("\\s+");
        for (int i = 0; i < splitted.length; i++) {
            String tag = splitted[i];
            tag = tag.replaceAll("\\s$", "");
            if (tag.length() > 0) {
                tagsList.add(tag);
            }
        }
        return tagsList;
    }

}
