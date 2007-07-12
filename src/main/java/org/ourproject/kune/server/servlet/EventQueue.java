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
package org.ourproject.kune.server.servlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ourproject.kune.client.model.Event;

/**
 * Event queue as see in:
 * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/fd724f4099a29251/ccd9aa8b65909272?lnk=st&q=continuations&rnum=1#ccd9aa8b65909272
 * (without continuations, don't scale)
 */
public class EventQueue {
    private static final long TIMEOUT = 60000;
    private static final int MAX_EVENTS = 10;

    public static EventQueue eventQueue = new EventQueue();

    private Map<String, List<Event>> eventMap = new HashMap<String, List<Event>>();
    private Map<String, Date> timestamps = new HashMap<String, Date>();
    private boolean showMissedEvents = false;
    private int maxEvents = MAX_EVENTS;
    private long timeout = TIMEOUT;

    public static EventQueue getInstance() {
        return eventQueue;
    }

    public void addEvent(String sessionHandle, Event event) {
        List<Event> eventList;
        synchronized (eventMap) {
            eventList = eventMap.get(sessionHandle);
            if (eventList == null) {
                eventList = new ArrayList<Event>();
                eventMap.put(sessionHandle, eventList);
            }

        }
        synchronized (eventList) {
            eventList.add(event);
            if (eventList.size() > maxEvents) {
                eventList.remove(0);
            }
            eventList.notify();
        }
    }

    public List<Event> getEvents(String sessionHandle) {
        List<Event> eventList = getEventList(sessionHandle);
        synchronized (eventList) {
            try {
                eventList.wait(timeout);
            } catch (InterruptedException e) {
                // Ignore
            }
        }
        return eventList;
    }

    private List<Event> getEventList(String session) {
        List<Event> eventList;
        synchronized (eventMap) {
            eventList = eventMap.get(session);
            if (eventList == null) {
                eventList = new ArrayList<Event>();
                eventMap.put(session, eventList);
            }
        }
        return eventList;
    }

    //@SuppressWarnings("unchecked")
    public List getNewEvents(String session) {
        Date lastTimestamp = (Date) timestamps.get(session);

        List<Event> eventList = null;
        List<Event> newEvents = new ArrayList<Event>();
        if (lastTimestamp == null) {
            if (showMissedEvents) {
                lastTimestamp = new Date(0);
                eventList = getEventList(session);
            } else {
                lastTimestamp = new Date();
                // Wait for next event
                eventList = getEvents(session);
            }
            timestamps.put(session, lastTimestamp);
        } else {
            // Check for events that might have been occured in the meantime
            eventList = getEventList(session);
            synchronized (eventList) {
                for (Event event : eventList) {
                    if (event.getTimestamp().after(lastTimestamp)) {
                        newEvents.add(event);
                    }
                }
                if (newEvents.size() > 0) {
                    timestamps.put(session, new Date());
                    return newEvents;
                } else {
                    // No events in the meantime, so wait for new events
                    eventList = getEvents(session);
                    timestamps.put(session, new Date());
                }
            }
        }
        synchronized (eventList) {
            for (Iterator i = eventList.iterator(); i.hasNext();) {
                Event event = (Event) i.next();
                if (event.getTimestamp().after(lastTimestamp)) {
                    newEvents.add(event);
                }
            }
        }
        return newEvents;
    }

}