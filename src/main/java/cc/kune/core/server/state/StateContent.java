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
 \*/
package cc.kune.core.server.state;

import java.util.Date;
import java.util.List;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Class StateContent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StateContent extends StateContainer {
  private List<User> authors;
  private String content;
  private AccessRights contentRights;
  private Double currentUserRate;
  private String documentId;
  private boolean isParticipant;
  private BasicMimeType mimeType;
  private Date modifiedOn;
  private Date publishedOn;
  private Double rate;
  private Integer rateByUsers;
  private ContentStatus status;
  private String tags;
  private int version;
  private String waveCreator;
  private List<String> waveParticipants;
  private String waveRef;

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

  public boolean getIsParticipant() {
    return isParticipant;
  }

  public BasicMimeType getMimeType() {
    return mimeType;
  }

  public Date getModifiedOn() {
    return modifiedOn;
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

  public String getWaveCreator() {
    return waveCreator;
  }

  public List<String> getWaveParticipants() {
    return waveParticipants;
  }

  public String getWaveRef() {
    return waveRef;
  }

  public boolean isParticipant() {
    return isParticipant;
  }

  public boolean isWave() {
    return waveRef != null;
  }

  public void setAuthors(final List<User> authors) {
    this.authors = authors;
  }

  public void setContent(final String content) {
    this.content = content;
  }

  public void setContentRights(final AccessRights contentRights) {
    this.contentRights = contentRights;
  }

  public void setCurrentUserRate(final Double currentUserRate) {
    this.currentUserRate = currentUserRate;
  }

  public void setDocumentId(final String documentId) {
    this.documentId = documentId;
  }

  public void setIsParticipant(final boolean isParticipant) {
    this.isParticipant = isParticipant;
  }

  public void setMimeType(final BasicMimeType mimeType) {
    this.mimeType = mimeType;
  }

  public void setModifiedOn(final Date modifiedOn) {
    this.modifiedOn = modifiedOn;
  }

  public void setPublishedOn(final Date publishedOn) {
    this.publishedOn = publishedOn;
  }

  public void setRate(final Double rate) {
    this.rate = rate;
  }

  public void setRateByUsers(final Integer rateByUsers) {
    this.rateByUsers = rateByUsers;
  }

  public void setStatus(final ContentStatus status) {
    this.status = status;
  }

  public void setTags(final String tags) {
    this.tags = tags;
  }

  public void setVersion(final int version) {
    this.version = version;
  }

  public void setWaveCreator(final String waveCreator) {
    this.waveCreator = waveCreator;
  }

  public void setWaveParticipants(final List<String> participants) {
    this.waveParticipants = participants;
  }

  public void setWaveRef(final String waveRef) {
    this.waveRef = waveRef;
  }

  @Override
  public String toString() {
    return "State[" + getStateToken() + "/" + getTypeId() + (mimeType != null ? "-" + mimeType : "")
        + "]";
  }

}
