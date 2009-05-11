/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.chat.server.managers;

import java.util.Collection;

import org.jivesoftware.smack.RosterEntry;

public interface XmppManager {

    Room createRoom(ChatConnection connection, String roomName, String ownerAlias);

    void destroyRoom(ChatConnection conn, String roomName);

    void disconnect(ChatConnection connection);

    Collection<RosterEntry> getRoster(ChatConnection connection);

    Room joinRoom(ChatConnection connection, String roomName, String alias);

    ChatConnection login(String userName, String password, String resource);

    void sendMessage(Room room, String message);

    void sendMessage(String userName, String message);

}
