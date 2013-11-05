/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
  
  /** The authors. */
  private List<User> authors;
  
  /** The content. */
  private String content;
  
  /** The content rights. */
  private AccessRights contentRights;
  
  /** The current user rate. */
  private Double currentUserRate;
  
  /** The document id. */
  private String documentId;
  
  /** The is participant. */
  private boolean isParticipant;
  
  /** The mime type. */
  private BasicMimeType mimeType;
  
  /** The modified on. */
  private Date modifiedOn;
  
  /** The published on. */
  private Date publishedOn;
  
  /** The rate. */
  private Double rate;
  
  /** The rate by users. */
  private Integer rateByUsers;
  
  /** The status. */
  private ContentStatus status;
  
  /** The tags. */
  private String tags;
  
  /** The version. */
  private int version;
  
  /** The wave ref. */
  private String waveRef;

  /**
   * Instantiates a new state content.
   */
  public StateContent() {
  }

  /**
   * Gets the authors.
   *
   * @return the authors
   */
  public List<User> getAuthors() {
    return authors;
  }

  /**
   * Gets the content.
   *
   * @return the content
   */
  public String getContent() {
    return content;
  }

  /**
   * Gets the content rights.
   *
   * @return the content rights
   */
  public AccessRights getContentRights() {
    return contentRights;
  }

  /**
   * Gets the current user rate.
   *
   * @return the current user rate
   */
  public Double getCurrentUserRate() {
    return currentUserRate;
  }

  /**
   * Gets the document id.
   *
   * @return the document id
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Gets the checks if is participant.
   *
   * @return the checks if is participant
   */
  public boolean getIsParticipant() {
    return isParticipant;
  }

  /**
   * Gets the mime type.
   *
   * @return the mime type
   */
  public BasicMimeType getMimeType() {
    return mimeType;
  }

  /**
   * Gets the modified on.
   *
   * @return the modified on
   */
  public Date getModifiedOn() {
    return modifiedOn;
  }

  /**
   * Gets the published on.
   *
   * @return the published on
   */
  public Date getPublishedOn() {
    return publishedOn;
  }

  /**
   * Gets the rate.
   *
   * @return the rate
   */
  public Double getRate() {
    return rate;
  }

  /**
   * Gets the rate by users.
   *
   * @return the rate by users
   */
  public Integer getRateByUsers() {
    return rateByUsers;
  }

  /**
   * Gets the status.
   *
   * @return the status
   */
  public ContentStatus getStatus() {
    return status;
  }

  /**
   * Gets the tags.
   *
   * @return the tags
   */
  public String getTags() {
    return tags;
  }

  /**
   * Gets the version.
   *
   * @return the version
   */
  public int getVersion() {
    return version;
  }

  /**
   * Gets the wave ref.
   *
   * @return the wave ref
   */
  public String getWaveRef() {
    return waveRef;
  }

  /**
   * Checks if is participant.
   *
   * @return true, if is participant
   */
  public boolean isParticipant() {
    return isParticipant;
  }

  /**
   * Checks if is wave.
   *
   * @return true, if is wave
   */
  public boolean isWave() {
    return waveRef != null;
  }

  /**
   * Sets the authors.
   *
   * @param authors the new authors
   */
  public void setAuthors(final List<User> authors) {
    this.authors = authors;
  }

  /**
   * Sets the content.
   *
   * @param content the new content
   */
  public void setContent(final String content) {
    this.content = content;
  }

  /**
   * Sets the content rights.
   *
   * @param contentRights the new content rights
   */
  public void setContentRights(final AccessRights contentRights) {
    this.contentRights = contentRights;
  }

  /**
   * Sets the current user rate.
   *
   * @param currentUserRate the new current user rate
   */
  public void setCurrentUserRate(final Double currentUserRate) {
    this.currentUserRate = currentUserRate;
  }

  /**
   * Sets the document id.
   *
   * @param documentId the new document id
   */
  public void setDocumentId(final String documentId) {
    this.documentId = documentId;
  }

  /**
   * Sets the checks if is participant.
   *
   * @param isParticipant the new checks if is participant
   */
  public void setIsParticipant(final boolean isParticipant) {
    this.isParticipant = isParticipant;
  }

  /**
   * Sets the mime type.
   *
   * @param mimeType the new mime type
   */
  public void setMimeType(final BasicMimeType mimeType) {
    this.mimeType = mimeType;
  }

  /**
   * Sets the modified on.
   *
   * @param modifiedOn the new modified on
   */
  public void setModifiedOn(final Date modifiedOn) {
    this.modifiedOn = modifiedOn;
  }

  /**
   * Sets the published on.
   *
   * @param publishedOn the new published on
   */
  public void setPublishedOn(final Date publishedOn) {
    this.publishedOn = publishedOn;
  }

  /**
   * Sets the rate.
   *
   * @param rate the new rate
   */
  public void setRate(final Double rate) {
    this.rate = rate;
  }

  /**
   * Sets the rate by users.
   *
   * @param rateByUsers the new rate by users
   */
  public void setRateByUsers(final Integer rateByUsers) {
    this.rateByUsers = rateByUsers;
  }

  /**
   * Sets the status.
   *
   * @param status the new status
   */
  public void setStatus(final ContentStatus status) {
    this.status = status;
  }

  /**
   * Sets the tags.
   *
   * @param tags the new tags
   */
  public void setTags(final String tags) {
    this.tags = tags;
  }

  /**
   * Sets the version.
   *
   * @param version the new version
   */
  public void setVersion(final int version) {
    this.version = version;
  }

  /**
   * Sets the wave ref.
   *
   * @param waveRef the new wave ref
   */
  public void setWaveRef(final String waveRef) {
    this.waveRef = waveRef;
  }

  /* (non-Javadoc)
   * @see cc.kune.core.server.state.StateContainer#toString()
   */
  @Override
  public String toString() {
    return "State[" + getStateToken() + "/" + getTypeId() + (mimeType != null ? "-" + mimeType : "")
        + "]";
  }

}
