package org.ourproject.kune.platf.client.dispatch;

public class HistoryToken {
    private static final char SEPARATOR = '.';
    private static final String REGEX = "\\.";
    public final String eventName;
    public final String value;
    private final String[] tokens;

    public HistoryToken(String encoded) {
	tokens = encoded.split(REGEX);
	eventName = tokens[0];
	if (tokens.length > 1) {
	    value = encoded.substring(eventName.length() + 1);
	} else {
	    value = null;
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

    public static String encodeState(String actionName, String groupName, String toolName, String contextRef,
	    String reference) {
	return actionName + SEPARATOR + groupName + SEPARATOR + toolName + SEPARATOR + contextRef + SEPARATOR
		+ reference;
    }

    public String getParam(int index) {
	return tokens[index + 1];
    }

    public String reencodeFrom(int begin) {
	String result = tokens[begin + 1];
	for (int index = begin + 2; index < tokens.length; index++) {
	    result += SEPARATOR + tokens[index];
	}
	return result;
    }

}
