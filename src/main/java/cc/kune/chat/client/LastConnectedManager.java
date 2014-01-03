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

/**
 * The Interface LastConnectedManager is used to collect information about users
 * presence (similar to Whatsapp "last seen" functionality.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface LastConnectedManager {

  /**
   * Gets presence info about some user
   * 
   * @param username
   *          the username
   * @param standalone
   *          the standalone
   * @return the string
   */
  String get(String username, boolean standalone);

  /**
   * Updates the presence about this user (normally via xmpp info)
   * 
   * @param username
   *          the username
   * @param lastConnected
   *          the last connected
   */
  void update(String username, Long lastConnected);

}
