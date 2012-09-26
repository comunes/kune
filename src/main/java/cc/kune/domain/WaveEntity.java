/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

package cc.kune.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(WaveRefKey.class)
// @Indexed
@Table(name = "wave")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WaveEntity implements Serializable {

  private static final long serialVersionUID = 8890747607044939769L;

  @Id
  // @AttributeOverrides({ @AttributeOverride(name = "domain", column =
  // @Column(name = "domain")),
  // @AttributeOverride(name = "waveId", column = @Column(name = "waveId")),
  // @AttributeOverride(name = "waveletId", column = @Column(name =
  // "waveletId")) })
  private String domain;

  @Basic
  private Long lastModifiedTime;
  @Id
  private String waveId;
  @Id
  private String waveletId;

  public WaveEntity() {
  }

  public WaveEntity(final String domain, final String waveId, final String waveletId,
      final Long lastModifiedTime) {
    this.domain = domain;
    this.waveId = waveId;
    this.waveletId = waveletId;
    this.lastModifiedTime = lastModifiedTime;
  }

  public String getDomain() {
    return domain;
  }

  public Long getLastModifiedTime() {
    return lastModifiedTime;
  }

  public String getWaveId() {
    return waveId;
  }

  public String getWaveletId() {
    return waveletId;
  }

  public void setDomain(final String domain) {
    this.domain = domain;
  }

  public void setLastModifiedTime(final Long lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  public void setWaveId(final String waveId) {
    this.waveId = waveId;
  }

  public void setWaveletId(final String waveletId) {
    this.waveletId = waveletId;
  }

}
