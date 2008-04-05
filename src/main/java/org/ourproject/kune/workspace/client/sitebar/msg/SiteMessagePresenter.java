package org.ourproject.kune.workspace.client.sitebar.msg;

import org.ourproject.kune.platf.client.View;

public class SiteMessagePresenter implements SiteMessage, MessagePresenter {
    private SiteMessageView view;
    private boolean isVisible;
    private String message;
    private int lastMessageType;

    public SiteMessagePresenter() {
    }

    public void init(final SiteMessageView siteMessageView) {
        this.view = siteMessageView;
        this.lastMessageType = SiteMessage.INFO;
        resetMessage();
    }

    public void resetMessage() {
        this.message = "";
        this.isVisible = false;
        view.hide();
    }

    public void setMessage(final String message, final int type) {
        if (isVisible) {
            if (!this.message.equals(message)) {
                // Concatenate message (if not is the same)
                this.message = this.message + "<br>" + message;
            }
        } else {
            // New message
            this.message = message;
        }
        if (lastMessageType != type) {
            if (isVisible) {
                if (type < lastMessageType) {
                    // more severe message
                    view.setMessage(this.message, lastMessageType, type);
                    lastMessageType = type;
                } else {
                    view.setMessage(this.message);
                }
            } else {
                // Was closed, and different message level
                view.setMessage(this.message, lastMessageType, type);
                lastMessageType = type;
            }
        } else {
            view.setMessage(this.message);
        }
        isVisible = true;
        view.show();
    }

    public void adjustWidth(final int windowWidth) {
        view.adjustWidth(windowWidth);
    }

    public View getView() {
        return view;
    }
}
