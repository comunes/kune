/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
