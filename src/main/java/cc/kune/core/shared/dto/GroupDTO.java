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

import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupDTO implements IsSerializable {

  AdmissionType admissionType;
  private String backgroundImage;
  private String compoundName;
  private Long createdOn;
  private ContentSimpleDTO defaultContent;
  private LicenseDTO defaultLicense;
  private GroupType groupType;
  private boolean hasBackground;
  private boolean hasLogo;
  private Long id;
  private String longName;
  private String shortName;
  private StateToken stateToken;
  private String workspaceTheme;

  public GroupDTO() {
    this(null, null, GroupType.ORGANIZATION);
  }

  public GroupDTO(final String shortName, final String longName, final GroupType type) {
    this.shortName = shortName;
    this.longName = longName;
    this.groupType = type;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final GroupDTO other = (GroupDTO) obj;
    if (shortName == null) {
      if (other.shortName != null) {
        return false;
      }
    } else if (!shortName.equals(other.shortName)) {
      return false;
    }
    return true;
  }

  public AdmissionType getAdmissionType() {
    return admissionType;
  }

  public String getBackgroundImage() {
    return backgroundImage;
  }

  public String getCompoundName() {
    if (compoundName == null) {
      compoundName = !longName.equals(shortName) ? longName + " (" + shortName + ")" : shortName;
    }
    return compoundName;
  }

  public Long getCreatedOn() {
    return createdOn;
  }

  public ContentSimpleDTO getDefaultContent() {
    return defaultContent;
  }

  public LicenseDTO getDefaultLicense() {
    return defaultLicense;
  }

  public GroupType getGroupType() {
    return groupType;
  }

  public boolean getHasBackground() {
    return hasBackground;
  }

  public boolean getHasLogo() {
    return hasLogo;
  }

  public Long getId() {
    return id;
  }

  public String getLongName() {
    return longName;
  }

  public String getShortName() {
    return shortName;
  }

  public StateToken getStateToken() {
    if (stateToken == null) {
      stateToken = new StateToken(shortName);
    }
    return stateToken;
  }

  public String getWorkspaceTheme() {
    return workspaceTheme;
  }

  public boolean hasBackground() {
    return getHasBackground();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (shortName == null ? 0 : shortName.hashCode());
    return result;
  }

  public boolean hasLogo() {
    return getHasLogo();
  }

  public boolean isNotPersonal() {
    return !isPersonal();
  }

  public boolean isPersonal() {
    return groupType.equals(GroupType.PERSONAL);
  }

  public void setAdmissionType(final AdmissionType admissionType) {
    this.admissionType = admissionType;
  }

  public void setBackgroundImage(final String backgroundImage) {
    this.backgroundImage = backgroundImage;
  }

  public void setCreatedOn(final Long createdOn) {
    this.createdOn = createdOn;
  }

  public void setDefaultContent(final ContentSimpleDTO defaultContent) {
    this.defaultContent = defaultContent;
  }

  public void setDefaultLicense(final LicenseDTO defaultLicense) {
    this.defaultLicense = defaultLicense;
  }

  public void setGroupType(final GroupType groupType) {
    this.groupType = groupType;
  }

  public void setHasBackground(final boolean hasBackground) {
    this.hasBackground = hasBackground;
  }

  public void setHasLogo(final boolean hasLogo) {
    this.hasLogo = hasLogo;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setLongName(final String name) {
    this.longName = name;
  }

  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }

  public void setWorkspaceTheme(final String workspaceTheme) {
    this.workspaceTheme = workspaceTheme;
  }

  @Override
  public String toString() {
    return "GroupDTO[" + shortName + "]";
  }
}
