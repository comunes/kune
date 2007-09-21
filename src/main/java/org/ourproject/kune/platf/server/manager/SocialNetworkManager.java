/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;

public interface SocialNetworkManager extends Manager<SocialNetwork, Long> {

    void addAdmin(User user, Group group);

    void addGroupToAdmins(Group group, Group inGroup);

    void addGroupToCollabs(Group group, Group inGroup);

    void addGroupToViewers(Group group, Group inGroup);

    String requestToJoin(User user, Group inGroup) throws SerializableException;

    void acceptJoinGroup(Group group, Group inGroup) throws SerializableException;

    void deleteMember(Group group, Group inGroup) throws SerializableException;

    void denyJoinGroup(Group group, Group inGroup) throws SerializableException;

    void setCollabAsAdmin(Group group, Group inGroup) throws SerializableException;

    void setAdminAsCollab(Group group, Group inGroup) throws SerializableException;

}
