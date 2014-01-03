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
package cc.kune.core.shared.dto;

import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractContentSimpleDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractContentSimpleDTO implements IsSerializable {

  /** The id. */
  protected Long id;

  /** The state token. */
  private StateToken stateToken;

  /** The type id. */
  private String typeId;

  /**
   * Gets the id.
   * 
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public abstract String getName();

  /**
   * Gets the state token.
   * 
   * @return the state token
   */
  public StateToken getStateToken() {
    return stateToken;
  }

  /**
   * Gets the type id.
   * 
   * @return the type id
   */
  public String getTypeId() {
    return typeId;
  }

  /**
   * Sets the id.
   * 
   * @param id
   *          the new id
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Sets the name.
   * 
   * @param name
   *          the new name
   */
  public abstract void setName(final String name);

  /**
   * Sets the state token.
   * 
   * @param stateToken
   *          the new state token
   */
  public void setStateToken(final StateToken stateToken) {
    this.stateToken = stateToken;
  }

  /**
   * Sets the type id.
   * 
   * @param typeId
   *          the new type id
   */
  public void setTypeId(final String typeId) {
    this.typeId = typeId;
  }

}
