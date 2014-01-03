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

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class GotoGroupLastVisitedContentAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GotoGroupLastVisitedContentAction extends AbstractExtendedAction {

  /** The group. */
  public GroupDTO group;

  /** The last visited. */
  protected StateToken lastVisited;

  /** The session. */
  private final Session session;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new goto group last visited content action.
   * 
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   */
  @Inject
  public GotoGroupLastVisitedContentAction(final StateManager stateManager, final Session session) {
    this.stateManager = stateManager;
    this.session = session;
    stateManager.onStateChanged(false, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        if (event.getState().getGroup().equals(group)) {
          lastVisited = event.getState().getStateToken();
        }
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    stateManager.gotoStateToken(lastVisited);
  }

  /**
   * Sets the group.
   * 
   * @param group
   *          the new group
   */
  public void setGroup(final GroupDTO group) {
    this.group = group;
    final StateToken currentToken = session.getCurrentStateToken();
    final StateToken groupToken = group.getStateToken();
    lastVisited = currentToken == null ? groupToken : groupToken.getGroup().equals(
        currentToken.getGroup()) ? currentToken : groupToken;
  }

}
