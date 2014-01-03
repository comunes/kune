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
package cc.kune.core.server.manager;

import java.util.List;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;
import cc.kune.domain.Group;
import cc.kune.domain.ParticipationData;
import cc.kune.domain.SocialNetwork;
import cc.kune.domain.SocialNetworkData;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface SocialNetworkManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface SocialNetworkManager extends Manager<SocialNetwork, Long> {

  /**
   * Accept join group.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @param inGroup
   *          the in group
   * @throws DefaultException
   *           the default exception
   */
  void acceptJoinGroup(User userLogged, Group group, Group inGroup) throws DefaultException;

  /**
   * Adds the as buddie.
   * 
   * @param userLogged
   *          the user logged
   * @param toUser
   *          the to user
   */
  void addAsBuddie(User userLogged, User toUser);

  /**
   * Adds the group to admins.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @param inGroup
   *          the in group
   * @throws DefaultException
   *           the default exception
   */
  void addGroupToAdmins(User userLogged, Group group, Group inGroup) throws DefaultException;

  /**
   * Adds the group to collabs.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @param inGroup
   *          the in group
   * @throws DefaultException
   *           the default exception
   */
  void addGroupToCollabs(User userLogged, Group group, Group inGroup) throws DefaultException;

  /**
   * Adds the group to viewers.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @param inGroup
   *          the in group
   * @throws DefaultException
   *           the default exception
   */
  void addGroupToViewers(User userLogged, Group group, Group inGroup) throws DefaultException;

  /**
   * Delete member.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @param inGroup
   *          the in group
   * @throws DefaultException
   *           the default exception
   * @throws AccessViolationException
   *           the access violation exception
   */
  void deleteMember(User userLogged, Group group, Group inGroup) throws DefaultException,
      AccessViolationException;

  /**
   * Deny join group.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @param inGroup
   *          the in group
   * @throws DefaultException
   *           the default exception
   */
  void denyJoinGroup(User userLogged, Group group, Group inGroup) throws DefaultException;

  /**
   * Find participation.
   * 
   * @param user
   *          the user
   * @param group
   *          the group
   * @return the participation data
   * @throws DefaultException
   *           the default exception
   */
  ParticipationData findParticipation(User user, Group group) throws DefaultException;

  /**
   * Find participation aggregated.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @return the list
   * @throws AccessViolationException
   *           the access violation exception
   */
  List<Group> findParticipationAggregated(User userLogged, Group group) throws AccessViolationException;

  /**
   * Generate response.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @return the social network data dto
   */
  SocialNetworkDataDTO generateResponse(User userLogged, Group group);

  /**
   * Gets the.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @return the social network
   * @throws DefaultException
   *           the default exception
   */
  SocialNetwork get(User userLogged, Group group) throws DefaultException;

  /**
   * Gets the social network data.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @return the social network data
   */
  SocialNetworkData getSocialNetworkData(User userLogged, Group group);

  /**
   * Request to join.
   * 
   * @param user
   *          the user
   * @param inGroup
   *          the in group
   * @return the social network request result
   * @throws DefaultException
   *           the default exception
   */
  SocialNetworkRequestResult requestToJoin(User user, Group inGroup) throws DefaultException;

  /**
   * Sets the admin as collab.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @param inGroup
   *          the in group
   * @throws DefaultException
   *           the default exception
   */
  void setAdminAsCollab(User userLogged, Group group, Group inGroup) throws DefaultException;

  /**
   * Sets the collab as admin.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @param inGroup
   *          the in group
   * @throws DefaultException
   *           the default exception
   */
  void setCollabAsAdmin(User userLogged, Group group, Group inGroup) throws DefaultException;

  /**
   * Un join group.
   * 
   * @param groupToUnJoin
   *          the group to un join
   * @param inGroup
   *          the in group
   * @throws DefaultException
   *           the default exception
   */
  void unJoinGroup(Group groupToUnJoin, Group inGroup) throws DefaultException;

}
