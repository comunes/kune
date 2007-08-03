package org.ourproject.kune.platf.client.workspace.ui;

import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class SiteMessagePresenter implements SiteMessage {
    private static final int TIMEVISIBLE = 4000;

    static final Images IMG = Images.App.getInstance();

    private static final int ERROR = 0;
    private static final int VERYIMP = 1;
    private static final int IMP = 2;
    private static final int INFO = 3;

    private SiteMessageView view;
    private boolean isVisible;
    private String message;
    private int lastMessageType;

    private AbstractImagePrototype[] messageIcons;

    private String[] messageStyle;

    public SiteMessagePresenter() {
        lastMessageType = INFO;
        isVisible = false;
        message = "";

        messageIcons = new AbstractImagePrototype[] { IMG.error(), IMG.important(), IMG.emblemImportant(),
                IMG.emblemImportant() };

        messageStyle = new String[] { "error", "veryimp", "imp", "info" };
    }

    public void init(SiteMessageView siteMessageView) {
        this.view = siteMessageView;
    }

    Timer timer = new Timer() {
        public void run() {
            reset();
        }
    };

    private void reset() {
        view.hide();
        view.reset();
        this.message = "";
        this.isVisible = false;
    }

    private void setMessage(String message, int type) {
        if (isVisible) {
            this.message = this.message + "\n" + message;
        } else {
            this.message = message;
            isVisible = true;
        }
        if (lastMessageType != type) {
            view.setMessage(message, messageIcons[lastMessageType], messageStyle[lastMessageType], messageStyle[type]);
            lastMessageType = type;
        } else {
            view.setMessage(message);
        }
        view.show();
        timer.schedule(TIMEVISIBLE);
    }

    public void info(String message) {
        setMessage(message, INFO);
    }

    public void important(String message) {
        setMessage(message, IMP);
    }

    public void veryImportant(String message) {
        setMessage(message, VERYIMP);
    }

    public void error(String message) {
        setMessage(message, ERROR);
    }

    public void onClose() {
        timer.run();
    }

    public void adjustWidth(int windowWidth) {
        view.adjustWidth(windowWidth);
    }
}
