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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.javax.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatException.NotAMucServiceException;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;
import org.jivesoftware.smackx.xdata.FormField.Type;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.server.properties.ChatProperties;

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
   * @throws NotConnectedException
   * @throws NoResponseException
   * @throws InterruptedException
   */
  private void configure(final MultiUserChat muc)
      throws XMPPException, NoResponseException, NotConnectedException, InterruptedException {
    final Form form = muc.getConfigurationForm();
    final Form answer = form.createAnswerForm();

    for (FormField field : form.getFields()) {
      Type type = field.getType();
      if (isVisible(type) && isNotEmpty(field) && isNotList(type)) {
        final List<String> values = new ArrayList<String>();
        for (String value : field.getValues()) {
          values.add(value);
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
      final String subject) throws NoResponseException, SmackException {
    final XmppConnection xConn = (XmppConnection) conn;
    AbstractXMPPConnection aConn = xConn.getConn();
    MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(aConn);
    final MultiUserChat muc = manager.getMultiUserChat(getRoomName(roomName));
    try {
      muc.create(Resourcepart.from(alias));
      configure(muc);
      final XmppRoom room = new XmppRoom(muc, alias);
      muc.addMessageListener(room);
      if (TextUtils.notEmpty(subject)) {
        muc.changeSubject(subject);
      }
      return room;
    } catch (XmppStringprepException | XMPPException | InterruptedException e) {
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
  public void destroyRoom(final ChatConnection conn, final String roomName)
      throws NoResponseException, NotConnectedException {
    final XmppConnection xConn = (XmppConnection) conn;
    AbstractXMPPConnection aConn = xConn.getConn();
    MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(aConn);
    final MultiUserChat muc = manager.getMultiUserChat(getRoomName(roomName));
    try {
      muc.destroy("Room removed by kune server", null);
    } catch (final XMPPException | InterruptedException e) {
      e.printStackTrace();
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
    AbstractXMPPConnection aConn = xConn.getConn();
    MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(aConn);
    final MultiUserChat muc = manager.getMultiUserChat(getRoomName(roomName));
    return muc.getOccupants() != null;
  }

  /**
   * Gets the room name.
   *
   * @param room
   *          the room
   * @return the room name
   *
   */
  private EntityBareJid getRoomName(final String room) {
    try {
      return JidCreate.entityBareFrom(room + "@" + chatProperties.getRoomHost());
    } catch (XmppStringprepException e) {
      throw new ChatException(e);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.server.xmpp.XmppManager#getRoster(cc.kune.core.server.xmpp
   * .ChatConnection)
   */
  @Override
  public Set<RosterEntry> getRoster(final ChatConnection conn) {
    final XmppConnection xConn = (XmppConnection) conn;
    final Roster roster = Roster.getInstanceFor(xConn.getConn());
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
  private boolean isNotList(final Type type) {
    return !FormField.Type.jid_multi.equals(type) && !FormField.Type.list_multi.equals(type)
        && !FormField.Type.list_single.equals(type) && !isVisible(type);
  }

  /**
   * Checks if is visible.
   *
   * @param type
   *          the type
   * @return true, if is visible
   */
  private boolean isVisible(final Type type) {
    return !FormField.Type.hidden.equals(type);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.server.xmpp.XmppManager#joinRoom(cc.kune.core.server.xmpp.
   * ChatConnection, java.lang.String, java.lang.String)
   */
  @Override
  public Room joinRoom(final ChatConnection connection, final String roomName, final String alias)
      throws NoResponseException, NotConnectedException {
    final XmppConnection xConn = (XmppConnection) connection;
    AbstractXMPPConnection aConn = xConn.getConn();
    MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(aConn);
    final MultiUserChat muc = manager.getMultiUserChat(getRoomName(roomName));
    try {
      muc.join(Resourcepart.from(alias));
      // configure(muc);
      final XmppRoom room = new XmppRoom(muc, alias);
      muc.addMessageListener(room);
      return room;
    } catch (final XMPPException | NotAMucServiceException | XmppStringprepException
        | InterruptedException e) {
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
    try {

      XMPPTCPConnectionConfiguration config;
      config = XMPPTCPConnectionConfiguration.builder()
      // .setUsernameAndPassword(userName, password)
      .setXmppDomain(getServerName()).setHost(getServerName()).setPort(5222)
      // .setDebuggerEnabled(true)
      .build();

      AbstractXMPPConnection conn = new XMPPTCPConnection(config);
      conn.connect();

      // It seems it's not necessary:
      // SASLMechanism mechanism = new SASLDigestMD5Mechanism();
      // SASLAuthentication.registerSASLMechanism(mechanism);
      // SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
      // SASLAuthentication.unBlacklistSASLMechanism("DIGEST-MD5");

      conn.login(userName, password, Resourcepart.from(resource));
      return new XmppConnection(userName, conn);
    } catch (InterruptedException | SmackException | XMPPException | IOException e) {
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
    try {
      message.setFrom(JidCreate.from(muc.getNickname()));
      muc.sendMessage(body);
    } catch (NotConnectedException | InterruptedException | XmppStringprepException e) {
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
    final AbstractXMPPConnection xmppConn = ((XmppConnection) connection).getConn();
    try {
      EntityBareJid userJid = JidCreate.entityBareFrom(userName + "@" + chatProperties.getDomain());
      Chat chat = ChatManager.getInstanceFor(xmppConn).chatWith(userJid);
      final Message message = new Message();
      message.setFrom(JidCreate.from(chatProperties.getDomain()));
      message.setTo(userJid);
      message.setType(org.jivesoftware.smack.packet.Message.Type.normal);
      message.setBody(text);
      message.setSubject("");
      chat.send(message);
      xmppConn.disconnect();
    } catch (final InterruptedException | NotConnectedException | XmppStringprepException e) {
      LOG.error("Error Delivering xmpp message to " + userName + ": ", e);
    }
  }
}
