package org.ourproject.kune.sitebar.client.msg;

public class SiteMessagePresenter implements SiteMessage {
    private SiteMessageView view;
    private boolean isVisible;
    private String message;
    private int lastMessageType;

    public SiteMessagePresenter() {
        lastMessageType = INFO;
        isVisible = false;
        message = "";
    }

    public void init(final SiteMessageView siteMessageView) {
        this.view = siteMessageView;
    }

    public void reset() {
        view.hide();
        view.reset();
        this.message = "";
        this.isVisible = false;
    }

    public void setValue(final String message, final int type) {
        if (isVisible) {
            this.message = this.message + "<br>" + message;
        } else {
            this.message = message;
            isVisible = true;
        }
        if (lastMessageType != type) {
            view.setMessage(this.message, lastMessageType, type);
            lastMessageType = type;
        } else {
            view.setMessage(this.message);
        }
        view.show();

    }

    public void onClose() {
        reset();
    }

    public void adjustWidth(final int windowWidth) {
        view.adjustWidth(windowWidth);
    }

    public SiteMessageView getView() {
        return view;
    }
}
