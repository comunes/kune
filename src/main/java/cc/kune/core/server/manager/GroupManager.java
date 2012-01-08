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
import java.util.Set;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupShortNameInUseException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;

public interface GroupManager extends Manager<Group, Long> {

  void changeDefLicense(User user, Group group, String licenseShortName);

  void changeWsTheme(User user, Group group, String theme) throws AccessViolationException;

  void checkIfLongNameAreInUse(String shortName);

  void checkIfShortNameAreInUse(String longName);

  void clearGroupBackImage(Group group);

  Group createGroup(Group group, User user, String publicDescrip) throws GroupShortNameInUseException,
      UserMustBeLoggedException;

  Group createUserGroup(User user) throws GroupShortNameInUseException, EmailAddressInUseException;

  Group createUserGroup(User user, boolean wantPersonalHomepage) throws GroupShortNameInUseException,
      EmailAddressInUseException;

  Set<Group> findAdminInGroups(Long groupId);

  Group findByShortName(String groupName);

  Set<Group> findCollabInGroups(Long groupId);

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

  void setGroupBackgroundImage(Group group, String backgroundFileName, String mime);

  void setToolEnabled(User userLogged, String groupShortName, String toolName, boolean enabled);

  /**
   * @param groupId
   *          the id of the Group to update
   * @param groupDTO
   *          the group with the name values to change (currently only changes
   *          shortname & longname)
   * @return returns the Group just persisted
   */
  Group update(Long groupId, GroupDTO groupDTO);

}
