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
package cc.kune.core.server.state;

import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.License;

public class StateContainer extends StateAbstract {

  private AccessLists accessLists;
  private Container container;
  private AccessRights containerRights;
  private I18nLanguage language;
  private License license;
  private TagCloudResult tagCloudResult;
  private String typeId;

  public StateContainer() {
  }

  public AccessLists getAccessLists() {
    return accessLists;
  }

  public Container getContainer() {
    return container;
  }

  public AccessRights getContainerRights() {
    return containerRights;
  }

  public I18nLanguage getLanguage() {
    return language;
  }

  public License getLicense() {
    return license;
  }

  public TagCloudResult getTagCloudResult() {
    return tagCloudResult;
  }

  public String getTypeId() {
    return typeId;
  }

  public boolean isType(final String type) {
    return getTypeId().equals(type);
  }

  public void setAccessLists(final AccessLists accessLists) {
    this.accessLists = accessLists;
  }

  public void setContainer(final Container container) {
    this.container = container;
  }

  public void setContainerRights(final AccessRights containerRights) {
    this.containerRights = containerRights;
  }

  public void setLanguage(final I18nLanguage language) {
    this.language = language;
  }

  public void setLicense(final License license) {
    this.license = license;
  }

  public void setTagCloudResult(final TagCloudResult tagCloudResult) {
    this.tagCloudResult = tagCloudResult;
  }

  public void setTypeId(final String typeId) {
    this.typeId = typeId;
  }

  @Override
  public String toString() {
    return "State[" + getStateToken() + "/" + getTypeId() + "]";
  }
}
