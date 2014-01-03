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

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.search.annotations.DocumentId;

/**
 * The Class Participants maps the waves of some participant
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Table(name = "participants")
public class ParticipantEntity {

  @Column(nullable = false, unique = true)
  private String address;

  /** The id. */
  @Id
  @DocumentId
  @GeneratedValue
  private Long id;

  /** The waves. */
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Sort(comparator = WaveEntity.class, type = SortType.COMPARATOR)
  @OrderBy("lastModifiedTime DESC")
  private SortedSet<WaveEntity> waves;

  /**
   * Instantiates a new shared waves.
   */
  public ParticipantEntity() {
    this("");
  }

  public ParticipantEntity(final String address) {
    this.address = address;
    this.waves = new TreeSet<WaveEntity>(new Comparator<WaveEntity>() {
      @Override
      public int compare(final WaveEntity o1, final WaveEntity o2) {
        return o1.getLastModifiedTime().compareTo(o2.getLastModifiedTime());
      }
    });
  }

  public void add(final WaveEntity wave) {
    waves.add(wave);
  }

  public String getAddress() {
    return address;
  }

  public Set<WaveEntity> getWaves() {
    return waves;
  }

  public void remove(final WaveEntity wave) {
    waves.remove(wave);
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public void setWaves(final SortedSet<WaveEntity> waves) {
    this.waves = waves;
  }
}
