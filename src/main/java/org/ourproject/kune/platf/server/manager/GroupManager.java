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
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;

public interface GroupManager extends Manager<Group, Long> {

    Group findByShortName(String groupName);

    // FIXME: (dani) no entiendo por qué estos dos métodos (los he hecho yo ;)
    // seguro que tiene razón de ser, pero el nombre no lo refleja!!!
    Group createGroup(String shortName, String longName, User user) throws SerializableException;

    Group createGroup(Group group, User user) throws SerializableException;

    Group createUserGroup(User user, License defaultLicense);

    Group getDefaultGroup();

    /**
     * IMPORTANT: returns null if userId is null
     * 
     * @param userId
     * @return
     */
    Group getGroupOfUserWithId(Long userId);

}
