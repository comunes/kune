/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.manager;

import java.util.List;


import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupNameInUseException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.manager.impl.SearchResult;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;

public interface GroupManager extends Manager<Group, Long> {

    void changeDefLicense(User user, Group group, String licenseShortName);

    void changeWsTheme(User user, Group group, String theme) throws AccessViolationException;

    void clearGroupBackImage(Group group);

    Group createGroup(Group group, User user) throws GroupNameInUseException, UserMustBeLoggedException;

    Group createUserGroup(User user) throws GroupNameInUseException, EmailAddressInUseException;

    Group createUserGroup(User user, boolean wantPersonalHomepage) throws GroupNameInUseException,
            EmailAddressInUseException;

    List<Group> findAdminInGroups(Long groupId);

    Group findByShortName(String groupName);

    List<Group> findCollabInGroups(Long groupId);

    List<String> findEnabledTools(Long id);

    /**
     * IMPORTANT: returns null if userId is null
     * 
     * @param userId
     * @return
     */
    Group getGroupOfUserWithId(Long userId);

    Group getSiteDefaultGroup();

    SearchResult<Group> search(String search);

    SearchResult<Group> search(String search, Integer firstResult, Integer maxResults);

    void setDefaultContent(String groupShortName, Content content);

    void setGroupBackImage(Group group, Content content);

    void setToolEnabled(User userLogged, String groupShortName, String toolName, boolean enabled);

}
