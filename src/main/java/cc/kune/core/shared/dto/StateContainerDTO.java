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

import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.utils.AccessRights;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class StateContainerDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StateContainerDTO extends StateAbstractDTO implements IsSerializable, HasContent {

  /** The access lists. */
  private AccessListsDTO accessLists;

  /** The container. */
  private ContainerDTO container;

  /** The container rights. */
  private AccessRights containerRights;

  /** The language. */
  private I18nLanguageDTO language;

  /** The license. */
  private LicenseDTO license;

  /** The tag cloud result. */
  private TagCloudResult tagCloudResult;

  /** The type id. */
  private String typeId;

  /**
   * Instantiates a new state container dto.
   */
  public StateContainerDTO() {
  }

  /**
   * Gets the access lists.
   * 
   * @return the access lists
   */
  public AccessListsDTO getAccessLists() {
    return accessLists;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.shared.dto.HasContent#getContainer()
   */
  @Override
  public ContainerDTO getContainer() {
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
  public I18nLanguageDTO getLanguage() {
    return language;
  }

  /**
   * Gets the license.
   * 
   * @return the license
   */
  public LicenseDTO getLicense() {
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.shared.dto.HasContent#getTypeId()
   */
  @Override
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
  public void setAccessLists(final AccessListsDTO accessLists) {
    this.accessLists = accessLists;
  }

  /**
   * Sets the container.
   * 
   * @param container
   *          the new container
   */
  public void setContainer(final ContainerDTO container) {
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
  public void setLanguage(final I18nLanguageDTO language) {
    this.language = language;
  }

  /**
   * Sets the license.
   * 
   * @param license
   *          the new license
   */
  public void setLicense(final LicenseDTO license) {
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.shared.dto.HasContent#setTypeId(java.lang.String)
   */
  @Override
  public void setTypeId(final String typeId) {
    this.typeId = typeId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.shared.dto.StateAbstractDTO#toString()
   */
  @Override
  public String toString() {
    return "StateDTO[" + getStateToken() + "/" + getTypeId() + "]";
  }

}
