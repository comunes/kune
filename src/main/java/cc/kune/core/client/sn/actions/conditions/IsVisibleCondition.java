/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
 *
 */
package cc.kune.core.client.sn.actions.conditions;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiAddCondition;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IsVisibleCondition implements GuiAddCondition {

  private final Session session;

  @Inject
  public IsVisibleCondition(final Session session) {
    this.session = session;
  }

  @Override
  public boolean mustBeAdded(final GuiActionDescrip descr) {
    final StateAbstractDTO currentState = session.getCurrentState();
    if (currentState instanceof StateContentDTO) {
      return ((StateContentDTO) currentState).getGroupRights().isVisible();
    } else {
      // session.getContainerState() instanceof StateContentDTO)
      return ((StateContainerDTO) currentState).getGroupRights().isVisible();
    }
  }
}
