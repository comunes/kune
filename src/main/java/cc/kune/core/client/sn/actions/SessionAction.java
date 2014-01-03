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
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class SessionAction is used to create actions that depends of current
 * session auth status.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class SessionAction extends AbstractExtendedAction {

  /** The session. */
  protected final Session session;

  /**
   * Instantiates a new session action.
   * 
   * @param session
   *          the kune session
   * @param authNeed
   *          if user authentication is needed
   */
  @Inject
  public SessionAction(final Session session, final boolean authNeed) {
    this.session = session;
    // Don't need to unregister
    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        refreshStatus(authNeed, event.isLogged());
      }
    });
  }

  /**
   * Refresh status.
   * 
   * @param authNeed
   *          the auth need
   * @param isLogged
   *          the is logged
   */
  public void refreshStatus(final boolean authNeed, final boolean isLogged) {
    boolean visible = false;
    final boolean noLogged = !isLogged;
    if (authNeed && noLogged) {
      visible = false;
    } else {
      // Auth ok
      visible = true;
    }
    setVisible(visible);
  }

  /**
   * Sets the visible.
   * 
   * @param visible
   *          the new visible
   */
  public void setVisible(final boolean visible) {
    setEnabled(visible);
    putValue(GuiActionDescrip.VISIBLE, visible);
  }
}
