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
package cc.kune.core.server.content;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.manager.Manager;
import cc.kune.core.server.manager.SearchResult;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;

// TODO: Auto-generated Javadoc
/**
 * The Interface ContainerManager.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface ContainerManager extends Manager<Container, Long> {

  /**
   * Creates the folder.
   * 
   * @param group
   *          the group
   * @param parent
   *          the parent
   * @param name
   *          the name
   * @param language
   *          the language
   * @param typeId
   *          the type id
   * @return the container
   */
  Container createFolder(Group group, Container parent, String name, I18nLanguage language, String typeId);

  /**
   * Creates the root folder.
   * 
   * @param group
   *          the group
   * @param toolName
   *          the tool name
   * @param name
   *          the name
   * @param rootType
   *          the root type
   * @return the container
   */
  Container createRootFolder(Group group, String toolName, String name, final String rootType);

  /**
   * Find if exists title.
   * 
   * @param container
   *          the container
   * @param title
   *          the title
   * @return true, if successful
   */
  boolean findIfExistsTitle(Container container, String title);

  /**
   * Gets the trash folder.
   * 
   * @param group
   *          the group
   * @return the trash folder
   */
  Container getTrashFolder(Group group);

  /**
   * Checks for trash folder.
   * 
   * @param group
   *          the group
   * @return true, if successful
   */
  boolean hasTrashFolder(Group group);

  /**
   * Move container.
   * 
   * @param container
   *          the container
   * @param newContainer
   *          the new container
   * @return the container
   */
  Container moveContainer(Container container, Container newContainer);

  /**
   * Purge all.
   * 
   * @param container
   *          the container
   * @return the container
   */
  Container purgeAll(Container container);

  /**
   * Purge container.
   * 
   * @param container
   *          the container
   * @return the container
   */
  Container purgeContainer(Container container);

  /**
   * Rename folder.
   * 
   * @param group
   *          the group
   * @param container
   *          the container
   * @param newName
   *          the new name
   * @return the container
   * @throws DefaultException
   *           the default exception
   */
  Container renameFolder(Group group, Container container, String newName) throws DefaultException;

  /**
   * Search.
   * 
   * @param search
   *          the search
   * @return the search result
   */
  SearchResult<Container> search(String search);

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
  SearchResult<Container> search(String search, Integer firstResult, Integer maxResults);

  /**
   * Sets the access list.
   * 
   * @param container
   *          the container
   * @param accessList
   *          the access list
   */
  void setAccessList(Container container, AccessLists accessList);
}
