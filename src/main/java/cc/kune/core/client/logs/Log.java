package cc.kune.core.client.logs;

import com.google.gwt.core.client.GWT;

public class Log {

    public static void debug(final String message) {
        GWT.log(message);
    }

    public static void debug(final String message, final Throwable caught) {
        GWT.log(message, caught);
    }

    public static void error(final String message) {
        debug(message);
    }

}
