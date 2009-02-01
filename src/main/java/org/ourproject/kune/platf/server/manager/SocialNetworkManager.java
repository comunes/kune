/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.client.dto.SocialNetworkRequestResult;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.SocialNetworkData;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.sn.ParticipationData;

public interface SocialNetworkManager extends Manager<SocialNetwork, Long> {

    void acceptJoinGroup(User userLogged, Group group, Group inGroup) throws DefaultException;

    void addGroupToAdmins(User userLogged, Group group, Group inGroup) throws DefaultException;

    void addGroupToCollabs(User userLogged, Group group, Group inGroup) throws DefaultException;

    void addGroupToViewers(User userLogged, Group group, Group inGroup) throws DefaultException;

    void deleteMember(User userLogged, Group group, Group inGroup) throws DefaultException, AccessViolationException;

    void denyJoinGroup(User userLogged, Group group, Group inGroup) throws DefaultException;

    ParticipationData findParticipation(User user, Group group) throws DefaultException;

    SocialNetwork get(User userLogged, Group group) throws DefaultException;

    SocialNetworkData getSocialNetworkData(User userLogged, Group group);

    SocialNetworkRequestResult requestToJoin(User user, Group inGroup) throws DefaultException;

    void setAdminAsCollab(User userLogged, Group group, Group inGroup) throws DefaultException;

    void setCollabAsAdmin(User userLogged, Group group, Group inGroup) throws DefaultException;

    void unJoinGroup(Group groupToUnJoin, Group inGroup) throws DefaultException;

}
