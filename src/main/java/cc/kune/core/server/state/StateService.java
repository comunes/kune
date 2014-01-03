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
package cc.kune.core.server.state;

import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface StateService.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface StateService {

  /**
   * Creates the.
   * 
   * @param userLogged
   *          the user logged
   * @param container
   *          the container
   * @return the state container
   */
  StateContainer create(User userLogged, Container container);

  /**
   * Creates the.
   * 
   * @param userLogged
   *          the user logged
   * @param content
   *          the content
   * @return the state content
   */
  StateContent create(User userLogged, Content content);

  /**
   * Creates the no home.
   * 
   * @param user
   *          the user
   * @param groupShortName
   *          the group short name
   * @return the state no content
   */
  StateNoContent createNoHome(User user, String groupShortName);
}
