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
package cc.kune.core.server.xmpp;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

// TODO: Auto-generated Javadoc
/**
 * The Class XmppRoom.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class XmppRoom implements Room, PacketListener {

  /** The alias. */
  private final String alias;

  /** The listener. */
  private RoomListener listener;

  /** The muc. */
  private final MultiUserChat muc;

  /**
   * Instantiates a new xmpp room.
   * 
   * @param muc
   *          the muc
   * @param alias
   *          the alias
   */
  public XmppRoom(final MultiUserChat muc, final String alias) {
    this.muc = muc;
    this.alias = alias;
  }

  /**
   * Gets the alias.
   * 
   * @return the alias
   */
  public String getAlias() {
    return alias;
  }

  /**
   * Gets the muc.
   * 
   * @return the muc
   */
  public MultiUserChat getMuc() {
    return muc;
  }

  /**
   * Process message.
   * 
   * @param message
   *          the message
   */
  private void processMessage(final Message message) {
    listener.onMessage(message.getFrom(), message.getTo(), message.getBody());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.jivesoftware.smack.PacketListener#processPacket(org.jivesoftware.smack
   * .packet.Packet)
   */
  @Override
  public void processPacket(final Packet packet) {
    if (packet instanceof Message) {
      processMessage((Message) packet);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.xmpp.Room#setListener(cc.kune.core.server.xmpp.RoomListener
   * )
   */
  @Override
  public void setListener(final RoomListener listener) {
    this.listener = listener;
  }

}
