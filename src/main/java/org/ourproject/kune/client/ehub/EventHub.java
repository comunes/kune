/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 *
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package org.ourproject.kune.client.ehub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ourproject.kune.client.model.Event;
import org.ourproject.kune.client.rpc.XmppService;
import org.ourproject.kune.client.ui.desktop.SiteMessageDialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class EventHub {
    private static final int NORMAL_TIMEOUT = 500;

    private static final int INITIAL_ERRORS_TIMEOUT = 3000;

    private static final int MANY_ERRORS_TIMEOUT = 20000;

    private Map eventSubscribers = null;

    private int pollErrorCount;

    private int subscribersCount;

    private Timer retryTimer;

    public EventHub() {
        subscribersCount = 0;
        pollErrorCount = 0;
        eventSubscribers = new HashMap();
    }

    public EventSubscription subscribe(String name, EventSubscriber eventSubscriber) {
        List subscribersList = (List) eventSubscribers.get(name);
        if (subscribersList == null) {
            subscribersList = new ArrayList();
            eventSubscribers.put(name, subscribersList);
        }
        subscribersList.add(eventSubscriber);
        subscribersCount++;

        if (subscribersCount == 1) {
            retryTimer = new Timer() {
                public void run() {
                    // FIXME: Memory problems
                    doPoll();
                }
            };
            retryTimer.schedule(NORMAL_TIMEOUT);
        }
        return new EventSubscription(name, eventSubscriber);
    }

    public void unSubscribe(EventSubscription subscription) {
        List subscribersList = (List) eventSubscribers.get(subscription.getName());
        try {
            subscribersList.remove(subscription.getEventSubscriber());
        } catch (Exception e) {
            GWT.log("HubEvent: unSubscribeException" + e.toString(), null);
            SiteMessageDialog.get().setMessageError("Unsubscribe exception: "+  e.toString());
        }
        subscribersCount--;
        if (subscribersCount == 0) {
            retryTimer.cancel();
            retryTimer = null;
        }
    }

    public void localPublish(Event event) {
        List subscribersList = (List) eventSubscribers.get(event.getName());
        if (subscribersList == null) {
            SiteMessageDialog.get().setMessageError("Nobody subscribed to this event");
            GWT.log("Subscribers of event: \'" + event.getName() + "'", null);
        }
        else {
            for (Iterator i = subscribersList.iterator(); i.hasNext();) {
                EventSubscriber eventSubscriber = (EventSubscriber) i.next();
                eventSubscriber.onEvent(event);
            }
        }
    }

    private void doPoll() {
        XmppService.App.getInstance().getEvents(new AsyncCallback() {
            public void onSuccess(Object result) {
                pollErrorCount = 0;
                processRemoteEvents(result);
            }
            public void onFailure(Throwable exception) {
                pollErrorCount++;
                if (pollErrorCount > 5) {
                    String message = "Error getting events: " + exception.toString();
                    SiteMessageDialog.get().setMessageError("Network error trying to contact server. " + message);
                    pollErrorCount = 0;
                    retryTimer.schedule(MANY_ERRORS_TIMEOUT);
                }
                else {
                    SiteMessageDialog.get().setMessageError("Some error getting events: " + exception.toString());
                    retryTimer.schedule(INITIAL_ERRORS_TIMEOUT);
                }
            }
        });
    }

    private void processRemoteEvents(Object result) {
        List events = (List) result;
        String message = "Success in getting " +
            + events.size() + " events";
        SiteMessageDialog.get().setMessageInfo(message);
        for (Iterator it = events.iterator(); it.hasNext();) {
            Event currentEvent = ((Event) it.next());

            localPublish(currentEvent);
        }
        retryTimer.schedule(NORMAL_TIMEOUT);
    }
}
