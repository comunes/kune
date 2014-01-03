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

import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface CreationService.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface CreationService {

  /**
   * Copy content.
   * 
   * @param user
   *          the user
   * @param container
   *          the container
   * @param contentToCopy
   *          the content to copy
   * @return the content
   */
  Content copyContent(User user, Container container, Content contentToCopy);

  /**
   * Creates the content.
   * 
   * @param title
   *          the title
   * @param body
   *          the body
   * @param user
   *          the user
   * @param container
   *          the container
   * @param typeId
   *          the type id
   * @return the content
   */
  Content createContent(String title, String body, User user, Container container, String typeId);

  /**
   * Creates the folder.
   * 
   * @param group
   *          the group
   * @param parentFolderId
   *          the parent folder id
   * @param name
   *          the name
   * @param language
   *          the language
   * @param contentTypeId
   *          the content type id
   * @return the container
   */
  Container createFolder(Group group, Long parentFolderId, String name, I18nLanguage language,
      String contentTypeId);

  /**
   * Creates the root folder.
   * 
   * @param group
   *          the group
   * @param name
   *          the name
   * @param rootName
   *          the root name
   * @param typeRoot
   *          the type root
   * @return the container
   */
  Container createRootFolder(Group group, String name, String rootName, String typeRoot);

}
