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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

import cc.kune.core.shared.domain.AccessRol;

@Entity
@Indexed
@Table(name = "access_lists")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccessLists {

  @OneToOne(cascade = CascadeType.ALL)
  protected GroupList admins;

  @OneToOne(cascade = CascadeType.ALL)
  protected GroupList editors;

  @Id
  @GeneratedValue
  @DocumentId
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  protected GroupList viewers;

  public AccessLists() {
    this.admins = new GroupList();
    this.editors = new GroupList();
    this.viewers = new GroupList();
  }

  public AccessLists(final Group group) {
    // We create a list with that group members as admins and editors and as
    // viewers, the viewer mode of the group
    this.admins = new GroupList(group);
    this.editors = new GroupList(group);
    this.viewers = group.getAccessLists().getList(AccessRol.Viewer);
  }

  public void addAdmin(final Group group) {
    admins.add(group);
  }

  public void addEditor(final Group group) {
    editors.add(group);
  }

  public void addViewer(final Group group) {
    viewers.add(group);
  }

  public GroupList getAdmins() {
    return admins;
  }

  public GroupList getEditors() {
    return editors;
  }

  public Long getId() {
    return id;
  }

  public GroupList getList(final AccessRol rol) {
    if (rol == AccessRol.Administrator) {
      return getAdmins();
    } else if (rol == AccessRol.Editor) {
      return getEditors();
    } else {
      return getViewers();
    }
  }

  public GroupList getViewers() {
    return viewers;
  }

  public void removeAdmin(final Group group) {
    admins.remove(group);
  }

  public void removeEditor(final Group group) {
    editors.remove(group);
  }

  public void removeViewer(final Group group) {
    viewers.remove(group);
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setList(final AccessRol rol, final GroupList list) {
    if (rol == AccessRol.Administrator) {
      this.admins = list;
    } else if (rol == AccessRol.Editor) {
      this.editors = list;
    } else {
      this.viewers = list;
    }
  }

  @Override
  public String toString() {
    return "AccessList[admins :" + admins + "; editors: " + editors + "; viewers: " + viewers + "]";
  }
}
