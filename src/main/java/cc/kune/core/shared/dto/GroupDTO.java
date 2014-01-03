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

import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupDTO implements IsSerializable {

  /** The admission type. */
  AdmissionType admissionType;

  /** The background image. */
  private String backgroundImage;

  /** The compound name. */
  private String compoundName;

  /** The created on. */
  private Long createdOn;

  /** The default content. */
  private ContentSimpleDTO defaultContent;

  /** The default license. */
  private LicenseDTO defaultLicense;

  /** The group type. */
  private GroupType groupType;

  /** The has background. */
  private boolean hasBackground;

  /** The has logo. */
  private boolean hasLogo;

  /** The id. */
  private Long id;

  /** The long name. */
  private String longName;

  /** The short name. */
  private String shortName;

  /** The state token. */
  private StateToken stateToken;

  /** The workspace theme. */
  private String workspaceTheme;

  /**
   * Instantiates a new group dto.
   */
  public GroupDTO() {
    this(null, null, GroupType.ORGANIZATION);
  }

  /**
   * Instantiates a new group dto.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   * @param type
   *          the type
   */
  public GroupDTO(final String shortName, final String longName, final GroupType type) {
    this.shortName = shortName;
    this.longName = longName;
    this.groupType = type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
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

  /**
   * Gets the admission type.
   * 
   * @return the admission type
   */
  public AdmissionType getAdmissionType() {
    return admissionType;
  }

  /**
   * Gets the background image.
   * 
   * @return the background image
   */
  public String getBackgroundImage() {
    return backgroundImage;
  }

  /**
   * Gets the compound name.
   * 
   * @return the compound name
   */
  public String getCompoundName() {
    if (compoundName == null) {
      compoundName = !longName.equals(shortName) ? longName + " (" + shortName + ")" : shortName;
    }
    return compoundName;
  }

  /**
   * Gets the created on.
   * 
   * @return the created on
   */
  public Long getCreatedOn() {
    return createdOn;
  }

  /**
   * Gets the default content.
   * 
   * @return the default content
   */
  public ContentSimpleDTO getDefaultContent() {
    return defaultContent;
  }

  /**
   * Gets the default license.
   * 
   * @return the default license
   */
  public LicenseDTO getDefaultLicense() {
    return defaultLicense;
  }

  /**
   * Gets the group type.
   * 
   * @return the group type
   */
  public GroupType getGroupType() {
    return groupType;
  }

  /**
   * Gets the checks for background.
   * 
   * @return the checks for background
   */
  public boolean getHasBackground() {
    return hasBackground;
  }

  /**
   * Gets the checks for logo.
   * 
   * @return the checks for logo
   */
  public boolean getHasLogo() {
    return hasLogo;
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
   * Gets the long name.
   * 
   * @return the long name
   */
  public String getLongName() {
    return longName;
  }

  /**
   * Gets the short name.
   * 
   * @return the short name
   */
  public String getShortName() {
    return shortName;
  }

  /**
   * Gets the state token.
   * 
   * @return the state token
   */
  public StateToken getStateToken() {
    if (stateToken == null) {
      stateToken = new StateToken(shortName);
    }
    return stateToken;
  }

  /**
   * Gets the workspace theme.
   * 
   * @return the workspace theme
   */
  public String getWorkspaceTheme() {
    return workspaceTheme;
  }

  /**
   * Checks for background.
   * 
   * @return true, if successful
   */
  public boolean hasBackground() {
    return getHasBackground();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (shortName == null ? 0 : shortName.hashCode());
    return result;
  }

  /**
   * Checks for logo.
   * 
   * @return true, if successful
   */
  public boolean hasLogo() {
    return getHasLogo();
  }

  /**
   * Checks if is not personal.
   * 
   * @return true, if is not personal
   */
  public boolean isNotPersonal() {
    return !isPersonal();
  }

  /**
   * Checks if is personal.
   * 
   * @return true, if is personal
   */
  public boolean isPersonal() {
    return groupType.equals(GroupType.PERSONAL);
  }

  /**
   * Sets the admission type.
   * 
   * @param admissionType
   *          the new admission type
   */
  public void setAdmissionType(final AdmissionType admissionType) {
    this.admissionType = admissionType;
  }

  /**
   * Sets the background image.
   * 
   * @param backgroundImage
   *          the new background image
   */
  public void setBackgroundImage(final String backgroundImage) {
    this.backgroundImage = backgroundImage;
  }

  /**
   * Sets the created on.
   * 
   * @param createdOn
   *          the new created on
   */
  public void setCreatedOn(final Long createdOn) {
    this.createdOn = createdOn;
  }

  /**
   * Sets the default content.
   * 
   * @param defaultContent
   *          the new default content
   */
  public void setDefaultContent(final ContentSimpleDTO defaultContent) {
    this.defaultContent = defaultContent;
  }

  /**
   * Sets the default license.
   * 
   * @param defaultLicense
   *          the new default license
   */
  public void setDefaultLicense(final LicenseDTO defaultLicense) {
    this.defaultLicense = defaultLicense;
  }

  /**
   * Sets the group type.
   * 
   * @param groupType
   *          the new group type
   */
  public void setGroupType(final GroupType groupType) {
    this.groupType = groupType;
  }

  /**
   * Sets the checks for background.
   * 
   * @param hasBackground
   *          the new checks for background
   */
  public void setHasBackground(final boolean hasBackground) {
    this.hasBackground = hasBackground;
  }

  /**
   * Sets the checks for logo.
   * 
   * @param hasLogo
   *          the new checks for logo
   */
  public void setHasLogo(final boolean hasLogo) {
    this.hasLogo = hasLogo;
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
   * Sets the long name.
   * 
   * @param name
   *          the new long name
   */
  public void setLongName(final String name) {
    this.longName = name;
  }

  /**
   * Sets the short name.
   * 
   * @param shortName
   *          the new short name
   */
  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }

  /**
   * Sets the workspace theme.
   * 
   * @param workspaceTheme
   *          the new workspace theme
   */
  public void setWorkspaceTheme(final String workspaceTheme) {
    this.workspaceTheme = workspaceTheme;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "GroupDTO[" + shortName + "]";
  }
}
