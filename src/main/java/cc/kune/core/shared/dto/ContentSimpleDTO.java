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
 */
package cc.kune.core.shared.dto;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.AccessRights;

public class ContentSimpleDTO extends AbstractContentSimpleDTO {

  private BasicMimeTypeDTO mimeType;
  private Long modifiedOn;
  private AccessRights rights;
  private ContentStatus status;
  private String title;
  private String waveId;

  public BasicMimeTypeDTO getMimeType() {
    return mimeType;
  }

  public Long getModifiedOn() {
    return modifiedOn;
  }

  @Override
  public String getName() {
    return title;
  }

  public AccessRights getRights() {
    return rights;
  }

  public ContentStatus getStatus() {
    return status;
  }

  public String getTitle() {
    return title;
  }

  public String getWaveId() {
    return waveId;
  }

  public void setMimeType(final BasicMimeTypeDTO mimeType) {
    this.mimeType = mimeType;
  }

  public void setModifiedOn(final Long modifiedOn) {
    this.modifiedOn = modifiedOn;
  }

  @Override
  public void setName(final String name) {
    this.title = name;
  }

  public void setRights(final AccessRights rights) {
    this.rights = rights;
  }

  public void setStatus(final ContentStatus status) {
    this.status = status;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public void setWaveId(final String waveId) {
    this.waveId = waveId;
  }

}
