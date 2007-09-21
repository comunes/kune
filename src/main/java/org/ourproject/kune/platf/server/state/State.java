/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.state;

import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.SocialNetwork;

public class State {
    private String documentId;
    private String content;
    private String title;
    private String toolName;
    private Group group;
    private Container container;
    private SocialNetwork socialNetwork;
    private AccessLists accessLists;
    private AccessRights contentRights;
    private AccessRights folderRights;
    private AccessRights groupRights;
    private Double rate;
    private Integer rateByUsers;
    private String typeId;
    private License license;

    public State() {
    }

    public AccessLists getAccessLists() {
	return accessLists;
    }

    public void setAccessLists(final AccessLists accessLists) {
	this.accessLists = accessLists;
    }

    public AccessRights getContentRights() {
	return contentRights;
    }

    public String getDocumentId() {
	return documentId;
    }

    public void setDocumentId(final String docRef) {
	this.documentId = docRef;
    }

    public AccessRights getFolderRights() {
	return folderRights;
    }

    public void setFolderRights(final AccessRights folderRights) {
	this.folderRights = folderRights;
    }

    public String getContent() {
	return content;
    }

    public void setContent(final String content) {
	this.content = content;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(final String title) {
	this.title = title;
    }

    public String getToolName() {
	return toolName;
    }

    public void setToolName(final String toolName) {
	this.toolName = toolName;
    }

    public Group getGroup() {
	return group;
    }

    public void setGroup(final Group group) {
	this.group = group;
    }

    public void setContentRights(final AccessRights accessRights) {
	this.contentRights = accessRights;
    }

    public Container getFolder() {
	return container;
    }

    public void setFolder(final Container container) {
	this.container = container;
    }

    public Double getRate() {
	return rate;
    }

    public void setRate(final Double rate) {
	this.rate = rate;
    }

    public Integer getRateByUsers() {
	return rateByUsers;
    }

    public void setRateByUsers(final Long rateByUsers) {
	this.rateByUsers = rateByUsers.intValue();
    }

    public void setTypeId(final String typeId) {
	this.typeId = typeId;
    }

    public String getTypeId() {
	return typeId;
    }

    public License getLicense() {
	return license;
    }

    public void setLicense(final License license) {
	this.license = license;
    }

    public SocialNetwork getSocialNetwork() {
	return socialNetwork;
    }

    public void setSocialNetwork(final SocialNetwork socialNetwork) {
	this.socialNetwork = socialNetwork;
    }

    public AccessRights getGroupRights() {
	return groupRights;
    }

    public void setGroupRights(final AccessRights groupRights) {
	this.groupRights = groupRights;
    }

}
