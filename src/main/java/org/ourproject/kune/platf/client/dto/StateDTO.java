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

package org.ourproject.kune.platf.client.dto;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StateDTO implements IsSerializable {
    private String documentId;
    private int version;
    private String content;
    private String title;
    private String toolName;
    private GroupDTO group;
    private ContainerDTO container;
    private ContainerDTO rootContainer;
    private SocialNetworkDTO socialNetwork;
    private AccessListsDTO accessLists;
    private AccessRightsDTO contentRights;
    private AccessRightsDTO containerRights;
    private AccessRightsDTO groupRights;
    private ContentStatusDTO status;
    private boolean isRateable;
    private Double rate;
    private Integer rateByUsers;
    private Double currentUserRate;
    private String typeId;
    private BasicMimeTypeDTO mimeType;
    private LicenseDTO license;
    private I18nLanguageDTO language;
    private Date publishedOn;
    private String tags;
    private List<UserSimpleDTO> authors;
    private List<TagResultDTO> groupTags;
    private SocialNetworkDTO groupMembers;
    private ParticipationDataDTO participation;
    private StateToken stateToken;
    private List<String> enabledTools;

    public StateDTO() {
        this(null, null, null);
    }

    public StateDTO(final String docRef, final String title, final String content) {
        this.documentId = docRef;
        this.title = title;
        this.content = content;
    }

    public AccessListsDTO getAccessLists() {
        return accessLists;
    }

    public List<UserSimpleDTO> getAuthors() {
        return authors;
    }

    public ContainerDTO getContainer() {
        return container;
    }

    public AccessRightsDTO getContainerRights() {
        return containerRights;
    }

    public String getContent() {
        return content;
    }

    public AccessRightsDTO getContentRights() {
        return this.contentRights;
    }

    public Double getCurrentUserRate() {
        return currentUserRate;
    }

    public String getDocumentId() {
        return documentId;
    }

    public List<String> getEnabledTools() {
        return enabledTools;
    }

    public GroupDTO getGroup() {
        return this.group;
    }

    public SocialNetworkDTO getGroupMembers() {
        return groupMembers;
    }

    public AccessRightsDTO getGroupRights() {
        return groupRights;
    }

    public List<TagResultDTO> getGroupTags() {
        return groupTags;
    }

    public I18nLanguageDTO getLanguage() {
        return language;
    }

    public LicenseDTO getLicense() {
        return license;
    }

    public BasicMimeTypeDTO getMimeType() {
        return mimeType;
    }

    public ParticipationDataDTO getParticipation() {
        return participation;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public Double getRate() {
        return rate;
    }

    public Integer getRateByUsers() {
        return rateByUsers;
    }

    public ContainerDTO getRootContainer() {
        return rootContainer;
    }

    public SocialNetworkDTO getSocialNetwork() {
        return socialNetwork;
    }

    public StateToken getStateToken() {
        return stateToken;
    }

    public ContentStatusDTO getStatus() {
        return status;
    }

    public String getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public String getToolName() {
        return toolName;
    }

    public String getTypeId() {
        return typeId;
    }

    public int getVersion() {
        return version;
    }

    // FIXME: maybe a tag in the content showing the type, think about this
    public boolean hasDocument() {
        return documentId != null;
    }

    public boolean isRateable() {
        return isRateable;
    }

    public void setAccessLists(final AccessListsDTO accessLists) {
        this.accessLists = accessLists;
    }

    public void setAuthors(final List<UserSimpleDTO> authors) {
        this.authors = authors;
    }

    public void setContainer(final ContainerDTO container) {
        this.container = container;
    }

    public void setContainerRights(final AccessRightsDTO containerRights) {
        this.containerRights = containerRights;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setContentRights(final AccessRightsDTO accessRights) {
        this.contentRights = accessRights;
    }

    public void setCurrentUserRate(final Double currentUserRate) {
        this.currentUserRate = currentUserRate;
    }

    public void setDocumentId(final String docRef) {
        this.documentId = docRef;
    }

    public void setEnabledTools(List<String> enabledTools) {
        this.enabledTools = enabledTools;
    }

    public void setGroup(final GroupDTO group) {
        this.group = group;
    }

    public void setGroupMembers(final SocialNetworkDTO groupMembers) {
        this.groupMembers = groupMembers;
    }

    public void setGroupRights(final AccessRightsDTO groupRights) {
        this.groupRights = groupRights;
    }

    public void setGroupTags(final List<TagResultDTO> groupTags) {
        this.groupTags = groupTags;
    }

    public void setLanguage(final I18nLanguageDTO language) {
        this.language = language;
    }

    public void setLicense(final LicenseDTO license) {
        this.license = license;
    }

    public void setMimeType(final BasicMimeTypeDTO mimeType) {
        this.mimeType = mimeType;
    }

    public void setParticipation(final ParticipationDataDTO participation) {
        this.participation = participation;
    }

    public void setPublishedOn(final Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setRate(final Double rate) {
        this.rate = rate;
    }

    public void setRateable(final boolean isRateable) {
        this.isRateable = isRateable;
    }

    public void setRateByUsers(final Integer rateByUsers) {
        this.rateByUsers = rateByUsers;
    }

    public void setRootContainer(final ContainerDTO rootContainer) {
        this.rootContainer = rootContainer;
    }

    public void setSocialNetwork(final SocialNetworkDTO socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public void setStateToken(final StateToken stateToken) {
        this.stateToken = stateToken;
    }

    public void setStatus(final ContentStatusDTO status) {
        this.status = status;
    }

    public void setTags(final String tags) {
        this.tags = tags;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setToolName(final String toolName) {
        this.toolName = toolName;
    }

    public void setTypeId(final String typeId) {
        this.typeId = typeId;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "StateDTO[" + stateToken + "/" + typeId + (mimeType != null ? "-" + mimeType : "") + "]";
    }

}
