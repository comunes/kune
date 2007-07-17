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

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.ourproject.kune.client.model.Event;
import org.ourproject.kune.client.rpc.XmppService;
import org.ourproject.kune.server.log.Logger;
import org.ourproject.kune.server.manager.XmppManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class XmppServiceServlet extends AsyncRemoteServiceServlet implements XmppService {
    private XmppManager xmppManager;
    private Log log = Logger.getLogger();

    private static final long serialVersionUID = 1L;

    public void changeSubject(String subject) throws SerializableException {
        try {
            xmppManager.changeSubject(subject);
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    public void createRoom(String Owner, String RoomName) throws SerializableException  {
        try {
            xmppManager.createRoom(Owner, RoomName);
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    public void joinRoom(String RoomName, String UserName) throws SerializableException  {
        try {
            xmppManager.joinRoom(RoomName, UserName);
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    public List<Event> getEvents() throws SerializableException {
        try {
            log.debug("getEvents called");
            return EventQueueCont.getInstance().getEvents(this.getThreadLocalRequest());
            // return EventQueue.getInstance().getEvents(this.getThreadLocalRequest().getSession().getId());
        } catch (Exception e) {
            if (e instanceof RuntimeException &&
                    "org.mortbay.jetty.RetryRequest".equals(e.getClass().getName())) {
                // Greg Wilkins says:
                // «The continuation mechanism throws the retry exception to make the servlet
                // exit the current handling.
                // You must let RetryException propogate to the container.»
                throw (RuntimeException) e;
            }
            else {
                log.debug("getEvents exception: " + e.toString());
                throw new SerializableException(e.toString());
            }
        }
    }

    public void sendMessage(String message) throws SerializableException {
        try {
            log.debug("sendMessage called");
            final HttpSession session = this.getThreadLocalRequest().getSession();
            EventQueueCont.getInstance().addEvent(session.getId(), new Event("org.ourproject.kune.muc.room", message));
        } catch (Exception e) {
            log.debug("sendMessage exception:" + e.toString());
            throw new SerializableException(e.toString());
        }
    }

    @Inject
    public void setXmppManager(XmppManager xmppManager) throws SerializableException  {
        this.xmppManager = xmppManager;
    }

}
