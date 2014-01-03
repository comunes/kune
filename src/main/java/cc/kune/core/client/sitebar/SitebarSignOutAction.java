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
package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.notify.ProgressHideEvent;
import cc.kune.common.client.notify.ProgressShowEvent;
import cc.kune.core.client.auth.WaveClientSimpleAuthenticator;
import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.sitebar.SitebarSignOutLink.BeforeSignOut;
import cc.kune.core.client.state.Session;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SitebarSignOutAction extends AbstractSignOutAction {
  /** The before sign out. */
  private final BeforeSignOut beforeSignOut;

  /** The event bus. */
  private final EventBus eventBus;

  /** The session. */
  private final Session session;

  /** The user service. */
  private final Provider<UserServiceAsync> userService;

  /** The wave auth. */
  private final WaveClientSimpleAuthenticator waveAuth;

  /**
   * Instantiates a new sitebar sign out action.
   * 
   * @param eventBus
   *          the event bus
   * @param i18n
   *          the i18n
   * @param beforeSignOut
   *          the before sign out
   * @param userService
   *          the user service
   * @param session
   *          the session
   * @param waveAuth
   *          the wave auth
   */
  @Inject
  public SitebarSignOutAction(final EventBus eventBus, final BeforeSignOut beforeSignOut,
      final Provider<UserServiceAsync> userService, final Session session,
      final WaveClientSimpleAuthenticator waveAuth) {
    super();
    this.eventBus = eventBus;
    this.userService = userService;
    this.session = session;
    this.beforeSignOut = beforeSignOut;
    this.waveAuth = waveAuth;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
   * common.client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    eventBus.fireEvent(new ProgressShowEvent());
    if (beforeSignOut.checkBeforeAction()) {
      waveAuth.doLogout(new AsyncCallback<Void>() {
        @Override
        public void onFailure(final Throwable caught) {
          onLogoutFail(caught);
        }

        @Override
        public void onSuccess(final Void result) {
          userService.get().logout(session.getUserHash(), new AsyncCallback<Void>() {
            @Override
            public void onFailure(final Throwable caught) {
              onLogoutFail(caught);
            }

            @Override
            public void onSuccess(final Void arg0) {
              eventBus.fireEvent(new ProgressHideEvent());
              session.signOut();
            }

          });
        }
      });
    } else {
      eventBus.fireEvent(new ProgressHideEvent());
    }
  }

  /**
   * On logout fail.
   * 
   * @param caught
   *          the caught
   */
  private void onLogoutFail(final Throwable caught) {
    eventBus.fireEvent(new ProgressHideEvent());
    if (caught instanceof SessionExpiredException) {
      session.signOut();
    } else if (caught instanceof UserMustBeLoggedException) {
      session.signOut();
    } else {
      throw new UIException("Other kind of exception in doLogout", caught);
    }
  }

}
