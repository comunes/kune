package org.ourproject.kune.platf.client.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;

public class DefaultDispatcher implements Dispatcher, HistoryListener {
    private final Injector injector;
    private final HashMap subscriptors;

    /**
     * la idea es que pueda haber distintos injectors... de momento, uno
     */
    public DefaultDispatcher(final Injector injector) {
	this.injector = injector;
	injector.setDispatcher(this);
	this.subscriptors = new HashMap();
    }

    public Action subscribe(final String eventName, final Action action) {
	List list = getSubscriptorsList(eventName);
	injector.inject(action);
	list.add(action);
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
	GWT.log("DISPATCH: " + eventName + " (" + value + ")", null);
	List list = getSubscriptorsList(eventName);
	for (int index = 0; index < list.size(); index++) {
	    fire((Action) list.get(index), value);
	}
    }

    private void fire(final Action action, final Object value) {
	action.execute(value);
    }

    public void fireState(final String encodedEvent) {
	History.newItem(encodedEvent);
    }

    public void onHistoryChanged(final String encoded) {
	HistoryToken token = new HistoryToken(encoded);
	fire(token.eventName, token.value);
    }

    public void subscribeAll(final HashMap actions) {
	Action action;
	String eventName;

	Iterator iterator = actions.keySet().iterator();
	while (iterator.hasNext()) {
	    eventName = (String) iterator.next();
	    action = (Action) actions.get(eventName);
	    subscribe(eventName, action);
	}
    }

}
