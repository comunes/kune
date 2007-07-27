package org.ourproject.kune.platf.client;

public class HistoryToken {
    private static final String SEPARATOR = ":";
    public final String controller;
    public final String action;
    public final String value;

    public HistoryToken(String controller, String action, String value) {
        this.controller = controller;
        this.action = action;
        this.value = value;
    }

    public static String encode(String controller, String action, int value) {
        return controller + SEPARATOR + action + SEPARATOR + value;
    }

    public static HistoryToken decode(String historyToken) {
        String[] splited = historyToken.split(SEPARATOR);
        return new HistoryToken(splited[0], splited[1], splited[2]);
    }

}
