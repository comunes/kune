/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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

package org.ourproject.kune.platf.server.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.Length;
import org.hibernate.validator.Pattern;
import org.ourproject.kune.platf.client.dto.GroupType;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Indexed
@Table(name = "groups")
public class Group implements HasId {

    // see: http://docs.codehaus.org/display/PICO/Good+Citizen:
    // Never expect or return null
    public static Group NO_GROUP = null;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    AdmissionType admissionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    GroupType type;

    @Id
    @DocumentId
    @GeneratedValue
    private Long id;

    @Field(index = Index.TOKENIZED, store = Store.NO)
    @Column(unique = true)
    @Length(min = 3, max = 15, message = "Shortname between 3 and 15 chars of lenght")
    @Pattern(regex = "^[a-z0-9_\\-]+$", message = "Must be between 3 and 15 lowercase characters. Can only contain characters, numbers, and dashes")
    private String shortName;

    @Field(index = Index.TOKENIZED, store = Store.NO)
    @Column(unique = true)
    @Length(min = 3, max = 50, message = "longName between 3 and 50 chars of lenght")
    private String longName;

    @Field(index = Index.TOKENIZED, store = Store.NO)
    @Length(max = 255, message = "Public desc max 255 chars of lenght")
    private String publicDesc;

    @OneToOne
    private Content defaultContent;

    @OneToOne
    private Content groupLogo;

    @OneToOne(cascade = CascadeType.ALL)
    private SocialNetwork socialNetwork;

    @OneToOne
    private License defaultLicense;

    @OneToMany
    private final Map<String, ToolConfiguration> toolsConfig;

    private String workspaceTheme;

    public Group() {
	this(null, null, null, null);
    }

    public Group(final String shortName, final String longName) {
	this(shortName, longName, null, GroupType.PROJECT);
    }

    public Group(final String shortName, final String longName, final License defaultLicense, final GroupType type) {
	this.shortName = shortName;
	this.longName = longName;
	this.toolsConfig = new HashMap<String, ToolConfiguration>();
	this.socialNetwork = new SocialNetwork();
	this.defaultLicense = defaultLicense;
	this.type = type;
	this.admissionType = AdmissionType.Moderated;
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

    @Finder(query = "FROM Group g WHERE g.id IN (SELECT g.id FROM g.socialNetwork.accessLists.admins.list adm WHERE adm.id = :groupid)")
    public List<Group> findAdminInGroups(@Named("groupid")
    final Long groupId) {
	return null;
    }

    @Finder(query = "FROM Group g WHERE g.shortName = :shortName")
    public Group findByShortName(@Named("shortName")
    final String shortName) {
	return null;
    }

    @Finder(query = "FROM Group g WHERE g.id IN (SELECT g.id FROM g.socialNetwork.accessLists.editors.list AS ed WHERE ed.id = :groupid)")
    public List<Group> findCollabInGroups(@Named("groupid")
    final Long groupId) {
	return null;
    }

    public AdmissionType getAdmissionType() {
	return admissionType;
    }

    @Finder(query = "FROM Group")
    public List<Group> getAll() {
	return null;
    }

    public Content getDefaultContent() {
	return defaultContent;
    }

    public License getDefaultLicense() {
	return defaultLicense;
    }

    public Content getGroupLogo() {
	return groupLogo;
    }

    public Long getId() {
	return id;
    }

    public String getLongName() {
	return longName;
    }

    public String getPublicDesc() {
	return publicDesc;
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

    public ToolConfiguration getToolConfiguration(final String name) {
	return toolsConfig.get(name);
    }

    public Map<String, ToolConfiguration> getToolsConfig() {
	return toolsConfig;
    }

    public GroupType getType() {
	return type;
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

    public void setAdmissionType(final AdmissionType admissionType) {
	this.admissionType = admissionType;
    }

    public void setDefaultContent(final Content defaultContent) {
	this.defaultContent = defaultContent;
    }

    public void setDefaultLicense(final License defaultLicense) {
	this.defaultLicense = defaultLicense;
    }

    public void setGroupLogo(final Content groupLogo) {
	this.groupLogo = groupLogo;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public void setLongName(final String longName) {
	this.longName = longName;
    }

    public void setPublicDesc(final String publicDesc) {
	this.publicDesc = publicDesc;
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

    public void setType(final GroupType type) {
	this.type = type;
    }

    public void setWorkspaceTheme(final String workspaceTheme) {
	this.workspaceTheme = workspaceTheme;
    }

}