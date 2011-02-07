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
 \*/
package org.ourproject.kune.platf.server.state;

import java.util.Date;
import java.util.List;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.User;


public class StateContent extends StateContainer {

    private String documentId;
    private int version;
    private String content;
    private AccessRights contentRights;
    private ContentStatus status;
    private Double rate;
    private Integer rateByUsers;
    private Double currentUserRate;
    private BasicMimeType mimeType;
    private Date publishedOn;
    private String tags;
    private List<User> authors;

    public StateContent() {
    }

    public List<User> getAuthors() {
        return authors;
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

    public ContentStatus getStatus() {
        return status;
    }

    public String getTags() {
        return tags;
    }

    public int getVersion() {
        return version;
    }

    public void setAuthors(List<User> authors) {
        this.authors = authors;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentRights(AccessRights contentRights) {
        this.contentRights = contentRights;
    }

    public void setCurrentUserRate(Double currentUserRate) {
        this.currentUserRate = currentUserRate;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setMimeType(BasicMimeType mimeType) {
        this.mimeType = mimeType;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setRateByUsers(Integer rateByUsers) {
        this.rateByUsers = rateByUsers;
    }

    public void setStatus(ContentStatus status) {
        this.status = status;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "State[" + getStateToken() + "/" + getTypeId() + (mimeType != null ? "-" + mimeType : "") + "]";
    }

}
