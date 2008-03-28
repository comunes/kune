package org.ourproject.kune.platf.client.dispatch;

public interface Dispatcher {

    <T> Action<T> subscribe(String eventName, Action<T> action);

    <T> void fire(String eventName, T param);

    <T> void fireDeferred(String eventName, T param);

}
