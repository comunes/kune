/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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

@Entity
@Indexed
@Table(name = "groups")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group implements HasId {

  // see: http://docs.codehaus.org/display/PICO/Good+Citizen:
  // Never expect or return null
  public static final Group NO_GROUP = null;
  // public static final String PROPS_ID = "groupprops";

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  AdmissionType admissionType;

  private String backgroundImage;

  private String backgroundMime;

  @Basic(optional = false)
  private final Long createdOn;

  @OneToOne
  private Content defaultContent;

  @OneToOne
  private License defaultLicense;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  GroupType groupType;

  @Id
  @DocumentId
  @GeneratedValue
  private Long id;

  @Lob
  private byte[] logo;

  @Basic
  private Long logoLastModifiedTime;

  @Embedded
  private BasicMimeType logoMime;

  @Field(index = Index.YES, store = Store.NO)
  @Column(nullable = false, unique = true)
  @Length(min = 3, max = CoreConstants.MAX_LONG_NAME_SIZE, message = "The longName must be between 3 and "
      + CoreConstants.MAX_LONG_NAME_SIZE + " characters of length")
  private String longName;

  @Field(index = Index.YES, store = Store.NO)
  @Column(unique = true)
  @Length(min = 3, max = CoreConstants.MAX_SHORT_NAME_SIZE, message = "The shortname must be between 3 and 15 characters of length")
  @Pattern(regexp = "^[a-z0-9]+$", message = "The name must be between 3 and "
      + CoreConstants.MAX_SHORT_NAME_SIZE
      + " lowercase characters. It can only contain Western characters, numbers, and dashes")
  private String shortName;

  @OneToOne(cascade = CascadeType.ALL)
  private SocialNetwork socialNetwork;

  @MapKeyColumn(name = "mapkey")
  @OneToMany
  private final Map<String, ToolConfiguration> toolsConfig;

  private String workspaceTheme;

  public Group() {
    this(null, null, null, null);
  }

  public Group(final String shortName, final String longName) {
    this(shortName, longName, null, GroupType.PROJECT);
  }

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

  public boolean existToolConfig(final String toolName) {
    return toolsConfig.get(toolName) != null;
  }

  @Transient
  public AccessLists getAccessLists() {
    return getSocialNetwork().getAccessLists();
  }

  public AdmissionType getAdmissionType() {
    return admissionType;
  }

  public String getBackgroundImage() {
    return backgroundImage;
  }

  public String getBackgroundMime() {
    return backgroundMime;
  }

  public Long getCreatedOn() {
    return createdOn;
  }

  public Content getDefaultContent() {
    return defaultContent;
  }

  public License getDefaultLicense() {
    return defaultLicense;
  }

  public GroupType getGroupType() {
    return groupType;
  }

  public boolean getHasLogo() {
    return hasLogo();
  }

  @Override
  public Long getId() {
    return id;
  }

  public byte[] getLogo() {
    return logo;
  }

  public BasicMimeType getLogoMime() {
    return logoMime;
  }

  public String getLongName() {
    return longName;
  }

  public Container getRoot(final String toolName) {
    return toolsConfig.get(toolName).getRoot();
  }

  public String getShortName() {
    return shortName;
  }

  public SocialNetwork getSocialNetwork() {
    return socialNetwork;
  }

  @Transient
  public StateToken getStateToken() {
    return new StateToken(shortName);
  }

  public ToolConfiguration getToolConfiguration(final String name) {
    return toolsConfig.get(name);
  }

  public Map<String, ToolConfiguration> getToolsConfig() {
    return toolsConfig;
  }

  public String getWorkspaceTheme() {
    return workspaceTheme;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (shortName == null ? 0 : shortName.hashCode());
    return result;
  }

  @Transient
  public boolean hasLogo() {
    return (logo != null && logo.length > 0 && logoMime != null);
  }

  public boolean isPersonal() {
    return getGroupType().equals(GroupType.PERSONAL);
  }

  public void setAdmissionType(final AdmissionType admissionType) {
    this.admissionType = admissionType;
  }

  public void setBackgroundImage(final String backgroundImage) {
    this.backgroundImage = backgroundImage;
  }

  public void setBackgroundMime(final String backgroundMime) {
    this.backgroundMime = backgroundMime;
  }

  public void setDefaultContent(final Content defaultContent) {
    this.defaultContent = defaultContent;
  }

  public void setDefaultLicense(final License defaultLicense) {
    this.defaultLicense = defaultLicense;
  }

  public void setGroupType(final GroupType groupType) {
    this.groupType = groupType;
  }

  public Long getLogoLastModifiedTime() {
    if (logoLastModifiedTime == null) {
      return System.currentTimeMillis();
    }
    return logoLastModifiedTime;
  }

  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  public void setLogo(final byte[] logo) {
    this.logo = logo;
    this.logoLastModifiedTime = System.currentTimeMillis();
  }

  public void setLogoMime(final BasicMimeType logoMime) {
    this.logoMime = logoMime;
  }

  public void setLongName(final String longName) {
    this.longName = longName;
  }

  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }

  public void setSocialNetwork(final SocialNetwork socialNetwork) {
    this.socialNetwork = socialNetwork;
  }

  public ToolConfiguration setToolConfig(final String name, final ToolConfiguration config) {
    toolsConfig.put(name, config);
    return config;
  }

  public void setWorkspaceTheme(final String workspaceTheme) {
    this.workspaceTheme = workspaceTheme;
  }

  @Override
  public String toString() {
    return "Group[" + shortName + ", " + hashCode() + "]";
  }
}