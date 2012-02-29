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

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.GroupDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * This action is similar to @link {@link UnJoinFromCurrentGroupAction} but is
 * used when you are not in this group (for instace you can leave a group from
 * your home page)
 */
public class UnJoinFromThisGroupAction extends RolAction {

  private final I18nTranslationService i18n;
  private final Session session;
  private final Provider<SocialNetServiceAsync> snServiceProvider;
  private final StateManager stateManager;

  @Inject
  public UnJoinFromThisGroupAction(final StateManager stateManager, final Session session,
      final I18nTranslationService i18n, final CoreResources res,
      final Provider<SocialNetServiceAsync> snServiceProvider,
      final AccessRightsClientManager rightsClientManager) {
    super(AccessRolDTO.Editor, true);
    this.stateManager = stateManager;
    this.session = session;
    this.i18n = i18n;
    this.snServiceProvider = snServiceProvider;
    putValue(NAME, i18n.t("Leave this group"));
    // putValue(TOOLTIP, i18n.t("I want to leave this group"));
    putValue(Action.SMALL_ICON, res.del());
    putValue(Action.STYLES, "k-sn-join");
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    NotifyUser.askConfirmation(i18n.t("Leave this group"), i18n.t("Are you sure?"),
        new OnAcceptCallback() {
          @Override
          public void onSuccess() {
            NotifyUser.showProgress();
            snServiceProvider.get().unJoinGroup(session.getUserHash(),
                ((GroupDTO) event.getTarget()).getStateToken(), new AsyncCallbackSimple<Void>() {
                  @Override
                  public void onSuccess(final Void result) {
                    NotifyUser.hideProgress();
                    NotifyUser.info(i18n.t("Removed as member"));
                    stateManager.refreshCurrentStateWithoutCache();
                  }
                });
          }
        });
  }

}