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

import com.calclab.emite.core.client.conn.XmppConnection;
import com.calclab.emite.core.client.xmpp.sasl.SASLManager;
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
import com.google.inject.Provider;

public class ChatInstances {

  public class AvatarManagerProvider implements Provider<AvatarManager> {
    @Override
    public AvatarManager get() {
      return Suco.get(AvatarManager.class);
    }
  }

  public class ChatManagerProvider implements Provider<ChatManager> {
    @Override
    public ChatManager get() {
      return Suco.get(ChatManager.class);
    }
  }

  public class PresenceManagerProvider implements Provider<PresenceManager> {
    @Override
    public PresenceManager get() {
      return Suco.get(PresenceManager.class);
    }
  }

  public class PrivateStorageManagerProvider implements Provider<PrivateStorageManager> {
    @Override
    public PrivateStorageManager get() {
      return Suco.get(PrivateStorageManager.class);
    }
  }

  public class RoomManagerProvider implements Provider<RoomManager> {
    @Override
    public RoomManager get() {
      return Suco.get(RoomManager.class);
    }
  }

  public class SASLManagerProvider implements Provider<SASLManager> {
    @Override
    public SASLManager get() {
      return Suco.get(SASLManager.class);
    }
  }

  public class SessionReconnectProvider implements Provider<SessionReconnect> {
    @Override
    public SessionReconnect get() {
      return Suco.get(SessionReconnect.class);
    }
  }

  public class SubscriptionHandlerProvider implements Provider<SubscriptionHandler> {
    @Override
    public SubscriptionHandler get() {
      return Suco.get(SubscriptionHandler.class);
    }
  }

  public class SubscriptionManagerProvider implements Provider<SubscriptionManager> {
    @Override
    public SubscriptionManager get() {
      return Suco.get(SubscriptionManager.class);
    }
  }
  public class XmppConnectionProvider implements Provider<XmppConnection> {
    @Override
    public XmppConnection get() {
      return Suco.get(XmppConnection.class);
    }
  }
  public class XmppRosterProvider implements Provider<XmppRoster> {
    @Override
    public XmppRoster get() {
      return Suco.get(XmppRoster.class);
    }
  }

  public class XmppSessionProvider implements Provider<XmppSession> {
    @Override
    public XmppSession get() {
      return Suco.get(XmppSession.class);
    }
  }

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
