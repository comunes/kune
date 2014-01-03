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

// TODO: Auto-generated Javadoc
/**
 * A item can be both a container or a content.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContainerSimpleDTO extends AbstractContentSimpleDTO {

  /** The name. */
  private String name;

  /** The parent token. */
  private StateToken parentToken;

  /**
   * Instantiates a new container simple dto.
   */
  public ContainerSimpleDTO() {
  }

  /**
   * Instantiates a new container simple dto.
   * 
   * @param name
   *          the name
   * @param parentToken
   *          the parent token
   * @param token
   *          the token
   * @param typeId
   *          the type id
   */
  public ContainerSimpleDTO(final String name, final StateToken parentToken, final StateToken token,
      final String typeId) {
    this.name = name;
    this.parentToken = parentToken;
    setStateToken(token);
    setTypeId(typeId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.shared.dto.AbstractContentSimpleDTO#getName()
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets the parent token.
   * 
   * @return the parent token
   */
  public StateToken getParentToken() {
    return parentToken;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.shared.dto.AbstractContentSimpleDTO#setName(java.lang.String)
   */
  @Override
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Sets the parent token.
   * 
   * @param parentToken
   *          the new parent token
   */
  public void setParentToken(final StateToken parentToken) {
    this.parentToken = parentToken;
  }

}
