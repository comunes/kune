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

package org.ourproject.kune.workspace.client.dto;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StateDTO implements IsSerializable {
    private String documentId;
    private int version;
    private String content;
    private String title;
    private String toolName;
    private GroupDTO group;
    private ContainerDTO folder;
    private SocialNetworkDTO socialNetwork;
    private AccessListsDTO accessLists;
    private AccessRightsDTO contentRights;
    private AccessRightsDTO folderRights;
    private AccessRightsDTO groupRights;
    private boolean isRateable;
    private Double rate;
    private Integer rateByUsers;
    private Double currentUserRate;
    private String typeId;
    private LicenseDTO license;
    private I18nLanguageDTO language;
    private Date publishedOn;
    private String tags;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.UserSimpleDTO>
     */
    private List authors;

    public StateDTO() {
        this(null, null, null);
    }

    public StateDTO(final String docRef, final String title, final String content) {
        this.documentId = docRef;
        this.title = title;
        this.content = content;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public void setDocumentId(final String docRef) {
        this.documentId = docRef;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getContent() {
        return content;
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

    public GroupDTO getGroup() {
        return this.group;
    }

    public void setGroup(final GroupDTO group) {
        this.group = group;
    }

    public AccessRightsDTO getContentRights() {
        return this.contentRights;
    }

    public void setContentRights(final AccessRightsDTO accessRights) {
        this.contentRights = accessRights;
    }

    public ContainerDTO getFolder() {
        return folder;
    }

    public void setFolder(final ContainerDTO folder) {
        this.folder = folder;
    }

    public StateToken getState() {
        return new StateToken(group.getShortName(), toolName, folder.getId().toString(), getDocumentId());
    }

    // FIXME: maybe a tag in the content showing the type, think about this
    public boolean hasDocument() {
        return documentId != null;
    }

    public AccessListsDTO getAccessLists() {
        return accessLists;
    }

    public void setAccessLists(final AccessListsDTO accessLists) {
        this.accessLists = accessLists;
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

    public void setRateByUsers(final Integer rateByUsers) {
        this.rateByUsers = rateByUsers;
    }

    public AccessRightsDTO getFolderRights() {
        return folderRights;
    }

    public void setFolderRights(final AccessRightsDTO folderRights) {
        this.folderRights = folderRights;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(final String typeId) {
        this.typeId = typeId;
    }

    public LicenseDTO getLicense() {
        return license;
    }

    public void setLicense(final LicenseDTO license) {
        this.license = license;
    }

    public SocialNetworkDTO getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(final SocialNetworkDTO socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public AccessRightsDTO getGroupRights() {
        return groupRights;
    }

    public void setGroupRights(final AccessRightsDTO groupRights) {
        this.groupRights = groupRights;
    }

    public Double getCurrentUserRate() {
        return currentUserRate;
    }

    public void setCurrentUserRate(final Double currentUserRate) {
        this.currentUserRate = currentUserRate;
    }

    public boolean isRateable() {
        return isRateable;
    }

    public void setRateable(final boolean isRateable) {
        this.isRateable = isRateable;
    }

    public I18nLanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(final I18nLanguageDTO language) {
        this.language = language;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(final Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public List getAuthors() {
        return authors;
    }

    public void setAuthors(final List authors) {
        this.authors = authors;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(final String tags) {
        this.tags = tags;
    }

}
