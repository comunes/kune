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
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.SimpleResponseCallback;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class JoinGroupAction extends SNRolAction {

  private final Provider<SignIn> signIn;

  @Inject
  public JoinGroupAction(final StateManager stateManager, final Session session,
      final I18nTranslationService i18n, final CoreResources res,
      final Provider<SocialNetworkServiceAsync> snServiceProvider, final Provider<SignIn> signIn,
      final AccessRightsClientManager rightsClientManager) {
    super(stateManager, session, i18n, res, snServiceProvider, rightsClientManager, AccessRolDTO.Viewer,
        false, true, false);
    this.signIn = signIn;
    putValue(NAME, i18n.t("Join"));
    putValue(TOOLTIP, i18n.t("Request to Join in this group"));
    putValue(Action.SMALL_ICON, res.addGreen());
    putValue(Action.STYLES, "k-sn-join");
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    if (session.isLogged()) {
      NotifyUser.askConfirmation(i18n.t("Confirm, please:"), i18n.t("Do you want to join this group?"),
          new SimpleResponseCallback() {
            @Override
            public void onCancel() {
              // Do nothing
            }

            @Override
            public void onSuccess() {
              NotifyUser.showProgressProcessing();
              snServiceProvider.get().requestJoinGroup(session.getUserHash(),
                  session.getCurrentState().getStateToken(),
                  new AsyncCallbackSimple<SocialNetworkRequestResult>() {
                    @Override
                    public void onSuccess(final SocialNetworkRequestResult result) {
                      NotifyUser.hideProgress();
                      switch ((result)) {
                      case accepted:
                        NotifyUser.info(i18n.t("You are now member of this group"));
                        stateManager.refreshCurrentStateWithoutCache();
                        break;
                      case denied:
                        NotifyUser.important(i18n.t("Sorry this is a closed group"));
                        break;
                      case moderated:
                        NotifyUser.info(i18n.t("Membership requested. Waiting for admins decision"));
                        break;
                      default:
                        NotifyUser.info(i18n.t("Programatic error in ParticipateAction"));
                      }
                    }
                  });
            }
          });
    } else {
      signIn.get().setErrorMessage(i18n.t("Sign in or create an account to participate in this group"),
          NotifyLevel.info);
      stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGNIN,
          session.getCurrentStateToken().toString()));
    }

  }

}