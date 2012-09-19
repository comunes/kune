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
package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;

import com.google.inject.Inject;

public class GotoGroupLastVisitedContentAction extends AbstractExtendedAction {

  private final StateManager stateManager;
  public GroupDTO group;
  protected StateToken lastVisited;
  private final Session session;

  @Inject
  public GotoGroupLastVisitedContentAction(final StateManager stateManager, Session session) {
    this.stateManager = stateManager;
    this.session = session;
    stateManager.onStateChanged(false, new StateChangedHandler() {
      @Override
      public void onStateChanged(StateChangedEvent event) {
        if (event.getState().getGroup().equals(group)) {
          lastVisited = event.getState().getStateToken();
        }
      }
    });
  }

  public void setGroup(GroupDTO group) {
    this.group = group;
    StateToken currentToken = session.getCurrentStateToken();
    StateToken groupToken = group.getStateToken();
    lastVisited = groupToken.getGroup().equals(currentToken.getGroup()) ? currentToken : groupToken;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    stateManager.gotoStateToken(lastVisited);
  }

}
