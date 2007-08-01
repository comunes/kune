package org.ourproject.kune.platf.client.dispatch;

public interface Dispatcher {
	Action subscribe(String eventName, Action action);
	void fireState(String encodedEvent);
	void fire(String eventName, Object value);
}
