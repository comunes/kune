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
 \*/
package cc.kune.core.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolSimpleDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolSimpleDTO implements IsSerializable {

  /** The name. */
  private String name;

  /** The root name. */
  private String rootName;

  /**
   * Instantiates a new tool simple dto.
   */
  public ToolSimpleDTO() {
    this(null, null);
  }

  /**
   * Instantiates a new tool simple dto.
   * 
   * @param name
   *          the name
   * @param rootName
   *          the root name
   */
  public ToolSimpleDTO(final String name, final String rootName) {
    this.name = name;
    this.rootName = rootName;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the root name.
   * 
   * @return the root name
   */
  public String getRootName() {
    return rootName;
  }

  /**
   * Sets the name.
   * 
   * @param name
   *          the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Sets the root name.
   * 
   * @param rootName
   *          the new root name
   */
  public void setRootName(final String rootName) {
    this.rootName = rootName;
  }
}
