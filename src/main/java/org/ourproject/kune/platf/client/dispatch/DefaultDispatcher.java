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

import org.ourproject.kune.platf.client.Services;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;

public class DefaultDispatcher implements Dispatcher {
    private static DefaultDispatcher instance;
    private final HashMap<String, List<Action>> subscriptors;
    private Services services;

    DefaultDispatcher() {
        this.subscriptors = new HashMap<String, List<Action>>();
    }

    public Action subscribe(final String eventName, final Action action) {
        List<Action> list = getSubscriptorsList(eventName);
        list.add(action);
        return action;
    }

    private List<Action> getSubscriptorsList(final String eventName) {
        List<Action> list = subscriptors.get(eventName);
        if (list == null) {
            list = new ArrayList<Action>();
            subscriptors.put(eventName, list);
        }
        return list;
    }

    public void fireDeferred(final String eventName, final Object value, final Object extra) {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                fire(eventName, value, extra);
            }
        });
    }

    public void fire(final String eventName, final Object value, final Object extra) {
        List<Action> list = getSubscriptorsList(eventName);
        for (int index = 0; index < list.size(); index++) {
            fire(list.get(index), value, extra);
        }
    }

    private void fire(final Action action, final Object value, final Object extra) {
        action.execute(value, extra, services);
    }

    public void fireState(final String encodedEvent) {
        History.newItem(encodedEvent);
    }

    public void setServices(final Services services) {
        this.services = services;
    }

    public static DefaultDispatcher getInstance() {
        if (instance == null) {
            instance = new DefaultDispatcher();
        }
        return instance;
    }

}
