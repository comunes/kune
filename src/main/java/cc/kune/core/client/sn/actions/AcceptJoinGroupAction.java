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
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class AcceptJoinGroupAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AcceptJoinGroupAction extends AbstractExtendedAction {

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The session. */
  private final Session session;

  /** The sn service provider. */
  private final Provider<SocialNetServiceAsync> snServiceProvider;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new accept join group action.
   * 
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   * @param snServiceProvider
   *          the sn service provider
   */
  @Inject
  public AcceptJoinGroupAction(final StateManager stateManager, final Session session,
      final I18nTranslationService i18n, final IconicResources res,
      final Provider<SocialNetServiceAsync> snServiceProvider) {
    this.stateManager = stateManager;
    this.session = session;
    this.i18n = i18n;
    this.snServiceProvider = snServiceProvider;
    putValue(NAME, i18n.t("Accept this member"));
    putValue(Action.SMALL_ICON, res.add());
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
    NotifyUser.showProgress();
    snServiceProvider.get().acceptJoinGroup(session.getUserHash(),
        session.getCurrentState().getStateToken(), ((GroupDTO) event.getTarget()).getShortName(),
        new AsyncCallbackSimple<SocialNetworkDataDTO>() {
          @Override
          public void onSuccess(final SocialNetworkDataDTO result) {
            NotifyUser.hideProgress();
            NotifyUser.info(i18n.t("Member accepted"));
            stateManager.setSocialNetwork(result);
          }
        });
  }

}
