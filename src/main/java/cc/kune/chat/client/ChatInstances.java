/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.chat.client;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.presence.PresenceManager;
import com.calclab.emite.im.client.roster.SubscriptionHandler;
import com.calclab.emite.im.client.roster.SubscriptionManager;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.reconnect.client.SessionReconnect;
import com.calclab.emite.xep.avatar.client.AvatarManager;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.emite.xep.storage.client.PrivateStorageManager;
import com.calclab.suco.client.Suco;

public class ChatInstances {

  public final AvatarManager avatarManager;
  public final ChatManager chatManager;
  public final PresenceManager presenceManager;
  public final PrivateStorageManager privateStorageManager;
  public final RoomManager roomManager;
  public final XmppRoster roster;
  public final SessionReconnect sessionReconnect;
  public final SubscriptionHandler subscriptionHandler;
  public final SubscriptionManager subscriptionManager;
  public final XmppSession xmppSession;

  public ChatInstances() {
    this.xmppSession = Suco.get(XmppSession.class);
    this.roster = Suco.get(XmppRoster.class);
    this.chatManager = Suco.get(ChatManager.class);
    this.roomManager = Suco.get(RoomManager.class);
    this.sessionReconnect = Suco.get(SessionReconnect.class);
    this.avatarManager = Suco.get(AvatarManager.class);
    this.presenceManager = Suco.get(PresenceManager.class);
    this.privateStorageManager = Suco.get(PrivateStorageManager.class);
    this.subscriptionManager = Suco.get(SubscriptionManager.class);
    this.subscriptionHandler = Suco.get(SubscriptionHandler.class);
  }
}
