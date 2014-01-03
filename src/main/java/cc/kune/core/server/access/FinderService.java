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
package cc.kune.core.server.access;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.Rate;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface FinderService.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface FinderService {

  /**
   * Find by root on group.
   * 
   * @param groupName
   *          the group name
   * @param toolName
   *          the tool name
   * @return the content
   * @throws DefaultException
   *           the default exception
   */
  Content findByRootOnGroup(String groupName, String toolName) throws DefaultException;

  /**
   * Gets the container.
   * 
   * @param folderId
   *          the folder id
   * @return the container
   * @throws DefaultException
   *           the default exception
   */
  Container getContainer(Long folderId) throws DefaultException;

  /**
   * Gets the container.
   * 
   * @param folderId
   *          the folder id
   * @return the container
   */
  Container getContainer(String folderId);

  /**
   * Gets the container by wave ref.
   * 
   * @param waveRef
   *          the wave ref
   * @return the container by wave ref
   */
  Content getContainerByWaveRef(String waveRef);

  /**
   * Gets the content.
   * 
   * @param contentId
   *          the content id
   * @return the content
   * @throws DefaultException
   *           the default exception
   */
  Content getContent(Long contentId) throws DefaultException;

  /**
   * Gets the content.
   * 
   * @param contentId
   *          the content id
   * @return the content
   * @throws ContentNotFoundException
   *           the content not found exception
   */
  Content getContent(String contentId) throws ContentNotFoundException;

  /**
   * Gets the content or def content.
   * 
   * @param token
   *          the token
   * @param defaultGroup
   *          the default group
   * @return the content or def content
   * @throws DefaultException
   *           the default exception
   */
  Content getContentOrDefContent(StateToken token, Group defaultGroup) throws DefaultException;

  /**
   * Gets the folder.
   * 
   * @param folderId
   *          the folder id
   * @return the folder
   * @throws DefaultException
   *           the default exception
   */
  Container getFolder(Long folderId) throws DefaultException;

  /**
   * Gets the rate.
   * 
   * @param user
   *          the user
   * @param content
   *          the content
   * @return the rate
   */
  Rate getRate(User user, Content content);

  /**
   * Gets the rate avg.
   * 
   * @param content
   *          the content
   * @return the rate avg
   */
  Double getRateAvg(Content content);

  /**
   * Gets the rate by users.
   * 
   * @param content
   *          the content
   * @return the rate by users
   */
  Long getRateByUsers(Content content);

}
