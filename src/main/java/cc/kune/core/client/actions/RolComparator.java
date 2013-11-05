/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.client.actions;

import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;

// TODO: Auto-generated Javadoc
/**
 * The Class RolComparator.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class RolComparator {

  /**
   * Checks if is enabled.
   *
   * @param rolRequired the rol required
   * @param rights the rights
   * @return true, if is enabled
   */
  public static boolean isEnabled(final AccessRolDTO rolRequired, final AccessRights rights) {
    switch (rolRequired) {
    case Administrator:
      return rights.isAdministrable();
    case Editor:
      return rights.isEditable();
    case Viewer:
      return rights.isVisible();
    }
    return false;
  }

  /**
   * Checks if is member.
   *
   * @param newRights the new rights
   * @return true, if is member
   */
  public static boolean isMember(final AccessRights newRights) {
    return newRights.isAdministrable() || newRights.isEditable();
  }

  /**
   * Instantiates a new rol comparator.
   */
  private RolComparator() {
  }
}
