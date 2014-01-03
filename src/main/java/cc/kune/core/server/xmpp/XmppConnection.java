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

import org.jivesoftware.smack.XMPPConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class XmppConnection.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class XmppConnection implements ChatConnection {

  /** The conn. */
  private final XMPPConnection conn;

  /** The user name. */
  private final String userName;

  /**
   * Instantiates a new xmpp connection.
   * 
   * @param userName
   *          the user name
   * @param conn
   *          the conn
   */
  public XmppConnection(final String userName, final XMPPConnection conn) {
    this.userName = userName;
    this.conn = conn;
  }

  /**
   * Gets the conn.
   * 
   * @return the conn
   */
  public XMPPConnection getConn() {
    return conn;
  }

  /**
   * Gets the user name.
   * 
   * @return the user name
   */
  public String getUserName() {
    return userName;
  }

}
