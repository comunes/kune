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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import cc.kune.core.shared.domain.GroupListMode;

@Entity
@Table(name = "group_list")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupList {
  @Id
  @GeneratedValue
  private Long id;

  @Fetch(FetchMode.JOIN)
  @OrderBy("shortName ASC")
  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  Set<Group> list;

  @Enumerated(EnumType.STRING)
  @Basic
  private GroupListMode mode;

  public GroupList() {
    this(new HashSet<Group>());
    this.mode = GroupListMode.NORMAL;
  }

  public GroupList(final Group group) {
    this();
    add(group);
  }

  public GroupList(final Set<Group> list) {
    this.list = list;
  }

  public void add(final Group group) {
    list.add(group);
    // TODO: Get this outside Domain?
    if (getMode() != GroupListMode.NORMAL) {
      setMode(GroupListMode.NORMAL);
    }
  }

  public void clear() {
    list.clear();
  }

  public ArrayList<Group> duplicate() {
    return new ArrayList<Group>(list);
  }

  public Long getId() {
    return id;
  }

  public Set<Group> getList() {
    return list;
  }

  public GroupListMode getMode() {
    return mode;
  }

  public boolean includes(final Group group) {
    // Duplicate code in GroupListDTO
    switch (mode) {
    case NOBODY:
      return false;
    case EVERYONE:
      return true;
    default:
      // Workaround for:
      // http://www.timo-ernst.net/2011/06/remove-and-contains-not-working-on-your-java-set/
      return new HashSet<Group>(list).contains(group);
      // return list.contains(group);
    }
  }

  public boolean isEmpty() {
    return list.size() == 0;
  }

  public void remove(final Group group) {
    list.remove(group);
    // TODO: Get this outside Domain?
    if (list.isEmpty()) {
      setMode(GroupListMode.NOBODY);
    }
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setList(final Set<Group> list) {
    this.list = list;
  }

  public void setMode(final GroupListMode mode) {
    this.mode = mode;
  }

  @Override
  public String toString() {
    return "GroupList[(" + mode + "): " + list + "]";
  }

}
