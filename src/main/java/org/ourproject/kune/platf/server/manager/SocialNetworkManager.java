/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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

import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.server.ParticipationData;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;

public interface SocialNetworkManager extends Manager<SocialNetwork, Long> {

    void addAdmin(User user, Group group);

    void addGroupToAdmins(User userLogged, Group group, Group inGroup) throws SerializableException;

    void addGroupToCollabs(User userLogged, Group group, Group inGroup) throws SerializableException;

    void addGroupToViewers(User userLogged, Group group, Group inGroup) throws SerializableException;

    String requestToJoin(User user, Group inGroup) throws SerializableException;

    void acceptJoinGroup(User userLogged, Group group, Group inGroup) throws SerializableException;

    void denyJoinGroup(User userLogged, Group group, Group inGroup) throws SerializableException;

    void setCollabAsAdmin(User userLogged, Group group, Group inGroup) throws SerializableException;

    void setAdminAsCollab(User userLogged, Group group, Group inGroup) throws SerializableException;

    void deleteMember(User userLogged, Group group, Group inGroup) throws SerializableException,
            AccessViolationException;

    void unJoinGroup(Group groupToUnJoin, Group inGroup) throws SerializableException;

    SocialNetwork find(User user, Group group) throws SerializableException;

    ParticipationData findParticipation(User user, Group group) throws SerializableException;

}
