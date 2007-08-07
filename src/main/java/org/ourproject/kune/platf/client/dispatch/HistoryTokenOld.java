package org.ourproject.kune.platf.client.dispatch;

public class HistoryTokenOld {
    private static final char SEPARATOR = '.';
    private static final String REGEX = "\\.";
    public final String eventName;
    public final String value;
    private final String[] tokens;

    public HistoryTokenOld(final String encoded) {
	tokens = encoded.split(REGEX);
	eventName = tokens[0];
	if (tokens.length > 1) {
	    value = encoded.substring(eventName.length() + 1);
	} else {
	    value = null;
	}
    }

    public static String encode(final String eventName, final int value) {
	return eventName + SEPARATOR + value;
    }

    public static String encode(final Object p1, final Object p2, final Object p3) {
	return p1.toString() + SEPARATOR + p2 + SEPARATOR + p3;
    }

    public static String encode(final String eventName, final String value) {
	return eventName + SEPARATOR + value;
    }

    public static String[] split(final Object value) {
	return ((String) value).split(REGEX);
    }

    public static String encodeState(final String actionName, final String groupName, final String toolName,
	    final String contextRef, final String reference) {
	return actionName + SEPARATOR + groupName + SEPARATOR + toolName + SEPARATOR + contextRef + SEPARATOR
		+ reference;
    }

    public String getParam(final int index) {
	return tokens[index + 1];
    }

    public String reencodeFrom(final int begin) {
	String result = tokens[begin + 1];
	for (int index = begin + 2; index < tokens.length; index++) {
	    result += SEPARATOR + tokens[index];
	}
	return result;
    }

    public String getParam(final int index, final String defaultValue) {
	if (index < tokens.length) {
	    return tokens[index];
	} else {
	    return defaultValue;
	}
    }

}
