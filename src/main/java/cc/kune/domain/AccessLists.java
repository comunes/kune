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

// TODO: Auto-generated Javadoc
/**
 * The Class AccessLists.
 *
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Indexed
@Table(name = "access_lists")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccessLists {

  /** The admins. */
  @OneToOne(cascade = CascadeType.ALL)
  protected GroupList admins;

  /** The editors. */
  @OneToOne(cascade = CascadeType.ALL)
  protected GroupList editors;

  /** The id. */
  @Id
  @GeneratedValue
  @DocumentId
  private Long id;

  /** The viewers. */
  @OneToOne(cascade = CascadeType.ALL)
  protected GroupList viewers;

  /**
   * Instantiates a new access lists.
   */
  public AccessLists() {
    this.admins = new GroupList();
    this.editors = new GroupList();
    this.viewers = new GroupList();
  }

  /**
   * Adds the admin.
   *
   * @param group the group
   */
  public void addAdmin(final Group group) {
    admins.add(group);
  }

  /**
   * Adds the editor.
   *
   * @param group the group
   */
  public void addEditor(final Group group) {
    editors.add(group);
  }

  /**
   * Adds the viewer.
   *
   * @param group the group
   */
  public void addViewer(final Group group) {
    viewers.add(group);
  }

  /**
   * Gets the admins.
   *
   * @return the admins
   */
  public GroupList getAdmins() {
    return admins;
  }

  /**
   * Gets the editors.
   *
   * @return the editors
   */
  public GroupList getEditors() {
    return editors;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the list.
   *
   * @param rol the rol
   * @return the list
   */
  public GroupList getList(final AccessRol rol) {
    if (rol == AccessRol.Administrator) {
      return getAdmins();
    } else if (rol == AccessRol.Editor) {
      return getEditors();
    } else {
      return getViewers();
    }
  }

  /**
   * Gets the viewers.
   *
   * @return the viewers
   */
  public GroupList getViewers() {
    return viewers;
  }

  /**
   * Removes the admin.
   *
   * @param group the group
   */
  public void removeAdmin(final Group group) {
    admins.remove(group);
  }

  /**
   * Removes the editor.
   *
   * @param group the group
   */
  public void removeEditor(final Group group) {
    editors.remove(group);
  }

  /**
   * Removes the viewer.
   *
   * @param group the group
   */
  public void removeViewer(final Group group) {
    viewers.remove(group);
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "AccessList[admins :" + admins + "; editors: " + editors + "; viewers: " + viewers + "]";
  }
}
