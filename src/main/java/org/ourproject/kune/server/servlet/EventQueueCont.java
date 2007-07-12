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

import javax.servlet.http.HttpServletRequest;

import org.ourproject.kune.client.model.Event;
import org.ourproject.kune.server.log.Logger;

import org.apache.commons.logging.Log;
import org.mortbay.util.ajax.Continuation;
import org.mortbay.util.ajax.ContinuationSupport;


/**
 * Event queue with jetty/continuations adapted from:
 * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/fd724f4099a29251/ccd9aa8b65909272?lnk=st&q=continuations&rnum=1#ccd9aa8b65909272
 */
public class EventQueueCont {
    private static final long TIMEOUT = 30000L;
    private static final int MAX_EVENTS = 10;

    public static EventQueueCont eventQueue = new EventQueueCont();

    private Map<String, List<Event>> eventMap = new HashMap<String, List<Event>>();
    private Map<String, Date> timestamps = new HashMap<String, Date>();
    private Map<String, Continuation> continuations = new HashMap<String, Continuation>();

    private boolean showMissedEvents = false;
    private int maxEvents = MAX_EVENTS;
    private Log log = Logger.getLogger();

    public static EventQueueCont getInstance() {
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
        synchronized (this) {
            eventList.add(event);
            if (eventList.size() > maxEvents) {
                eventList.remove(0);
            }
            // resume continuations waiting for events
            continuations.get(sessionHandle).resume();
        }

    }

    public List<Event> getEvents(HttpServletRequest request) {
        String sessionHandle = request.getSession().getId();
        List<Event> eventList;
        synchronized (eventMap) {
            eventList = eventMap.get(sessionHandle);
            if (eventList == null) {
                eventList = new ArrayList<Event>();
                eventMap.put(sessionHandle, eventList);
            }
        }

        // if the session has no events.
        if (eventList.size() == 0) {
            synchronized (this) {
                // suspend the continuation waiting for events
                log.debug("Suspend the continuation waiting for events");
                Continuation continuation =
                    ContinuationSupport.getContinuation(request, this);
                Continuation currentContinuation = continuations.get(sessionHandle);
;                if (continuation != null && currentContinuation != null && currentContinuation != continuation)
                    currentContinuation.resume();
                continuations.put(sessionHandle, continuation);
                if (continuation.suspend(TIMEOUT)) {
                    log.debug("Resume called");
                }
                else {
                    log.debug("Timeout");
                }
                if (continuation.isPending()) {
                    log.debug("Continuation is Pending");
                }
                if (continuation.isResumed()) {
                    log.debug("Continuation is resumed");
                }
            }
            log.debug("Resume the continuation");
            continuations.get(sessionHandle).resume();
            continuations.put(sessionHandle, null);
        }
        return eventList;
    }

    private List<Event> getEventList(HttpServletRequest request) {
        String sessionHandle = request.getSession().getId();
        List<Event> eventList;
        synchronized (eventMap) {
            eventList = eventMap.get(sessionHandle);
            if (eventList == null) {
                eventList = new ArrayList<Event>();
                eventMap.put(sessionHandle, eventList);
            }
        }
        return eventList;
    }

    public List getNewEvents(HttpServletRequest request) {
        String sessionHandle = request.getSession().getId();
        Date lastTimestamp = (Date) timestamps.get(sessionHandle);

        List<Event> eventList = null;
        List<Event> newEvents = new ArrayList<Event>();
        if (lastTimestamp == null) {
            if (showMissedEvents) {
                lastTimestamp = new Date(0);
                eventList = getEventList(request);
            } else {
                lastTimestamp = new Date();
                // Wait for next event
                eventList = getEvents(request);
            }
            timestamps.put(sessionHandle, lastTimestamp);
        } else {
            // Check for events that might have been occured in the meantime
            eventList = getEventList(request);
            synchronized (eventList) {
                for (Event event : eventList) {
                    if (event.getTimestamp().after(lastTimestamp)) {
                        newEvents.add(event);
                    }
                }
                if (newEvents.size() > 0) {
                    timestamps.put(sessionHandle, new Date());
                    return newEvents;
                } else {
                    // No events in the meantime, so wait for new events
                    eventList = getEvents(request);
                    timestamps.put(sessionHandle, new Date());
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

    public void setContinuation(Continuation continuation, Continuation currentContinuation) {
        if (continuation != null && currentContinuation != null && currentContinuation != continuation)
            currentContinuation.resume();
        currentContinuation = continuation;
    }

}