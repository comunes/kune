package org.ourproject.kune.platf.client.dispatch;

import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;

public class DefaultDispacherRevisited<E> implements DispatcherRevisited {

    private static DefaultDispacherRevisited instance;
    private final HashMap<String, ActionRevisitedList> subscriptors;
    private E environment;

    public static DefaultDispacherRevisited getInstance() {
        if (instance == null) {
            instance = new DefaultDispacherRevisited();
        }
        return instance;
    }

    DefaultDispacherRevisited() {
        this.subscriptors = new HashMap<String, ActionRevisitedList>();
    }

    public <T> void fire(final String eventName, final T param) {
        ActionRevisitedList<T> list = getSubscriptorsList(eventName);
        for (Iterator<ActionRevisited<T>> iterator = list.iterator(); iterator.hasNext();) {
            ActionRevisited<T> action = iterator.next();
            fire(action, param);
        }
    }

    public <T> void fireDeferred(final String eventName, final T param) {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                fire(eventName, param);
            }
        });
    }

    public <T> ActionRevisited<T> subscribe(final String eventName, final ActionRevisited<T> action) {
        ActionRevisitedList<T> list = getSubscriptorsList(eventName);
        list.addAction(action);
        return action;
    }

    public void fireState(final String encodedEvent) {
        History.newItem(encodedEvent);
    }

    public void setEnvironment(final E environment) {
        this.environment = environment;
    }

    private <T> void fire(final ActionRevisited<T> action, final T param) {
        action.execute(param, environment);
    }

    private <T> ActionRevisitedList<T> getSubscriptorsList(final String eventName) {
        ActionRevisitedList<T> list = subscriptors.get(eventName);
        if (list == null) {
            list = new ActionRevisitedList<T>();
            subscriptors.put(eventName, list);
        }
        return list;
    }

}
