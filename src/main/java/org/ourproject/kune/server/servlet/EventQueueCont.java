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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.ourproject.kune.client.model.Event;
import org.ourproject.kune.server.log.Logger;

import org.apache.commons.logging.Log;
import org.mortbay.util.ajax.Continuation;
import org.mortbay.util.ajax.ContinuationSupport;


/**
 * Event queue with jetty/continuations inspired in:
 * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/fd724f4099a29251/ccd9aa8b65909272?lnk=st&q=continuations&rnum=1#ccd9aa8b65909272
 */
public class EventQueueCont {

    private static final long TIMEOUT = 30000L;

    private static final int MAX_EVENTS = 20;

    public static EventQueueCont eventQueue = new EventQueueCont();

    private Map<String, List<Event>> eventMap = new ConcurrentHashMap<String, List<Event>>();
    private Map<String, Continuation> continuations = new ConcurrentHashMap<String, Continuation>();

    private int maxEvents = MAX_EVENTS;
    private Log log = Logger.getLogger();

    public static EventQueueCont getInstance() {
        return eventQueue;
    }

    public void addEvent(String sessionHandle, Event event) {
        List<Event> eventList;
        eventList = eventMap.get(sessionHandle);
        if (eventList == null) {
            eventList = new ArrayList<Event>();
            eventMap.put(sessionHandle, eventList);
        }
        synchronized (eventList) {
            eventList.add(event);
            log.debug("Added event: " + event.getName());
            if (eventList.size() > maxEvents) {
                eventList.remove(0);
            }
        }

        Continuation continuation = continuations.get(sessionHandle);
        if (continuation != null) {
            log.debug("Was waiting: continuation, resume called after add event");
            continuation.resume();
            continuations.remove(sessionHandle);
        }
    }

    public List<Event> getEvents(HttpServletRequest request) {
        String sessionHandle = request.getSession().getId();
        List<Event> eventList = getEventsOfSession(sessionHandle);
        List<Event> newEvents = new ArrayList<Event>();

        // if the session has no events.
        if (eventList.size() == 0) {
            // suspend the continuation waiting for events
            log.debug("No events now: suspend the continuation waiting for events");
            Continuation continuation =
                ContinuationSupport.getContinuation(request, this);
            Continuation currentContinuation = continuations.get(sessionHandle);
            if (continuation != null && currentContinuation != null && currentContinuation != continuation)
                currentContinuation.resume();
            continuations.put(sessionHandle, continuation);
            if (continuation.suspend(TIMEOUT)) {
                log.debug("Continuation resume called and try to get new events");
            }
            else {
                log.debug("getEvents: continuation timeout");
            }
            log.debug("getEvents: Resume the continuation");
            continuations.get(sessionHandle).resume();
            continuations.remove(sessionHandle);
        }
        else {
            synchronized (eventList) {
                for (Iterator i = eventList.iterator(); i.hasNext();) {
                    Event event = (Event) i.next();
                    newEvents.add(event);
                    i.remove();
                }
            }
        }
        log.debug("Returning " + eventList.size() + (eventList.size() == 1? " event": " events"));
        return newEvents;
    }

    public List<Event> getEventsOfSession(String sessionHandle) {
        List<Event> eventList;
        eventList = eventMap.get(sessionHandle);
        if (eventList == null) {
            eventList = new ArrayList<Event>();
            eventMap.put(sessionHandle, eventList);
        }
        return eventList;
    }

}