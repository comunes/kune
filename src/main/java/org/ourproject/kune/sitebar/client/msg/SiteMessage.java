package org.ourproject.kune.sitebar.client.msg;

public interface SiteMessage {
    public static final int ERROR = 0;
    public static final int VERYIMP = 1;
    public static final int IMP = 2;
    public static final int INFO = 3;

    void setValue(String message, int level);
}
