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
package cc.kune.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.waveprotocol.wave.model.waveref.WaveRef;

/**
 * The Class WaveRefKey is a database representation of {@link WaveRef}
 */
@Embeddable
public class WaveRefKey implements Serializable {

  private static final long serialVersionUID = -4751013448278982822L;

  public static WaveRefKey of(final String domain, final String waveId, final String waveletId) {
    return new WaveRefKey(domain, waveId, waveletId);
  }

  private String domain;

  private String waveId;

  private String waveletId;

  /**
   * Instantiates a new wave ref key.
   */
  public WaveRefKey() {
  }

  /**
   * Instantiates a new wave ref key.
   * 
   * @param waveId
   *          the wave id
   * @param waveletId
   *          the wavelet id
   */
  public WaveRefKey(final String domain, final String waveId, final String waveletId) {
    this.domain = domain;
    this.waveId = waveId;
    this.waveletId = waveletId;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final WaveRefKey other = (WaveRefKey) obj;
    if (domain == null) {
      if (other.domain != null) {
        return false;
      }
    } else if (!domain.equals(other.domain)) {
      return false;
    }
    if (waveId == null) {
      if (other.waveId != null) {
        return false;
      }
    } else if (!waveId.equals(other.waveId)) {
      return false;
    }
    if (waveletId == null) {
      if (other.waveletId != null) {
        return false;
      }
    } else if (!waveletId.equals(other.waveletId)) {
      return false;
    }
    return true;
  }

  public String getDomain() {
    return domain;
  }

  public String getWaveId() {
    return waveId;
  }

  public String getWaveletId() {
    return waveletId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((domain == null) ? 0 : domain.hashCode());
    result = prime * result + ((waveId == null) ? 0 : waveId.hashCode());
    result = prime * result + ((waveletId == null) ? 0 : waveletId.hashCode());
    return result;
  }

  public void setDomain(final String domain) {
    this.domain = domain;
  }

  public void setWaveId(final String waveId) {
    this.waveId = waveId;
  }

  public void setWaveletId(final String waveletId) {
    this.waveletId = waveletId;
  }

}
