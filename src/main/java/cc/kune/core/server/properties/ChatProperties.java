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
package cc.kune.core.server.properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * Syntactic sugar!.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class ChatProperties {

  /** The properties. */
  private final KuneProperties properties;

  /**
   * Instantiates a new chat properties.
   * 
   * @param properties
   *          the properties
   */
  @Inject
  public ChatProperties(final KuneProperties properties) {
    this.properties = properties;
  }

  /**
   * Gets the admin jid.
   * 
   * @return the admin jid
   */
  public String getAdminJID() {
    return properties.get(KuneProperties.SITE_ADMIN_SHORTNAME);
  }

  /**
   * Gets the admin passwd.
   * 
   * @return the admin passwd
   */
  public String getAdminPasswd() {
    return properties.get(KuneProperties.SITE_ADMIN_PASSWD);
  }

  /**
   * Gets the domain.
   * 
   * @return the domain
   */
  public String getDomain() {
    return properties.get(KuneProperties.CHAT_DOMAIN);
  }

  /**
   * Gets the http base.
   * 
   * @return the http base
   */
  public String getHttpBase() {
    return properties.get(KuneProperties.CHAT_HTTP_BASE);
  }

  /**
   * Gets the room host.
   * 
   * @return the room host
   */
  public String getRoomHost() {
    return properties.get(KuneProperties.CHAT_ROOM_HOST);
  }
}
