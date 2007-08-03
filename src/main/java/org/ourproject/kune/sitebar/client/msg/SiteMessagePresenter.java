package org.ourproject.kune.sitebar.client.msg;

import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class SiteMessagePresenter implements SiteMessage {
    private static final int TIMEVISIBLE = 4000;

    private SiteMessageView view;
    private boolean isVisible;
    private String message;
    private int lastMessageType;

    private final AbstractImagePrototype[] messageIcons;

    private final String[] messageStyle;

    public SiteMessagePresenter() {
	lastMessageType = INFO;
	isVisible = false;
	message = "";

	final Images images = Images.App.getInstance();
	messageIcons = new AbstractImagePrototype[] { images.error(), images.important(), images.emblemImportant(),
		images.emblemImportant() };

	messageStyle = new String[] { "error", "veryimp", "imp", "info" };
    }

    public void init(final SiteMessageView siteMessageView) {
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

    public void setValue(final String message, final int type) {
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

    public void onClose() {
	timer.run();
    }

    public void adjustWidth(final int windowWidth) {
	view.adjustWidth(windowWidth);
    }
}
