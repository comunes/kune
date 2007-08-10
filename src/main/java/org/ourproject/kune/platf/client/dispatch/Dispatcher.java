package org.ourproject.kune.platf.client.dispatch;

import com.google.gwt.user.client.HistoryListener;

public interface Dispatcher extends HistoryListener {
    Action subscribe(String eventName, Action action);

    void fireState(String encodedEvent);

    void fire(String eventName, Object value);
}
