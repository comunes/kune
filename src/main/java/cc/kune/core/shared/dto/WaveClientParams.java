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
package cc.kune.core.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class WaveClientParams.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WaveClientParams implements IsSerializable {
  
  /** The client flags. */
  private String clientFlags;
  
  /** The session json. */
  private String sessionJSON;
  
  /** The websocket address. */
  private String websocketAddress;

  /**
   * Instantiates a new wave client params.
   */
  public WaveClientParams() {
  }

  /**
   * Instantiates a new wave client params.
   *
   * @param sessionJSON the session json
   * @param clientFlags the client flags
   * @param websocketAddress the websocket address
   */
  public WaveClientParams(final String sessionJSON, final String clientFlags, String websocketAddress) {
    this.sessionJSON = sessionJSON;
    this.clientFlags = clientFlags;
    this.websocketAddress = websocketAddress;
  }

  /**
   * Gets the client flags.
   *
   * @return the client flags
   */
  public String getClientFlags() {
    return clientFlags;
  }

  /**
   * Gets the session json.
   *
   * @return the session json
   */
  public String getSessionJSON() {
    return sessionJSON;
  }

  /**
   * Sets the client flags.
   *
   * @param clientFlags the new client flags
   */
  public void setClientFlags(final String clientFlags) {
    this.clientFlags = clientFlags;
  }

  /**
   * Sets the session json.
   *
   * @param sessionJSON the new session json
   */
  public void setSessionJSON(final String sessionJSON) {
    this.sessionJSON = sessionJSON;
  }

  /**
   * Gets the websocket address.
   *
   * @return the websocket address
   */
  public String getWebsocketAddress() {
    return websocketAddress;
  }

  /**
   * Sets the websocket address.
   *
   * @param websocketAddress the new websocket address
   */
  public void setWebsocketAddress(String websocketAddress) {
    this.websocketAddress = websocketAddress;
  }

}
