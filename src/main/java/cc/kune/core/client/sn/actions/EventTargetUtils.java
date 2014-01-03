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
package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

// TODO: Auto-generated Javadoc
/**
 * The Class EventTargetUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EventTargetUtils {

  /**
   * Gets the target token.
   * 
   * @param event
   *          the event
   * @return the target token
   */
  public static StateToken getTargetToken(final ActionEvent event) {
    StateToken token;
    final Object target = event.getTarget();
    if (target instanceof GroupDTO) {
      token = ((GroupDTO) target).getStateToken();
    } else {
      token = ((UserSimpleDTO) target).getStateToken();
    }
    return token;
  }

  /**
   * Checks if is person.
   * 
   * @param event
   *          the event
   * @return true, if is person
   */
  public static boolean isPerson(final ActionEvent event) {
    final Object target = event.getTarget();
    final boolean isUserDTO = target instanceof UserSimpleDTO;
    final boolean isGroup = target instanceof GroupDTO;
    return isUserDTO || (isGroup && ((GroupDTO) target).isPersonal());
  }

}
