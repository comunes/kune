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

import java.util.List;

import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ContainerDTO.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContainerDTO implements IsSerializable {

  /** The absolute path. */
  private ContainerSimpleDTO[] absolutePath;

  /** The childs. */
  private List<ContainerSimpleDTO> childs;

  /** The contents. */
  private List<ContentSimpleDTO> contents;

  /** The id. */
  private Long id;

  /** The name. */
  private String name;

  /** The parent token. */
  private StateToken parentToken;

  /** The state token. */
  private StateToken stateToken;

  /** The type id. */
  private String typeId;

  /**
   * Instantiates a new container dto.
   */
  public ContainerDTO() {
  }

  /**
   * Gets the absolute path.
   * 
   * @return the absolute path
   */
  public ContainerSimpleDTO[] getAbsolutePath() {
    return absolutePath;
  }

  /**
   * Gets the child folders.
   * 
   * @return the childs
   */
  public List<ContainerSimpleDTO> getChilds() {
    return childs;
  }

  /**
   * Gets the child contents.
   * 
   * @return the contents
   */
  public List<ContentSimpleDTO> getContents() {
    return contents;
  }

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
   * Checks if is root.
   * 
   * @return true, if is root
   */
  public boolean isRoot() {
    return parentToken == null;
  }

  /**
   * Sets the absolute path.
   * 
   * @param absolutePath
   *          the new absolute path
   */
  public void setAbsolutePath(final ContainerSimpleDTO[] absolutePath) {
    this.absolutePath = absolutePath;
  }

  /**
   * Sets the childs.
   * 
   * @param childs
   *          the new childs
   */
  public void setChilds(final List<ContainerSimpleDTO> childs) {
    this.childs = childs;
  }

  /**
   * Sets the contents.
   * 
   * @param contents
   *          the new contents
   */
  public void setContents(final List<ContentSimpleDTO> contents) {
    this.contents = contents;
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
