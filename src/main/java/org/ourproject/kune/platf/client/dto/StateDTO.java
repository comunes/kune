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

public class StateDTO extends StateContentDTO implements IsSerializable {
    private String documentId;
    private int version;
    private String content;
    private ContainerDTO container;
    private ContainerDTO rootContainer;
    private SocialNetworkDTO socialNetwork;
    private AccessRightsDTO contentRights;
    private AccessRightsDTO containerRights;
    private ContentStatusDTO status;
    private boolean isRateable;
    private Double rate;
    private Integer rateByUsers;
    private Double currentUserRate;
    private BasicMimeTypeDTO mimeType;
    private LicenseDTO license;
    private Date publishedOn;
    private String tags;
    private List<UserSimpleDTO> authors;
    private List<TagResultDTO> groupTags;

    public StateDTO() {
        this(null, null, null);
    }

    public StateDTO(final String docRef, final String title, final String content) {
        this.documentId = docRef;
        this.content = content;
        setTitle(title);
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

    public List<TagResultDTO> getGroupTags() {
        return groupTags;
    }

    public LicenseDTO getLicense() {
        return license;
    }

    public BasicMimeTypeDTO getMimeType() {
        return mimeType;
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

    public ContentStatusDTO getStatus() {
        return status;
    }

    public String getTags() {
        return tags;
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

    public void setGroupTags(final List<TagResultDTO> groupTags) {
        this.groupTags = groupTags;
    }

    public void setLicense(final LicenseDTO license) {
        this.license = license;
    }

    public void setMimeType(final BasicMimeTypeDTO mimeType) {
        this.mimeType = mimeType;
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

    public void setStatus(final ContentStatusDTO status) {
        this.status = status;
    }

    public void setTags(final String tags) {
        this.tags = tags;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "StateDTO[" + getStateToken() + "/" + getTypeId() + (mimeType != null ? "-" + mimeType : "") + "]";
    }

}
