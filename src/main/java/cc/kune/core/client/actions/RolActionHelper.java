/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.core.client.actions;

import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;

/**
 * The Class RolActionHelper.
 */
public class RolActionHelper {

  /**
   * Checks if is authorized.
   * 
   * @param rolRequired
   *          the rol required
   * @param rights
   *          the rights
   * @return true, if is authorized
   */
  public static boolean isAuthorized(final AccessRolDTO rolRequired, final AccessRights rights) {
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
   * @param newRights
   *          the rights
   * @return true, if is member
   */
  public static boolean isMember(final AccessRights newRights) {
    return newRights.isAdministrable() || newRights.isEditable();
  }

  /**
   * Must add.
   * 
   * @param rolRequired
   *          the minimun rol required to add a action
   * @param higherRol
   *          the higher rol to add or not a action
   * @param isAuthNeeded
   *          the is authentication needed
   * @param isLogged
   *          the is the usser logged
   * @param rights
   *          the rights on the current status
   * 
   * @return true, if the action should be added
   */
  public static boolean mustAdd(final AccessRolDTO rolRequired, final AccessRolDTO higherRol,
      final boolean isAuthNeeded, final boolean isLogged, final AccessRights rights) {

    if (isAuthNeeded) {
      if (!isLogged) {
        return false;
      }
    }
    // We check if the rol < higgerRol (this is used to exclude some minor
    // actions for admins, etc)
    if (higherRol != null) {
      switch (higherRol) {
      case Viewer:
        if (rights.isAdministrable() || rights.isEditable()) {
          return false;
        }
        break;
      case Editor:
        if (rights.isAdministrable()) {
          return false;
        }
        break;
      default:
        break;
      }
    }

    return isAuthorized(rolRequired, rights);
  }

}
