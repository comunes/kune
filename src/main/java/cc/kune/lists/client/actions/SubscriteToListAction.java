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
package cc.kune.lists.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.ConfirmAskEvent;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.lists.client.rpc.ListsServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SubscriteToListAction extends RolAction {

  public static final String ISMEMBER = "stla-ismember";

  private final EventBus eventBus;
  private final I18nTranslationService i18n;
  private final Provider<ListsServiceAsync> listService;
  private final Session session;
  private final Provider<SignIn> signIn;
  private final StateManager stateManager;

  @Inject
  public SubscriteToListAction(final Provider<SignIn> signIn, final I18nTranslationService i18n,
      final StateManager stateManager, final Session session,
      final Provider<ListsServiceAsync> listService, final EventBus eventBus) {
    super(AccessRolDTO.Viewer, false);
    this.signIn = signIn;
    this.i18n = i18n;
    this.stateManager = stateManager;
    this.session = session;
    this.listService = listService;
    this.eventBus = eventBus;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    if (session.isLogged()) {
      if (isMember()) {
        ConfirmAskEvent.fire(eventBus, i18n.t("Please confirm"), i18n.t("Are you sure?"), i18n.t("Yes"),
            i18n.t("No"), null, null, new OnAcceptCallback() {
              @Override
              public void onSuccess() {
                performAction();
              }
            });
      } else {
        performAction();
      }
    } else {
      signIn.get().setErrorMessage(i18n.t("Sign in or create an account to susbscribe to this list"),
          NotifyLevel.info);
      stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGN_IN,
          session.getCurrentStateToken().toString()));
    }
  }

  private Boolean isMember() {
    return (Boolean) getValue(ISMEMBER);
  }

  private void performAction() {
    NotifyUser.showProgress();
    final boolean subcribe = !isMember();
    listService.get().subscribeToList(session.getUserHash(), session.getCurrentStateToken(),
        Boolean.valueOf(subcribe), new AsyncCallbackSimple<StateContainerDTO>() {
          @Override
          public void onSuccess(final StateContainerDTO result) {
            NotifyUser.info(subcribe ? i18n.t("Subscribed") : i18n.t("Unsubscribed"));
            stateManager.setRetrievedState(result);
            stateManager.refreshCurrentState();
            NotifyUser.hideProgress();
          }
        });
  }

}
