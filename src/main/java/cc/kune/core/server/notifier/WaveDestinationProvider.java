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
package cc.kune.core.server.notifier;

import java.util.Collection;

import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.wave.server.kspecific.KuneWaveServerUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class WaveDestinationProvider. A WaveRef is used to get the participants
 * and to send notifications to them
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WaveDestinationProvider implements DestinationProvider {

  /** The author. */
  private final String author;

  /** The list. */
  private Collection<Addressee> list;

  /** The ref. */
  private final WaveRef ref;

  /**
   * Instantiates a new wave destination provider.
   * 
   * @param ref
   *          the ref
   * @param author
   *          the author
   */
  public WaveDestinationProvider(final WaveRef ref, final String author) {
    this.ref = ref;
    this.author = author;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
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
    final WaveDestinationProvider other = (WaveDestinationProvider) obj;
    if (ref == null) {
      if (other.ref != null) {
        return false;
      }
    } else if (!ref.equals(other.ref)) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.wave.server.kspecific.pendnotif.DestinationProvider#getDest()
   */
  @Override
  public Collection<Addressee> getDest() {
    if (list == null) {
      list = KuneWaveServerUtils.getLocalParticipantsExceptAuthor(ref, author);
    }
    return list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ref == null) ? 0 : ref.hashCode());
    return result;
  }

}
