package org.ourproject.kune.platf.client.dispatch;

public class ActionEvent<T> {
    public final String event;
    public final Action<T> action;

    public ActionEvent(final String event, final Action<T> action) {
        this.event = event;
        this.action = action;
    }
}
