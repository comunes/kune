package org.ourproject.kune.platf.client.dispatch;

public interface DispatcherRevisited {

    <T> ActionRevisited<T> subscribe(String eventName, ActionRevisited<T> action);

    <T> void fire(String eventName, T param);

    <T> void fireDeferred(String eventName, T param);

}
