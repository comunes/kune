package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.Window;

public class KuneWindowUtils {
    public static int getClientHeight() {
        return Window.getClientHeight();
    }

    public static int getClientWidth() {
        return Window.getClientWidth();
    }

    public static void open(String url) {
        Window.open(url, "_blank", "");
    }
}
