/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.dispatch;

import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;

public class DefaultDispatcher implements Dispatcher {

    private static DefaultDispatcher instance;
    private final HashMap<String, ActionList<?>> subscriptors;

    public static DefaultDispatcher getInstance() {
        if (instance == null) {
            instance = new DefaultDispatcher();
        }
        return instance;
    }

    DefaultDispatcher() {
        this.subscriptors = new HashMap<String, ActionList<?>>();
    }

    public <T> void fire(final String eventName, final T param) {
        ActionList<T> list = getSubscriptorsList(eventName);
        for (Iterator<Action<T>> iterator = list.iterator(); iterator.hasNext();) {
            Action<T> action = iterator.next();
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

    public <T> Action<T> subscribe(final String eventName, final Action<T> action) {
        ActionList<T> list = getSubscriptorsList(eventName);
        list.addAction(action);
        return action;
    }

    public void fireState(final String encodedEvent) {
        History.newItem(encodedEvent);
    }

    private <T> void fire(final Action<T> action, final T param) {
        action.execute(param);
    }

    @SuppressWarnings("unchecked")
    private <T> ActionList<T> getSubscriptorsList(final String eventName) {
        ActionList<T> list = (ActionList<T>) subscriptors.get(eventName);
        if (list == null) {
            list = new ActionList<T>();
            subscriptors.put(eventName, list);
        }
        return list;
    }

}
