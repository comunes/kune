/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
package cc.kune.chat.client;

import cc.kune.core.client.contacts.SimpleContactManager;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.xep.muc.client.Room;

// TODO: Auto-generated Javadoc
/**
 * The Interface ChatClient.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface ChatClient extends SimpleContactManager {
  
  /** The Constant CHAT_CLIENT_ICON_ID. */
  public static final String CHAT_CLIENT_ICON_ID = "k-chat-icon-id";

  /**
   * Chat.
   *
   * @param jid the jid
   */
  void chat(XmppURI jid);

  /**
   * Do login.
   */
  void doLogin();

  /**
   * Checks if is buddy.
   *
   * @param jid the jid
   * @return true, if is buddy
   */
  boolean isBuddy(XmppURI jid);

  /**
   * Checks if is xmpp logged in.
   *
   * @return true, if is xmpp logged in
   */
  boolean isXmppLoggedIn();

  /**
   * Join room.
   *
   * @param roomName the room name
   * @param userAlias the user alias
   * @return the room
   */
  Room joinRoom(String roomName, String userAlias);

  /**
   * Join room.
   *
   * @param roomName the room name
   * @param subject the subject
   * @param userAlias the user alias
   * @return the room
   */
  Room joinRoom(String roomName, String subject, String userAlias);

  /**
   * Login.
   *
   * @param uri the uri
   * @param passwd the passwd
   */
  void login(XmppURI uri, String passwd);

  /**
   * Login if necessary.
   *
   * @return true if loggin is needed
   */
  boolean loginIfNecessary();

  /**
   * Logout.
   */
  void logout();

  /**
   * Room uri from.
   *
   * @param shortName the short name
   * @return the xmpp uri
   */
  XmppURI roomUriFrom(String shortName);

  /**
   * Sets the avatar.
   *
   * @param photoBinary the new avatar
   */
  void setAvatar(String photoBinary);

  /**
   * Show.
   */
  void show();

  /**
   * Uri from.
   *
   * @param shortName the short name
   * @return the xmpp uri
   */
  XmppURI uriFrom(String shortName);

}
