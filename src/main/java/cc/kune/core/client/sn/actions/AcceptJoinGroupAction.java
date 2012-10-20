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

public class AcceptJoinGroupAction extends AbstractExtendedAction {
  private final I18nTranslationService i18n;
  private final Session session;
  private final Provider<SocialNetServiceAsync> snServiceProvider;

  private final StateManager stateManager;

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