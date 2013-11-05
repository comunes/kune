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
package cc.kune.core.client.sn.actions.conditions;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiAddCondition;
import cc.kune.common.client.errors.UIException;
import cc.kune.core.client.contacts.SimpleContactManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IsBuddieCondition implements GuiAddCondition {

  private final Session session;
  private final SimpleContactManager simpleContactManager;

  @Inject
  public IsBuddieCondition(final Session session, final SimpleContactManager simpleContactManager) {
    this.simpleContactManager = simpleContactManager;
    this.session = session;
  }

  private boolean isBuddie(final String targetName) {
    return simpleContactManager.isBuddy(targetName);
  }

  private boolean isThisGroupInRoster(final GuiActionDescrip descr) {
    final String targetName = ((GroupDTO) descr.getTarget()).getShortName();
    return isBuddie(targetName);
  }

  private boolean isThisPersonInRoster(final GuiActionDescrip descr) {
    final String targetName = ((UserSimpleDTO) descr.getTarget()).getShortName();
    return isBuddie(targetName);
  }

  @Override
  public boolean mustBeAdded(final GuiActionDescrip descr) {
    if (session.isNotLogged()) {
      return false;
    }
    if (descr.getTarget() instanceof UserSimpleDTO) {
      return isThisPersonInRoster(descr);
    } else if (descr.getTarget() instanceof GroupDTO) {
      return isThisGroupInRoster(descr);
    } else {
      throw new UIException("Unsupported target");
    }
  }
}
