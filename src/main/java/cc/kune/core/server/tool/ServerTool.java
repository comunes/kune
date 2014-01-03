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
package cc.kune.core.server.tool;

import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * Tools must have a corresponding module and must be marked asEagerSingleton.
 * The register method must have the @Inject annotation
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface ServerTool {

  /**
   * Check types before container creation.
   * 
   * @param parentTypeId
   *          the parent type id
   * @param typeId
   *          the type id
   */
  void checkTypesBeforeContainerCreation(String parentTypeId, String typeId);

  /**
   * Check types before content creation.
   * 
   * @param parentTypeId
   *          the parent type id
   * @param typeId
   *          the type id
   */
  void checkTypesBeforeContentCreation(String parentTypeId, String typeId);

  /**
   * Gets the name.
   * 
   * @return the name
   */
  String getName();

  /**
   * Gets the root name.
   * 
   * @return the root name
   */
  String getRootName();

  /**
   * Gets the target.
   * 
   * @return the target
   */
  ServerToolTarget getTarget();

  /**
   * Inits the group.
   * 
   * @param user
   *          the user
   * @param group
   *          the group
   * @param vars
   *          the vars
   * @return the group
   */
  Group initGroup(User user, Group group, Object... vars);

  /**
   * On create container.
   * 
   * @param container
   *          the container
   * @param parent
   *          the parent
   */
  void onCreateContainer(Container container, Container parent);

  /**
   * On create content.
   * 
   * @param content
   *          the content
   * @param parent
   *          the parent
   */
  void onCreateContent(Content content, Container parent);

  /**
   * Register.
   * 
   * @param registry
   *          the registry
   */
  void register(ServerToolRegistry registry);
}
