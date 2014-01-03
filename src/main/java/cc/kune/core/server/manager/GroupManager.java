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
import java.util.Set;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupShortNameInUseException;
import cc.kune.core.client.errors.ToolIsDefaultException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface GroupManager.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface GroupManager extends Manager<Group, Long> {

  /**
   * Change def license.
   * 
   * @param user
   *          the user
   * @param group
   *          the group
   * @param licenseShortName
   *          the license short name
   */
  void changeDefLicense(User user, Group group, String licenseShortName);

  /**
   * Change ws theme.
   * 
   * @param user
   *          the user
   * @param group
   *          the group
   * @param theme
   *          the theme
   * @throws AccessViolationException
   *           the access violation exception
   */
  void changeWsTheme(User user, Group group, String theme) throws AccessViolationException;

  /**
   * Check if long name are in use.
   * 
   * @param shortName
   *          the short name
   */
  void checkIfLongNameAreInUse(String shortName);

  /**
   * Check if short name are in use.
   * 
   * @param longName
   *          the long name
   */
  void checkIfShortNameAreInUse(String longName);

  /**
   * Clear group back image.
   * 
   * @param group
   *          the group
   */
  void clearGroupBackImage(Group group);

  /**
   * Count # of registered groups (only used to see in the database is
   * initialized).
   * 
   * @return the int
   */
  Long count();

  /**
   * Creates the group.
   * 
   * @param group
   *          the group
   * @param user
   *          the user
   * @param publicDescrip
   *          the public descrip
   * @return the group
   * @throws GroupShortNameInUseException
   *           the group short name in use exception
   * @throws UserMustBeLoggedException
   *           the user must be logged exception
   */
  Group createGroup(Group group, User user, String publicDescrip) throws GroupShortNameInUseException,
      UserMustBeLoggedException;

  /**
   * Creates the user group.
   * 
   * @param user
   *          the user
   * @return the group
   * @throws GroupShortNameInUseException
   *           the group short name in use exception
   * @throws EmailAddressInUseException
   *           the email address in use exception
   */
  Group createUserGroup(User user) throws GroupShortNameInUseException, EmailAddressInUseException;

  /**
   * Creates the user group.
   * 
   * @param user
   *          the user
   * @param wantPersonalHomepage
   *          the want personal homepage
   * @return the group
   * @throws GroupShortNameInUseException
   *           the group short name in use exception
   * @throws EmailAddressInUseException
   *           the email address in use exception
   */
  Group createUserGroup(User user, boolean wantPersonalHomepage) throws GroupShortNameInUseException,
      EmailAddressInUseException;

  /**
   * Find admin in groups.
   * 
   * @param groupId
   *          the group id
   * @return the sets the
   */
  Set<Group> findAdminInGroups(Long groupId);

  /**
   * Find by short name.
   * 
   * @param groupName
   *          the group name
   * @return the group
   */
  Group findByShortName(String groupName);

  /**
   * Find collab in groups.
   * 
   * @param groupId
   *          the group id
   * @return the sets the
   */
  Set<Group> findCollabInGroups(Long groupId);

  /**
   * Find enabled tools.
   * 
   * @param id
   *          the id
   * @return the list
   */
  List<String> findEnabledTools(Long id);

  /**
   * IMPORTANT: returns null if userId is null.
   * 
   * @param userId
   *          the user id
   * @return the group of user with id
   */
  Group getGroupOfUserWithId(Long userId);

  /**
   * Gets the site default group.
   * 
   * @return the site default group
   */
  Group getSiteDefaultGroup();

  /**
   * Inits the trash.
   * 
   * @param group
   *          the group
   */
  void initTrash(Group group);

  /**
   * Search.
   * 
   * @param search
   *          the search
   * @return the search result
   */
  SearchResult<Group> search(String search);

  /**
   * Search.
   * 
   * @param search
   *          the search
   * @param firstResult
   *          the first result
   * @param maxResults
   *          the max results
   * @return the search result
   */
  SearchResult<Group> search(String search, Integer firstResult, Integer maxResults);

  /**
   * Sets the default content.
   * 
   * @param groupShortName
   *          the group short name
   * @param content
   *          the content
   */
  void setDefaultContent(String groupShortName, Content content);

  /**
   * Sets the group background image.
   * 
   * @param group
   *          the group
   * @param backgroundFileName
   *          the background file name
   * @param mime
   *          the mime
   */
  void setGroupBackgroundImage(Group group, String backgroundFileName, String mime);

  /**
   * Sets the tool enabled.
   * 
   * @param userLogged
   *          the user logged
   * @param groupShortName
   *          the group short name
   * @param toolName
   *          the tool name
   * @param enabled
   *          the enabled
   * @throws ToolIsDefaultException
   *           the tool is default exception
   */
  void setToolEnabled(User userLogged, String groupShortName, String toolName, boolean enabled)
      throws ToolIsDefaultException;

  /**
   * Update.
   * 
   * @param groupId
   *          the id of the Group to update
   * @param groupDTO
   *          the group with the name values to change (currently only changes
   *          shortname & longname)
   * @return returns the Group just persisted
   */
  Group update(Long groupId, GroupDTO groupDTO);

}
