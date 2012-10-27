/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
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

public class StateContainerDTO extends StateAbstractDTO implements IsSerializable, HasContent {

  private AccessListsDTO accessLists;
  private ContainerDTO container;
  private AccessRights containerRights;
  private I18nLanguageDTO language;
  private LicenseDTO license;
  private TagCloudResult tagCloudResult;
  private String typeId;

  public StateContainerDTO() {
  }

  public AccessListsDTO getAccessLists() {
    return accessLists;
  }

  @Override
  public ContainerDTO getContainer() {
    return container;
  }

  public AccessRights getContainerRights() {
    return containerRights;
  }

  public I18nLanguageDTO getLanguage() {
    return language;
  }

  public LicenseDTO getLicense() {
    return license;
  }

  public TagCloudResult getTagCloudResult() {
    return tagCloudResult;
  }

  @Override
  public String getTypeId() {
    return typeId;
  }

  public boolean isType(final String type) {
    return getTypeId().equals(type);
  }

  public void setAccessLists(final AccessListsDTO accessLists) {
    this.accessLists = accessLists;
  }

  public void setContainer(final ContainerDTO container) {
    this.container = container;
  }

  public void setContainerRights(final AccessRights containerRights) {
    this.containerRights = containerRights;
  }

  public void setLanguage(final I18nLanguageDTO language) {
    this.language = language;
  }

  public void setLicense(final LicenseDTO license) {
    this.license = license;
  }

  public void setTagCloudResult(final TagCloudResult tagCloudResult) {
    this.tagCloudResult = tagCloudResult;
  }

  @Override
  public void setTypeId(final String typeId) {
    this.typeId = typeId;
  }

  @Override
  public String toString() {
    return "StateDTO[" + getStateToken() + "/" + getTypeId() + "]";
  }

}
