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

package cc.kune.trash.server;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.trash.shared.TrashToolConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class TrashServerUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TrashServerUtils {

  /**
   * In trash.
   * 
   * @param container
   *          the container
   * @return true, if successful
   */
  public static boolean inTrash(final Container container) {
    return isTrash(container.getStateToken());
  }

  /**
   * In trash.
   * 
   * @param content
   *          the content
   * @return true, if successful
   */
  public static boolean inTrash(final Content content) {
    return inTrash(content.getContainer());
  }

  /**
   * Checks if is trash.
   * 
   * @param token
   *          the token
   * @return true, if is trash
   */
  public static boolean isTrash(final StateToken token) {
    return token.getTool().equals(TrashToolConstants.TOOL_NAME);
  }

}
