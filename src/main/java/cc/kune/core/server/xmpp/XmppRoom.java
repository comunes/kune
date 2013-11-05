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
package cc.kune.core.server.xmpp;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

public class XmppRoom implements Room, PacketListener {
  private final String alias;
  private RoomListener listener;
  private final MultiUserChat muc;

  public XmppRoom(final MultiUserChat muc, final String alias) {
    this.muc = muc;
    this.alias = alias;
  }

  public String getAlias() {
    return alias;
  }

  public void processPacket(final Packet packet) {
    if (packet instanceof Message) {
      processMessage((Message) packet);
    }
  }

  private void processMessage(final Message message) {
    listener.onMessage(message.getFrom(), message.getTo(), message.getBody());
  }

  public void setListener(final RoomListener listener) {
    this.listener = listener;
  }

  public MultiUserChat getMuc() {
    return muc;
  }

}
