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

import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class AccessRightsUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class AccessRightsUtils {

  /** The rights service. */
  @Inject
  private static AccessRightsService rightsService;

  /**
   * Correct member.
   * 
   * @param user
   *          the user
   * @param group
   *          the group
   * @param memberType
   *          the member type
   * @return true, if successful
   */
  public static boolean correctMember(final User user, final Group group, final AccessRol memberType) {

    final AccessRights accessRights = rightsService.get(user, group.getSocialNetwork().getAccessLists());

    switch (memberType) {
    case Administrator:
      return accessRights.isAdministrable();
    case Editor:
      return accessRights.isEditable();
    default:
      return accessRights.isVisible();
    }
  }
}
