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

package org.ourproject.kune.platf.server.state;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.domain.BasicMimeType;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.ContentStatus;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.TagResult;
import org.ourproject.kune.platf.server.domain.User;

public class State extends StateContent {
    private String documentId;
    private String content;
    private Container container;
    private Container rootContainer;
    private AccessRights contentRights;
    private AccessRights containerRights;
    private ContentStatus status;
    private boolean isRateable;
    private Double rate;
    private Integer rateByUsers;
    private Double currentUserRate;
    private BasicMimeType mimeType;
    private License license;
    private List<User> authors;
    private Date publishedOn;
    private String tags;
    private List<TagResult> groupTags;

    public State() {
    }

    public List<User> getAuthors() {
        return authors;
    }

    public Container getContainer() {
        return container;
    }

    public AccessRights getContainerRights() {
        return containerRights;
    }

    public String getContent() {
        return content;
    }

    public AccessRights getContentRights() {
        return contentRights;
    }

    public Double getCurrentUserRate() {
        return currentUserRate;
    }

    public String getDocumentId() {
        return documentId;
    }

    public List<TagResult> getGroupTags() {
        return groupTags;
    }

    public License getLicense() {
        return license;
    }

    public BasicMimeType getMimeType() {
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

    public Container getRootContainer() {
        return rootContainer;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public String getTags() {
        return tags;
    }

    public boolean isRateable() {
        return isRateable;
    }

    public void setAuthors(final List<User> authors) {
        this.authors = authors;
    }

    public void setContainer(final Container container) {
        this.container = container;
    }

    public void setContainerRights(final AccessRights containerRights) {
        this.containerRights = containerRights;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setContentRights(final AccessRights accessRights) {
        this.contentRights = accessRights;
    }

    public void setCurrentUserRate(final Double currentUserRate) {
        this.currentUserRate = currentUserRate;
    }

    public void setDocumentId(final String docRef) {
        this.documentId = docRef;
    }

    public void setGroupTags(final List<TagResult> groupTags) {
        this.groupTags = groupTags;
    }

    public void setIsRateable(final boolean isRateable) {
        this.isRateable = isRateable;
    }

    public void setLicense(final License license) {
        this.license = license;
    }

    public void setMimeType(final BasicMimeType mimeType) {
        this.mimeType = mimeType;
    }

    public void setPublishedOn(final Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setRate(final Double rate) {
        if (rate != null) {
            this.rate = rate;
        } else {
            this.rate = 0d;
        }
    }

    public void setRateByUsers(final Long rateByUsers) {
        if (rateByUsers != null) {
            this.rateByUsers = rateByUsers.intValue();
        } else {
            this.rateByUsers = 0;
        }
    }

    public void setRootContainer(final Container rootContainer) {
        this.rootContainer = rootContainer;
    }

    public void setStatus(final ContentStatus status) {
        this.status = status;
    }

    public void setTags(final String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "State[" + getStateToken() + "/" + getTypeId() + "]";
    }
}
