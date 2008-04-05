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
