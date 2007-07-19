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

import javax.servlet.ServletException;

import org.ourproject.kune.client.model.Event;
import org.ourproject.kune.client.rpc.XmppService;
import org.ourproject.kune.server.KuneModule;
import org.ourproject.kune.server.manager.XmppManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class XmppServiceServlet extends AsyncRemoteServiceServlet implements XmppService {

    private XmppManager xmppManager;

    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        super.init();
        inject();
    }

    private void inject() {
        Injector injector = Guice.createInjector(new KuneModule());
        injector.injectMembers(this);
    }

    public void changeSubject(String subject) throws SerializableException {
        try {
            xmppManager.changeSubject(subject);
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    public void createRoom(String owner, String roomName) throws SerializableException  {
        try {
            xmppManager.createRoom(this.getThreadLocalRequest().getSession().getId(), owner, roomName);
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    public void joinRoom(String roomName, String nick) throws SerializableException  {
        try {
            xmppManager.joinRoom(this.getThreadLocalRequest().getSession().getId(), roomName, nick);
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    public void leaveRoom(String roomName) throws SerializableException {
        try {
            xmppManager.leaveRoom(this.getThreadLocalRequest().getSession().getId(), roomName);
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    public void login() throws SerializableException  {
        try {
            xmppManager.connectAndLogin(this.getThreadLocalRequest().getSession().getId(), "test2345", "test2345");
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    public List<Event> getEvents() throws SerializableException {
        try {
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
                throw new SerializableException(e.toString());
            }
        }
    }

    public void sendMessage(String roomName, String message) throws SerializableException {
        try {
            xmppManager.sendMessage(this.getThreadLocalRequest().getSession().getId(), roomName, message);
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    @Inject
    public void setXmppManager(XmppManager xmppManager) throws SerializableException  {
        this.xmppManager = xmppManager;
    }

}
