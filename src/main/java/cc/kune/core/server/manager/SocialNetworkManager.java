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

public interface SocialNetworkManager extends Manager<SocialNetwork, Long> {

  void acceptJoinGroup(User userLogged, Group group, Group inGroup) throws DefaultException;

  void addAsBuddie(User userLogged, User toUser);

  void addGroupToAdmins(User userLogged, Group group, Group inGroup) throws DefaultException;

  void addGroupToCollabs(User userLogged, Group group, Group inGroup) throws DefaultException;

  void addGroupToViewers(User userLogged, Group group, Group inGroup) throws DefaultException;

  void deleteMember(User userLogged, Group group, Group inGroup) throws DefaultException,
      AccessViolationException;

  void denyJoinGroup(User userLogged, Group group, Group inGroup) throws DefaultException;

  ParticipationData findParticipation(User user, Group group) throws DefaultException;

  List<Group> findParticipationAggregated(User userLogged, Group group) throws AccessViolationException;

  SocialNetworkDataDTO generateResponse(User userLogged, Group group);

  SocialNetwork get(User userLogged, Group group) throws DefaultException;

  SocialNetworkData getSocialNetworkData(User userLogged, Group group);

  SocialNetworkRequestResult requestToJoin(User user, Group inGroup) throws DefaultException;

  void setAdminAsCollab(User userLogged, Group group, Group inGroup) throws DefaultException;

  void setCollabAsAdmin(User userLogged, Group group, Group inGroup) throws DefaultException;

  void unJoinGroup(Group groupToUnJoin, Group inGroup) throws DefaultException;

}
