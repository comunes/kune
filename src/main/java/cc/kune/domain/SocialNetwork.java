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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cc.kune.core.shared.domain.SocialNetworkVisibility;

// TODO: Auto-generated Javadoc
/**
 * The Class SocialNetwork.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Table(name = "social_networks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SocialNetwork {

  /** The Constant EMPTY. */
  public static final SocialNetwork EMPTY = new SocialNetwork(SocialNetworkVisibility.onlyadmins);

  /** The access lists. */
  @OneToOne(cascade = CascadeType.ALL)
  AccessLists accessLists;

  /** The id. */
  @Id
  @GeneratedValue
  Long id;

  /** The pending collaborators. */
  @OneToOne(cascade = CascadeType.ALL)
  GroupList pendingCollaborators;

  /** The visibility. */
  @Enumerated(EnumType.STRING)
  SocialNetworkVisibility visibility;

  /**
   * Instantiates a new social network.
   */
  public SocialNetwork() {
    accessLists = new AccessLists();
    pendingCollaborators = new GroupList();
    visibility = SocialNetworkVisibility.anyone;
  }

  /**
   * Instantiates a new social network.
   * 
   * @param visibility
   *          the visibility
   */
  public SocialNetwork(final SocialNetworkVisibility visibility) {
    accessLists = new AccessLists();
    pendingCollaborators = new GroupList();
    this.visibility = visibility;
  }

  /**
   * Adds the admin.
   * 
   * @param group
   *          the group
   */
  public void addAdmin(final Group group) {
    accessLists.addAdmin(group);
  }

  /**
   * Adds the collaborator.
   * 
   * @param group
   *          the group
   */
  public void addCollaborator(final Group group) {
    accessLists.addEditor(group);
  }

  /**
   * Adds the pending collaborator.
   * 
   * @param group
   *          the group
   */
  public void addPendingCollaborator(final Group group) {
    pendingCollaborators.add(group);
  }

  /**
   * Adds the viewer.
   * 
   * @param group
   *          the group
   */
  public void addViewer(final Group group) {
    accessLists.addViewer(group);
  }

  /**
   * Gets the access lists.
   * 
   * @return the access lists
   */
  public AccessLists getAccessLists() {
    return accessLists;
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
   * Gets the pending collaborators.
   * 
   * @return the pending collaborators
   */
  public GroupList getPendingCollaborators() {
    return pendingCollaborators;
  }

  /**
   * Gets the visibility.
   * 
   * @return the visibility
   */
  public SocialNetworkVisibility getVisibility() {
    return visibility;
  }

  /**
   * Checks if is admin.
   * 
   * @param group
   *          the group
   * @return true, if is admin
   */
  public boolean isAdmin(final Group group) {
    return accessLists.getAdmins().includes(group);
  }

  /**
   * Checks if is collab.
   * 
   * @param group
   *          the group
   * @return true, if is collab
   */
  public boolean isCollab(final Group group) {
    return accessLists.getEditors().includes(group);
  }

  /**
   * Checks if is pending collab.
   * 
   * @param group
   *          the group
   * @return true, if is pending collab
   */
  public boolean isPendingCollab(final Group group) {
    return pendingCollaborators.getList().contains(group);
  }

  /**
   * Checks if is viewer.
   * 
   * @param group
   *          the group
   * @return true, if is viewer
   */
  public boolean isViewer(final Group group) {
    return accessLists.getViewers().includes(group);
  }

  /**
   * Removes the admin.
   * 
   * @param group
   *          the group
   */
  public void removeAdmin(final Group group) {
    accessLists.removeAdmin(group);
  }

  /**
   * Removes the collaborator.
   * 
   * @param group
   *          the group
   */
  public void removeCollaborator(final Group group) {
    accessLists.removeEditor(group);
  }

  /**
   * Removes the pending collaborator.
   * 
   * @param group
   *          the group
   */
  public void removePendingCollaborator(final Group group) {
    pendingCollaborators.getList().remove(group);
  }

  /**
   * Removes the viewer.
   * 
   * @param group
   *          the group
   */
  public void removeViewer(final Group group) {
    accessLists.removeViewer(group);
  }

  /**
   * Sets the access lists.
   * 
   * @param accessList
   *          the new access lists
   */
  public void setAccessLists(final AccessLists accessList) {
    this.accessLists = accessList;
  }

  /**
   * Sets the id.
   * 
   * @param id
   *          the new id
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Sets the pending collaborators.
   * 
   * @param pendingCollaborators
   *          the new pending collaborators
   */
  public void setPendingCollaborators(final GroupList pendingCollaborators) {
    this.pendingCollaborators = pendingCollaborators;
  }

  /**
   * Sets the visibility.
   * 
   * @param visibility
   *          the new visibility
   */
  public void setVisibility(final SocialNetworkVisibility visibility) {
    this.visibility = visibility;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SocialNetwork[accessList: " + accessLists + "; pendingsCollabs: " + pendingCollaborators
        + "]";
  }
}
