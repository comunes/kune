/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

public interface ChatClient extends SimpleContactManager {
  public static final String CHAT_CLIENT_ICON_ID = "k-chat-icon-id";

  void chat(XmppURI jid);

  void doLogin();

  boolean isBuddy(XmppURI jid);

  boolean isXmppLoggedIn();

  Room joinRoom(String roomName, String userAlias);

  Room joinRoom(String roomName, String subject, String userAlias);

  void login(XmppURI uri, String passwd);

  /**
   * @return true if loggin is needed
   */
  boolean loginIfNecessary();

  void logout();

  XmppURI roomUriFrom(String shortName);

  void setAvatar(String photoBinary);

  void show();

  XmppURI uriFrom(String shortName);

}
