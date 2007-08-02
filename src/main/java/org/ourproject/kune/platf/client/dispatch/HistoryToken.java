package org.ourproject.kune.platf.client.dispatch;

public class HistoryToken {
    private static final char SEPARATOR = '.';
    private static final String REGEX = "\\.";
    public final String eventName;
    public final String value;

    public HistoryToken(String encoded) {
	int index = encoded.indexOf(SEPARATOR);
	if (index > 0) {
	    this.eventName = encoded.substring(0, index);
	    this.value = encoded.substring(index + 1);
	} else {
	    this.eventName = encoded;
	    this.value = null;
	}
    }

    public static String encode(String eventName, int value) {
	return eventName + SEPARATOR + value;
    }

    public static String encode(Object p1, Object p2, Object p3) {
	return p1.toString() + SEPARATOR + p2 + SEPARATOR + p3;
    }

    public static String encode(String eventName, String value) {
	return eventName + SEPARATOR + value;
    }

    public static String[] split(Object value) {
	return ((String) value).split(REGEX);
    }

}
