/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.core.server.xmpp;

import java.util.Collection;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;

// TODO: Auto-generated Javadoc
/**
 * The Interface XmppManager.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface XmppManager {

  /**
   * Creates the room.
   * 
   * @param connection
   *          the connection
   * @param roomName
   *          the room name
   * @param ownerAlias
   *          the owner alias
   * @param subject
   *          the subject
   * @return the room
 * @throws SmackException 
 * @throws NoResponseException 
   */
  Room createRoom(ChatConnection connection, String roomName, String ownerAlias, String subject) throws NoResponseException, SmackException;

  /**
   * Destroy room.
   * 
   * @param conn
   *          the conn
   * @param roomName
   *          the room name
 * @throws NotConnectedException 
 * @throws NoResponseException 
   */
  void destroyRoom(ChatConnection conn, String roomName) throws NoResponseException, NotConnectedException;

  /**
   * Disconnect.
   * 
   * @param connection
   *          the connection
   */
  void disconnect(ChatConnection connection);

  /**
   * Exist room.
   * 
   * @param conn
   *          the conn
   * @param roomName
   *          the room name
   * @return true, if successful
   */
  boolean existRoom(ChatConnection conn, String roomName);

  /**
   * Gets the roster.
   * 
   * @param connection
   *          the connection
   * @return the roster
   */
  Collection<RosterEntry> getRoster(ChatConnection connection);

  /**
   * Join room.
   * 
   * @param connection
   *          the connection
   * @param roomName
   *          the room name
   * @param alias
   *          the alias
   * @return the room
 * @throws NotConnectedException 
 * @throws NoResponseException 
   */
  Room joinRoom(ChatConnection connection, String roomName, String alias) throws NoResponseException, NotConnectedException;

  /**
   * Login.
   * 
   * @param userName
   *          the user name
   * @param password
   *          the password
   * @param resource
   *          the resource
   * @return the chat connection
   */
  ChatConnection login(String userName, String password, String resource);

  /**
   * Send message.
   * 
   * @param room
   *          the room
   * @param message
   *          the message
   */
  void sendMessage(Room room, String message);

  /**
   * Send message.
   * 
   * @param userName
   *          the user name
   * @param message
   *          the message
   */
  void sendMessage(String userName, String message);

}
