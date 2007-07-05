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

import org.ourproject.kune.client.rpc.XmppService;
import org.ourproject.kune.server.manager.XmppManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;

public class XmppServiceServlet extends RemoteServiceServlet implements
XmppService {
    private XmppManager xmppManager;

    private static final long serialVersionUID = 1L;

    public void ChangeSubject(String subject) throws SerializableException {
        try {
            xmppManager.ChangeSubject(subject);
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    public void CreateRoom(String Owner, String RoomName) throws SerializableException  {
        try {
            xmppManager.CreateRoom(Owner, RoomName);
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    public void JoinRoom(String RoomName, String UserName) throws SerializableException  {
        try {
            xmppManager.JoinRoom(RoomName, UserName);
        } catch (Exception e) {
            throw new SerializableException(e.toString());
        }
    }

    @Inject
    public void setXmppManager(XmppManager xmppManager) throws SerializableException  {
        this.xmppManager = xmppManager;
    }

}
