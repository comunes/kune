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
package cc.kune.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;

import cc.kune.core.shared.CoreConstants;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.utils.HasId;

// TODO: Auto-generated Javadoc
/**
 * The Class Group.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Indexed
@Table(name = "groups")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group implements HasId {

  // see: http://docs.codehaus.org/display/PICO/Good+Citizen:
  // Never expect or return null
  /** The Constant NO_GROUP. */
  public static final Group NO_GROUP = null;
  // public static final String PROPS_ID = "groupprops";

  /** The admission type. */
  @org.hibernate.annotations.Index(name = "admissionType")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  AdmissionType admissionType;

  /** The background image. */
  private String backgroundImage;

  /** The background mime. */
  private String backgroundMime;

  /** The created on. */
  @org.hibernate.annotations.Index(name = "createdOn")
  @Basic(optional = false)
  private final Long createdOn;

  /** The default content. */
  @OneToOne
  private Content defaultContent;

  /** The default license. */
  @OneToOne
  private License defaultLicense;

  /** The group type. */
  @org.hibernate.annotations.Index(name = "groupType")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  GroupType groupType;

  /** The id. */
  @Id
  @DocumentId
  @GeneratedValue
  private Long id;

  /** The logo. */
  @Lob
  private byte[] logo;

  /** The logo last modified time. */
  @Basic
  private Long logoLastModifiedTime;

  /** The logo mime. */
  @Embedded
  private BasicMimeType logoMime;

  /** The long name. */
  @Field(index = Index.YES, store = Store.NO)
  @Column(nullable = false, unique = true)
  @org.hibernate.annotations.Index(name = "longName")
  @Length(min = 3, max = CoreConstants.MAX_LONG_NAME_SIZE, message = "The longName must be between 3 and "
      + CoreConstants.MAX_LONG_NAME_SIZE + " characters of length")
  private String longName;

  /** The short name. */
  @Field(index = Index.YES, store = Store.NO)
  @Column(unique = true)
  @Length(min = 3, max = CoreConstants.MAX_SHORT_NAME_SIZE, message = "The shortname must be between 3 and 15 characters of length")
  @Pattern(regexp = "^[a-z0-9]+$", message = "The name must be between 3 and "
      + CoreConstants.MAX_SHORT_NAME_SIZE
      + " lowercase characters. It can only contain Western characters, numbers, and dashes")
  @org.hibernate.annotations.Index(name = "shortName")
  private String shortName;

  /** The social network. */
  @OneToOne(cascade = CascadeType.ALL)
  private SocialNetwork socialNetwork;

  /** The tools config. */
  @MapKeyColumn(name = "mapkey")
  @OneToMany
  private final Map<String, ToolConfiguration> toolsConfig;

  /** The workspace theme. */
  private String workspaceTheme;

  /**
   * Instantiates a new group.
   */
  public Group() {
    this(null, null, null, null);
  }

  /**
   * Instantiates a new group.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   */
  public Group(final String shortName, final String longName) {
    this(shortName, longName, null, GroupType.PROJECT);
  }

  /**
   * Instantiates a new group.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   * @param defaultLicense
   *          the default license
   * @param type
   *          the type
   */
  public Group(final String shortName, final String longName, final License defaultLicense,
      final GroupType type) {
    this.shortName = shortName;
    this.longName = longName;
    this.toolsConfig = new HashMap<String, ToolConfiguration>();
    this.socialNetwork = new SocialNetwork();
    this.defaultLicense = defaultLicense;
    this.groupType = type;
    this.admissionType = AdmissionType.Moderated;
    this.createdOn = System.currentTimeMillis();
    this.logoLastModifiedTime = System.currentTimeMillis();
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
    final Group other = (Group) obj;
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
   * Exist tool config.
   * 
   * @param toolName
   *          the tool name
   * @return true, if successful
   */
  public boolean existToolConfig(final String toolName) {
    return toolsConfig.get(toolName) != null;
  }

  /**
   * Gets the access lists.
   * 
   * @return the access lists
   */
  @Transient
  public AccessLists getAccessLists() {
    return getSocialNetwork().getAccessLists();
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
   * Gets the background mime.
   * 
   * @return the background mime
   */
  public String getBackgroundMime() {
    return backgroundMime;
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
  public Content getDefaultContent() {
    return defaultContent;
  }

  /**
   * Gets the default license.
   * 
   * @return the default license
   */
  public License getDefaultLicense() {
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
    return hasBackground();
  }

  /**
   * Gets the checks for logo.
   * 
   * @return the checks for logo
   */
  public boolean getHasLogo() {
    return hasLogo();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.domain.utils.HasId#getId()
   */
  @Override
  public Long getId() {
    return id;
  }

  /**
   * Gets the logo.
   * 
   * @return the logo
   */
  public byte[] getLogo() {
    return logo;
  }

  /**
   * Gets the logo last modified time.
   * 
   * @return the logo last modified time
   */
  public Long getLogoLastModifiedTime() {
    if (logoLastModifiedTime == null) {
      return System.currentTimeMillis();
    }
    return logoLastModifiedTime;
  }

  /**
   * Gets the logo mime.
   * 
   * @return the logo mime
   */
  public BasicMimeType getLogoMime() {
    return logoMime;
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
   * Gets the root.
   * 
   * @param toolName
   *          the tool name
   * @return the root
   */
  public Container getRoot(final String toolName) {
    return toolsConfig.get(toolName).getRoot();
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
   * Gets the social network.
   * 
   * @return the social network
   */
  public SocialNetwork getSocialNetwork() {
    return socialNetwork;
  }

  /**
   * Gets the state token.
   * 
   * @return the state token
   */
  @Transient
  public StateToken getStateToken() {
    return new StateToken(shortName);
  }

  /**
   * Gets the tool configuration.
   * 
   * @param name
   *          the name
   * @return the tool configuration
   */
  public ToolConfiguration getToolConfiguration(final String name) {
    return toolsConfig.get(name);
  }

  /**
   * Gets the tools config.
   * 
   * @return the tools config
   */
  public Map<String, ToolConfiguration> getToolsConfig() {
    return toolsConfig;
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
  @Transient
  public boolean hasBackground() {
    return backgroundImage != null;
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
  @Transient
  public boolean hasLogo() {
    return (logo != null && logo.length > 0 && logoMime != null);
  }

  /**
   * Checks if is personal.
   * 
   * @return true, if is personal
   */
  public boolean isPersonal() {
    return getGroupType().equals(GroupType.PERSONAL);
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
   * Sets the background mime.
   * 
   * @param backgroundMime
   *          the new background mime
   */
  public void setBackgroundMime(final String backgroundMime) {
    this.backgroundMime = backgroundMime;
  }

  /**
   * Sets the default content.
   * 
   * @param defaultContent
   *          the new default content
   */
  public void setDefaultContent(final Content defaultContent) {
    this.defaultContent = defaultContent;
  }

  /**
   * Sets the default license.
   * 
   * @param defaultLicense
   *          the new default license
   */
  public void setDefaultLicense(final License defaultLicense) {
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.domain.utils.HasId#setId(java.lang.Long)
   */
  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Sets the logo.
   * 
   * @param logo
   *          the new logo
   */
  public void setLogo(final byte[] logo) {
    this.logo = logo;
    this.logoLastModifiedTime = System.currentTimeMillis();
  }

  /**
   * Sets the logo mime.
   * 
   * @param logoMime
   *          the new logo mime
   */
  public void setLogoMime(final BasicMimeType logoMime) {
    this.logoMime = logoMime;
  }

  /**
   * Sets the long name.
   * 
   * @param longName
   *          the new long name
   */
  public void setLongName(final String longName) {
    this.longName = longName;
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
   * Sets the social network.
   * 
   * @param socialNetwork
   *          the new social network
   */
  public void setSocialNetwork(final SocialNetwork socialNetwork) {
    this.socialNetwork = socialNetwork;
  }

  /**
   * Sets the tool config.
   * 
   * @param name
   *          the name
   * @param config
   *          the config
   * @return the tool configuration
   */
  public ToolConfiguration setToolConfig(final String name, final ToolConfiguration config) {
    toolsConfig.put(name, config);
    return config;
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
    return "Group[" + shortName + ", " + hashCode() + "]";
  }
}
