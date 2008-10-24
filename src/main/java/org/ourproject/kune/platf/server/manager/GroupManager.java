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

import java.util.List;

import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

public interface GroupManager extends Manager<Group, Long> {

    void changeWsTheme(User user, Group group, String theme) throws AccessViolationException;

    Group createGroup(Group group, User user) throws GroupNameInUseException, UserMustBeLoggedException;

    Group createUserGroup(User user) throws GroupNameInUseException, EmailAddressInUseException;

    Group createUserGroup(User user, boolean wantPersonalHomepage) throws GroupNameInUseException,
            EmailAddressInUseException;

    List<Group> findAdminInGroups(Long groupId);

    Group findByShortName(String groupName);

    List<Group> findCollabInGroups(Long groupId);

    List<String> findEnabledTools(Long id);

    Group getSiteDefaultGroup();

    /**
     * IMPORTANT: returns null if userId is null
     * 
     * @param userId
     * @return
     */
    Group getGroupOfUserWithId(Long userId);

    void reIndex();

    SearchResult<Group> search(String search);

    SearchResult<Group> search(String search, Integer firstResult, Integer maxResults);

    void setDefaultContent(String groupShortName, Content content);

    void setGroupLogo(Group group, Content content);

}
