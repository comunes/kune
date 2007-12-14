package org.ourproject.kune.platf.client.ui;

public class KuneStringUtils {
    public KuneStringUtils() {
    }

    public static String generateHtmlLink(final String href, final String text) {
        return "<a href=\"" + href + "\" target=\"_blank\">" + text + "</a>";
    }

}
