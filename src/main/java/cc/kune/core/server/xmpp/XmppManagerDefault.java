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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.server.properties.ChatProperties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class XmppManagerDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class XmppManagerDefault implements XmppManager {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(XmppManagerDefault.class);

  /** The chat properties. */
  private final ChatProperties chatProperties;

  /**
   * Instantiates a new xmpp manager default.
   * 
   * @param chatProperties
   *          the chat properties
   */
  @Inject
  public XmppManagerDefault(final ChatProperties chatProperties) {
    this.chatProperties = chatProperties;
  }

  /**
   * Configure.
   * 
   * @param muc
   *          the muc
   * @throws XMPPException
   *           the xMPP exception
   */
  private void configure(final MultiUserChat muc) throws XMPPException {
    final Form form = muc.getConfigurationForm();
    final Form answer = form.createAnswerForm();

    for (final Iterator<FormField> fields = form.getFields(); fields.hasNext();) {
      final FormField field = fields.next();
      final String type = field.getType();
      if (isVisible(type) && isNotEmpty(field) && isNotList(type)) {
        final List<String> values = new ArrayList<String>();
        for (final Iterator<String> it = field.getValues(); it.hasNext();) {
          values.add(it.next());
        }
        answer.setAnswer(field.getVariable(), values);
      }
    }
    answer.setAnswer("muc#roomconfig_persistentroom", true);
    muc.sendConfigurationForm(answer);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.xmpp.XmppManager#createRoom(cc.kune.core.server.xmpp
   * .ChatConnection, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public Room createRoom(final ChatConnection conn, final String roomName, final String alias,
      final String subject) {
    final XmppConnection xConn = (XmppConnection) conn;
    final MultiUserChat muc = new MultiUserChat(xConn.getConn(), getRoomName(roomName));
    try {
      muc.create(alias);
      configure(muc);
      final XmppRoom room = new XmppRoom(muc, alias);
      muc.addMessageListener(room);
      if (TextUtils.notEmpty(subject)) {
        muc.changeSubject(subject);
      }
      return room;
    } catch (final XMPPException e) {
      throw new ChatException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.xmpp.XmppManager#destroyRoom(cc.kune.core.server.xmpp
   * .ChatConnection, java.lang.String)
   */
  @Override
  public void destroyRoom(final ChatConnection conn, final String roomName) {
    final XmppConnection xConn = (XmppConnection) conn;
    final MultiUserChat muc = new MultiUserChat(xConn.getConn(), getRoomName(roomName));
    try {

      muc.destroy("Room removed by kune server", "");
    } catch (final XMPPException e) {
      throw new ChatException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.xmpp.XmppManager#disconnect(cc.kune.core.server.xmpp
   * .ChatConnection)
   */
  @Override
  public void disconnect(final ChatConnection connection) {
    final XmppConnection xConn = (XmppConnection) connection;
    xConn.getConn().disconnect();

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.xmpp.XmppManager#existRoom(cc.kune.core.server.xmpp
   * .ChatConnection, java.lang.String)
   */
  @Override
  public boolean existRoom(final ChatConnection conn, final String roomName) {
    final XmppConnection xConn = (XmppConnection) conn;
    final MultiUserChat muc = new MultiUserChat(xConn.getConn(), getRoomName(roomName));
    final Iterator<String> occupants = muc.getOccupants();
    return occupants != null;
  }

  /**
   * Gets the room name.
   * 
   * @param room
   *          the room
   * @return the room name
   */
  private String getRoomName(final String room) {
    return room + "@" + chatProperties.getRoomHost();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.xmpp.XmppManager#getRoster(cc.kune.core.server.xmpp
   * .ChatConnection)
   */
  @Override
  public Collection<RosterEntry> getRoster(final ChatConnection conn) {
    final XmppConnection xConn = (XmppConnection) conn;
    final Roster roster = xConn.getConn().getRoster();
    return roster.getEntries();
  }

  /**
   * Gets the server name.
   * 
   * @return the server name
   */
  private String getServerName() {
    return chatProperties.getDomain();
  }

  /**
   * Checks if is not empty.
   * 
   * @param field
   *          the field
   * @return true, if is not empty
   */
  private boolean isNotEmpty(final FormField field) {
    return field.getVariable() != null;
  }

  /**
   * Checks if is not list.
   * 
   * @param type
   *          the type
   * @return true, if is not list
   */
  private boolean isNotList(final String type) {
    return !FormField.TYPE_JID_MULTI.equals(type) && !FormField.TYPE_LIST_MULTI.equals(type)
        && !FormField.TYPE_LIST_SINGLE.equals(type) && !isVisible(type);
  }

  /**
   * Checks if is visible.
   * 
   * @param type
   *          the type
   * @return true, if is visible
   */
  private boolean isVisible(final String type) {
    return !FormField.TYPE_HIDDEN.equals(type);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.xmpp.XmppManager#joinRoom(cc.kune.core.server.xmpp.
   * ChatConnection, java.lang.String, java.lang.String)
   */
  @Override
  public Room joinRoom(final ChatConnection connection, final String roomName, final String alias) {
    final XmppConnection xConn = (XmppConnection) connection;
    final MultiUserChat muc = new MultiUserChat(xConn.getConn(), getRoomName(roomName));
    try {
      muc.join(alias);
      // configure(muc);
      final XmppRoom room = new XmppRoom(muc, alias);
      muc.addMessageListener(room);
      return room;
    } catch (final XMPPException e) {
      throw new ChatException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.xmpp.XmppManager#login(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  @Override
  public ChatConnection login(final String userName, final String password, final String resource) {
    final ConnectionConfiguration config = new ConnectionConfiguration(getServerName(), 5222);
    final XMPPConnection conn = new XMPPConnection(config);
    try {
      conn.connect();
      conn.login(userName, password, resource);
      return new XmppConnection(userName, conn);
    } catch (final XMPPException e) {
      throw new ChatException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.xmpp.XmppManager#sendMessage(cc.kune.core.server.xmpp
   * .Room, java.lang.String)
   */
  @Override
  public void sendMessage(final Room room, final String body) {
    final XmppRoom xAccess = (XmppRoom) room;
    final MultiUserChat muc = xAccess.getMuc();
    final Message message = muc.createMessage();
    message.setBody(body);
    message.setFrom(muc.getNickname());
    try {
      muc.sendMessage(body);
    } catch (final XMPPException e) {
      throw new ChatException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.xmpp.XmppManager#sendMessage(java.lang.String,
   * java.lang.String)
   */
  @Override
  public void sendMessage(final String userName, final String text) {
    final ChatConnection connection = login(chatProperties.getAdminJID(),
        chatProperties.getAdminPasswd(), "kuneserveradmin" + System.currentTimeMillis());
    final XMPPConnection xmppConn = ((XmppConnection) connection).getConn();
    final String userJid = userName + "@" + chatProperties.getDomain();
    final Chat newChat = xmppConn.getChatManager().createChat(userJid, new MessageListener() {
      @Override
      public void processMessage(final Chat arg0, final Message arg1) {
        LOG.info("Sended message: " + arg1.getBody());
      }
    });
    try {
      final Message message = new Message();
      message.setFrom(chatProperties.getDomain());
      message.setTo(userJid);
      message.setType(Type.normal);
      message.setBody(text);
      message.setSubject("");
      newChat.sendMessage(message);
    } catch (final Exception e) {
      LOG.error("Error Delivering xmpp message to " + userName + ": ", e);
    }
    xmppConn.disconnect();
  }
}
