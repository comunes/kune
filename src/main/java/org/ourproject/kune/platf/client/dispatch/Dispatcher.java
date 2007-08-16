package org.ourproject.kune.platf.client.dispatch;

public interface Dispatcher {
    public static class App {
	public static Dispatcher instance;
    }

    Action subscribe(Action action);

    void fire(String eventName, Object value, Object extra);

    void fire(String eventName, Object value);

    Action getAction(String key);

}
