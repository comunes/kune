package org.ourproject.kune.platf.client.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;

public class DefaultDispatcher implements Dispatcher {
    private final HashMap subscriptors;
    private final HashMap actions;

    public DefaultDispatcher() {
	this.subscriptors = new HashMap();
	this.actions = new HashMap();
    }

    public Action subscribe(final Action action) {
	List list = getSubscriptorsList(action.getEventName());
	list.add(action);
	actions.put(action.getActionName(), action);
	return action;
    }

    private List getSubscriptorsList(final String eventName) {
	Object list = subscriptors.get(eventName);
	if (list == null) {
	    list = new ArrayList();
	    subscriptors.put(eventName, list);
	}
	return (List) list;
    }

    public void fire(final String eventName, final Object value) {
	fire(eventName, value, null);
    }

    public void fire(final String eventName, final Object value, final Object extra) {
	GWT.log("DISPATCH: " + eventName + " (" + value + ")", null);
	List list = getSubscriptorsList(eventName);
	for (int index = 0; index < list.size(); index++) {
	    fire((Action) list.get(index), value, extra);
	}
    }

    private void fire(final Action action, final Object value, final Object extra) {
	action.execute(value, extra);
    }

    public void fireState(final String encodedEvent) {
	History.newItem(encodedEvent);
    }

    public Action getAction(final String key) {
	return (Action) actions.get(key);
    }

}
