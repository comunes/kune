package org.ourproject.kune.workspace.client.sitebar.msg;

import org.ourproject.kune.platf.client.View;

public interface SiteMessage extends View {
    public static final int ERROR = 0;
    public static final int VERYIMP = 1;
    public static final int IMP = 2;
    public static final int INFO = 3;

    void setMessage(String message, int level);

    void adjustWidth(int width);

    View getView();
}
