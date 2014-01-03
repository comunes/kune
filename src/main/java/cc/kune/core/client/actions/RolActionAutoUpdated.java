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
package cc.kune.core.client.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.events.AccessRightsChangedEvent;
import cc.kune.core.client.events.AccessRightsChangedEvent.AccessRightsChangedHandler;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * This is a RolAction (a Action that store which permissions are needed to
 * permit its execution) but that auto refresh its status depending on the state
 * of the application (if we are authenticated, and so on).
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class RolActionAutoUpdated extends AbstractExtendedAction {

  /** The session. */
  protected final Session session;

  /** The state manager. */
  protected final StateManager stateManager;

  /**
   * Instantiates a new rol action auto updated.
   * 
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param rightsManager
   *          the rights manager
   * @param rolRequired
   *          the rol required
   * @param authNeed
   *          the auth need
   * @param visibleForNonMemb
   *          the visible for non memb
   * @param visibleForMembers
   *          the visible for members
   */
  @Inject
  public RolActionAutoUpdated(final StateManager stateManager, final Session session,
      final AccessRightsClientManager rightsManager, final AccessRolDTO rolRequired,
      final boolean authNeed, final boolean visibleForNonMemb, final boolean visibleForMembers) {
    this.stateManager = stateManager;
    this.session = session;
    session.onUserSignInOrSignOut(false, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        if (session.getCurrentState() != null) {
          refreshStatus(rolRequired, authNeed, session.isLogged(), visibleForMembers, visibleForNonMemb,
              session.getCurrentState().getGroupRights());
        }
      }
    });
    rightsManager.onRightsChanged(true, new AccessRightsChangedHandler() {
      @Override
      public void onAccessRightsChanged(final AccessRightsChangedEvent event) {
        refreshStatus(rolRequired, authNeed, session.isLogged(), visibleForMembers, visibleForNonMemb,
            event.getCurrentRights());
      }
    });
  }

  /**
   * Refresh status.
   * 
   * @param rolRequired
   *          the rol required
   * @param authNeed
   *          the auth need
   * @param isLogged
   *          the is logged
   * @param visibleForMembers
   *          the visible for members
   * @param visibleForNonMemb
   *          the visible for non memb
   * @param newRights
   *          the new rights
   */
  public void refreshStatus(final AccessRolDTO rolRequired, final boolean authNeed,
      final boolean isLogged, final boolean visibleForMembers, final boolean visibleForNonMemb,
      final AccessRights newRights) {

    // TODO move this to {link @RolActionHelper}
    boolean newEnabled = false;
    if (authNeed && !isLogged) {
      newEnabled = false;
    } else {
      // Auth ok
      newEnabled = RolActionHelper.isAuthorized(rolRequired, newRights);
      if (newEnabled) {
        final boolean isMember = RolActionHelper.isMember(newRights);
        newEnabled = isMember && visibleForMembers || !isMember && visibleForNonMemb;
      }
    }
    setEnabled(!newEnabled);
    setEnabled(newEnabled);

    // Workaround to force change ...
    putValue(GuiActionDescrip.VISIBLE, !newEnabled);
    putValue(GuiActionDescrip.VISIBLE, newEnabled);
  }
}
