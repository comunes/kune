/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

public class StateContentDTO extends StateContainerDTO implements IsSerializable {

    private String documentId;
    private int version;
    private String content;
    private AccessRightsDTO contentRights;
    private ContentStatusDTO status;
    private Double rate;
    private Integer rateByUsers;
    private Double currentUserRate;
    private BasicMimeTypeDTO mimeType;
    private Date publishedOn;
    private String tags;
    private List<UserSimpleDTO> authors;

    public StateContentDTO() {
    }

    public List<UserSimpleDTO> getAuthors() {
        return authors;
    }

    public String getContent() {
        return content;
    }

    public AccessRightsDTO getContentRights() {
        return contentRights;
    }

    public Double getCurrentUserRate() {
        return currentUserRate;
    }

    public String getDocumentId() {
        return documentId;
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

    public ContentStatusDTO getStatus() {
        return status;
    }

    public String getTags() {
        return tags;
    }

    public int getVersion() {
        return version;
    }

    public void setAuthors(List<UserSimpleDTO> authors) {
        this.authors = authors;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentRights(AccessRightsDTO contentRights) {
        this.contentRights = contentRights;
    }

    public void setCurrentUserRate(Double currentUserRate) {
        this.currentUserRate = currentUserRate;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setMimeType(BasicMimeTypeDTO mimeType) {
        this.mimeType = mimeType;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setRate(RateResultDTO result) {
        setRate(result.getRate());
        setRateByUsers(result.getRateByUsers());
        setCurrentUserRate(result.getCurrentUserRate());
    }

    public void setRateByUsers(Integer rateByUsers) {
        this.rateByUsers = rateByUsers;
    }

    public void setStatus(ContentStatusDTO status) {
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
        return "StateDTO[" + getStateToken() + "/" + getTypeId() + (mimeType != null ? "-" + mimeType : "") + "]";
    }

}
