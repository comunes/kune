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
package cc.kune.chat.server;

import cc.kune.chat.shared.ChatToolConstants;
import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.errors.GroupNotFoundException;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.xmpp.ChatConnection;
import cc.kune.core.server.xmpp.XmppManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class ChatManagerDefault.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class ChatManagerDefault implements ChatManager {

  /** The creation service. */
  private final CreationService creationService;
  
  /** The group manager. */
  private final GroupManager groupManager;
  
  /** The xmpp manager. */
  private final XmppManager xmppManager;

  /**
   * Instantiates a new chat manager default.
   *
   * @param xmppManager the xmpp manager
   * @param groupManager the group manager
   * @param creationService the creation service
   */
  @Inject
  public ChatManagerDefault(final XmppManager xmppManager, final GroupManager groupManager,
      final CreationService creationService) {
    this.xmppManager = xmppManager;
    this.groupManager = groupManager;
    this.creationService = creationService;
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.server.ChatManager#addRoom(java.lang.String, cc.kune.domain.User, cc.kune.core.shared.domain.utils.StateToken, java.lang.String, java.lang.String)
   */
  @Override
  public Container addRoom(final String userHash, final User user, final StateToken parentToken,
      final String roomName, final String subject) {
    final String groupShortName = parentToken.getGroup();
    final String userShortName = user.getShortName();
    final ChatConnection connection = xmppManager.login(userShortName, userHash, userHash);
    if (!xmppManager.existRoom(connection, roomName)) {
      xmppManager.createRoom(connection, roomName, userShortName + userHash, subject);
      xmppManager.disconnect(connection);
    }
    try {
      return creationService.createFolder(groupManager.findByShortName(groupShortName),
          ContentUtils.parseId(parentToken.getFolder()), roomName, user.getLanguage(),
          ChatToolConstants.TYPE_ROOM);
    } catch (final ContentNotFoundException e) {
      xmppManager.destroyRoom(connection, roomName);
      throw new ContentNotFoundException();
    } catch (final AccessViolationException e) {
      xmppManager.destroyRoom(connection, roomName);
      throw new AccessViolationException();
    } catch (final GroupNotFoundException e) {
      xmppManager.destroyRoom(connection, roomName);
      throw new GroupNotFoundException();
    }
  }
}
