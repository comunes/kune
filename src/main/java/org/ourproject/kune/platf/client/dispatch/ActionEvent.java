package org.ourproject.kune.platf.client.dispatch;

public class ActionEvent {
    public final String event;
    public final Action action;

    public ActionEvent(final String event, final Action action) {
	this.event = event;
	this.action = action;
    }
}
