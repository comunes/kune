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

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class RolAction.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class RolAction extends AbstractExtendedAction {

  /** The auth need. */
  private final boolean authNeed;
  
  /** The rol required. */
  private final AccessRolDTO rolRequired;

  /**
   * Instantiates a new rol action.
   *
   * @param rolRequired the Rol required to allow this action
   * @param authNeed if we need to be authenticated to execute this action
   */
  @Inject
  public RolAction(final AccessRolDTO rolRequired, final boolean authNeed) {
    this.rolRequired = rolRequired;
    this.authNeed = authNeed;
  }

  /**
   * Gets the rol required.
   *
   * @return the rol required
   */
  public AccessRolDTO getRolRequired() {
    return rolRequired;
  }

  /**
   * Checks if is auth need.
   *
   * @return true, if is auth need
   */
  public boolean isAuthNeed() {
    return authNeed;
  }

}
