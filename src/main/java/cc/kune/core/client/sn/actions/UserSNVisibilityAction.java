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
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.UserSNetVisibility;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSNVisibilityAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserSNVisibilityAction extends AbstractExtendedAction {

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The session. */
  private final Session session;

  /** The state manager. */
  private final StateManager stateManager;

  /** The user service async. */
  private final Provider<UserServiceAsync> userServiceAsync;

  /** The visibility. */
  private UserSNetVisibility visibility;

  /**
   * Instantiates a new user sn visibility action.
   * 
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param userServiceProvider
   *          the user service provider
   */
  @Inject
  public UserSNVisibilityAction(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final Provider<UserServiceAsync> userServiceProvider) {
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.userServiceAsync = userServiceProvider;
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
    userServiceAsync.get().setBuddiesVisibility(session.getUserHash(),
        session.getCurrentState().getGroup().getStateToken(), visibility,
        new AsyncCallbackSimple<Void>() {
          @Override
          public void onSuccess(final Void result) {
            // NotifyUser.info(i18n.t("Visibility of your network changed to "
            // + visibility.toString()));
            NotifyUser.info(i18n.t("Visibility of your network changed"));
            stateManager.refreshCurrentStateWithoutCache();
          }
        });

  }

  /**
   * Sets the visibility.
   * 
   * @param visibility
   *          the new visibility
   */
  public void setVisibility(final UserSNetVisibility visibility) {
    this.visibility = visibility;
  }

}
