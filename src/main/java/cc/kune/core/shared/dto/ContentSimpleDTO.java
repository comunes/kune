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
 */
package cc.kune.core.shared.dto;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.AccessRights;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentSimpleDTO.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContentSimpleDTO extends AbstractContentSimpleDTO {

  /** The mime type. */
  private BasicMimeTypeDTO mimeType;

  /** The modified on. */
  private Long modifiedOn;

  /** The rights. */
  private AccessRights rights;

  /** The status. */
  private ContentStatus status;

  /** The title. */
  private String title;

  /** The wave id. */
  private String waveId;

  /**
   * Gets the mime type.
   * 
   * @return the mime type
   */
  public BasicMimeTypeDTO getMimeType() {
    return mimeType;
  }

  /**
   * Gets the modified on.
   * 
   * @return the modified on
   */
  public Long getModifiedOn() {
    return modifiedOn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.shared.dto.AbstractContentSimpleDTO#getName()
   */
  @Override
  public String getName() {
    return title;
  }

  /**
   * Gets the rights.
   * 
   * @return the rights
   */
  public AccessRights getRights() {
    return rights;
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
   * Gets the title.
   * 
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the wave id.
   * 
   * @return the wave id
   */
  public String getWaveId() {
    return waveId;
  }

  /**
   * Sets the mime type.
   * 
   * @param mimeType
   *          the new mime type
   */
  public void setMimeType(final BasicMimeTypeDTO mimeType) {
    this.mimeType = mimeType;
  }

  /**
   * Sets the modified on.
   * 
   * @param modifiedOn
   *          the new modified on
   */
  public void setModifiedOn(final Long modifiedOn) {
    this.modifiedOn = modifiedOn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.shared.dto.AbstractContentSimpleDTO#setName(java.lang.String)
   */
  @Override
  public void setName(final String name) {
    this.title = name;
  }

  /**
   * Sets the rights.
   * 
   * @param rights
   *          the new rights
   */
  public void setRights(final AccessRights rights) {
    this.rights = rights;
  }

  /**
   * Sets the status.
   * 
   * @param status
   *          the new status
   */
  public void setStatus(final ContentStatus status) {
    this.status = status;
  }

  /**
   * Sets the title.
   * 
   * @param title
   *          the new title
   */
  public void setTitle(final String title) {
    this.title = title;
  }

  /**
   * Sets the wave id.
   * 
   * @param waveId
   *          the new wave id
   */
  public void setWaveId(final String waveId) {
    this.waveId = waveId;
  }

}
