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

import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

public interface GroupManager extends Manager<Group, Long> {

    Group findByShortName(String groupName);

    List<Group> findAdminInGroups(Long groupId);

    List<Group> findCollabInGroups(Long groupId);

    Group createGroup(Group group, User user) throws GroupNameInUseException, UserMustBeLoggedException;

    Group createUserGroup(User user) throws GroupNameInUseException, EmailAddressInUseException;

    Group getDefaultGroup();

    void changeWsTheme(User user, Group group, String theme) throws AccessViolationException;

    /**
     * IMPORTANT: returns null if userId is null
     * 
     * @param userId
     * @return
     */
    Group getGroupOfUserWithId(Long userId);

    List<Group> search(String search) throws ParseException;

    List<Group> search(String search, Integer firstResult, Integer maxResults) throws ParseException;

    void reIndex();

}
