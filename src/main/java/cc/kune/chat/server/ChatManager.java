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

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface ChatManager.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface ChatManager {

  /**
   * Adds the room.
   *
   * @param userHash the user hash
   * @param user the user
   * @param parentToken the parent token
   * @param roomName the room name
   * @param subject the subject
   * @return the container
   */
  Container addRoom(String userHash, User user, StateToken parentToken, String roomName, String subject);

}
