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

import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.TagUserContent;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface TagUserContentManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface TagUserContentManager extends Manager<TagUserContent, Long> {

  /**
   * Gets the tag cloud result by group.
   * 
   * @param group
   *          the group
   * @return the tag cloud result by group
   */
  TagCloudResult getTagCloudResultByGroup(Group group);

  /**
   * Gets the tags as string.
   * 
   * @param user
   *          the user
   * @param content
   *          the content
   * @return the tags as string
   */
  String getTagsAsString(User user, Content content);

  /**
   * Removes the.
   * 
   * @param user
   *          the user
   * @param content
   *          the content
   */
  void remove(User user, Content content);

  /**
   * Sets the tags.
   * 
   * @param user
   *          the user
   * @param content
   *          the content
   * @param tags
   *          the tags
   */
  void setTags(User user, Content content, String tags);

}
