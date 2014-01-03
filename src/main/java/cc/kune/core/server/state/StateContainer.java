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
package cc.kune.core.server.state;

import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.License;

// TODO: Auto-generated Javadoc
/**
 * The Class StateContainer.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StateContainer extends StateAbstract {

  /** The access lists. */
  private AccessLists accessLists;

  /** The container. */
  private Container container;

  /** The container rights. */
  private AccessRights containerRights;

  /** The language. */
  private I18nLanguage language;

  /** The license. */
  private License license;

  /** The tag cloud result. */
  private TagCloudResult tagCloudResult;

  /** The type id. */
  private String typeId;

  /**
   * Instantiates a new state container.
   */
  public StateContainer() {
  }

  /**
   * Gets the access lists.
   * 
   * @return the access lists
   */
  public AccessLists getAccessLists() {
    return accessLists;
  }

  /**
   * Gets the container.
   * 
   * @return the container
   */
  public Container getContainer() {
    return container;
  }

  /**
   * Gets the container rights.
   * 
   * @return the container rights
   */
  public AccessRights getContainerRights() {
    return containerRights;
  }

  /**
   * Gets the language.
   * 
   * @return the language
   */
  public I18nLanguage getLanguage() {
    return language;
  }

  /**
   * Gets the license.
   * 
   * @return the license
   */
  public License getLicense() {
    return license;
  }

  /**
   * Gets the tag cloud result.
   * 
   * @return the tag cloud result
   */
  public TagCloudResult getTagCloudResult() {
    return tagCloudResult;
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
   * Checks if is type.
   * 
   * @param type
   *          the type
   * @return true, if is type
   */
  public boolean isType(final String type) {
    return getTypeId().equals(type);
  }

  /**
   * Sets the access lists.
   * 
   * @param accessLists
   *          the new access lists
   */
  public void setAccessLists(final AccessLists accessLists) {
    this.accessLists = accessLists;
  }

  /**
   * Sets the container.
   * 
   * @param container
   *          the new container
   */
  public void setContainer(final Container container) {
    this.container = container;
  }

  /**
   * Sets the container rights.
   * 
   * @param containerRights
   *          the new container rights
   */
  public void setContainerRights(final AccessRights containerRights) {
    this.containerRights = containerRights;
  }

  /**
   * Sets the language.
   * 
   * @param language
   *          the new language
   */
  public void setLanguage(final I18nLanguage language) {
    this.language = language;
  }

  /**
   * Sets the license.
   * 
   * @param license
   *          the new license
   */
  public void setLicense(final License license) {
    this.license = license;
  }

  /**
   * Sets the tag cloud result.
   * 
   * @param tagCloudResult
   *          the new tag cloud result
   */
  public void setTagCloudResult(final TagCloudResult tagCloudResult) {
    this.tagCloudResult = tagCloudResult;
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.state.StateAbstract#toString()
   */
  @Override
  public String toString() {
    return "State[" + getStateToken() + "/" + getTypeId() + "]";
  }
}
