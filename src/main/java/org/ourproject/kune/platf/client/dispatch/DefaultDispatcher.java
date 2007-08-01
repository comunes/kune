package org.ourproject.kune.platf.client.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ourproject.kune.platf.client.inject.Injector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;

public class DefaultDispatcher implements Dispatcher, HistoryListener {
	private final Injector injector;
	private final HashMap subscriptors;

	/**
	 * la idea es que pueda haber distintos injectors... de momento, uno
	 */
	public DefaultDispatcher(Injector injector) {
		this.injector = injector;
		injector.setDispatcher(this);
		this.subscriptors = new HashMap();
	}

	public Action subscribe(String eventName, Action action) {
		List list = getSubscriptorsList(eventName);
		list.add(action);
		return action;
	}

	private List getSubscriptorsList(String eventName) {
		Object list = subscriptors.get(eventName);
		if (list == null) {
			list = new ArrayList();
			subscriptors.put(eventName, list);
		}
		return (List) list;
	}

	public void fire(String eventName, Object value) {
		GWT.log("DISPATCH: " + eventName + " (" + value + ")", null);
		List list = getSubscriptorsList(eventName);
		for (int index = 0; index < list.size(); index++) {
			fire((Action) list.get(index), value);
		}
	}

	private void fire(Action action, Object value) {
		injector.inject(action);
		action.execute(value);
	}

	public void fireState(String encodedEvent) {
	    History.newItem(encodedEvent);
	}

	public void onHistoryChanged(String encoded) {
		HistoryToken token = new HistoryToken(encoded);
		fire(token.eventName, token.value);
	}

}
