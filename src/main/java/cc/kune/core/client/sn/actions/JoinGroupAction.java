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

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.events.MyGroupsChangedEvent;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class JoinGroupAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class JoinGroupAction extends SNRolAction {

  /** The event bus. */
  private final EventBus eventBus;

  /** The sign in. */
  private final Provider<SignIn> signIn;

  /**
   * Instantiates a new join group action.
   * 
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   * @param eventBus
   *          the event bus
   * @param snServiceProvider
   *          the sn service provider
   * @param signIn
   *          the sign in
   * @param rightsClientManager
   *          the rights client manager
   */
  @Inject
  public JoinGroupAction(final StateManager stateManager, final Session session,
      final I18nTranslationService i18n, final IconicResources res, final EventBus eventBus,
      final Provider<SocialNetServiceAsync> snServiceProvider, final Provider<SignIn> signIn,
      final AccessRightsClientManager rightsClientManager) {
    super(stateManager, session, i18n, res, snServiceProvider, rightsClientManager, AccessRolDTO.Viewer,
        false, true, false);
    this.eventBus = eventBus;
    this.signIn = signIn;
    putValue(NAME, i18n.t("Join"));
    putValue(TOOLTIP, i18n.t("Request to Join in this group"));
    putValue(Action.SMALL_ICON, res.add());
    putValue(Action.STYLES, "k-sn-join");
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
    if (session.isLogged()) {
      NotifyUser.askConfirmation(i18n.t("Confirm, please:"), i18n.t("Do you want to join this group?"),
          new SimpleResponseCallback() {
            @Override
            public void onCancel() {
              // Do nothing
            }

            @Override
            public void onSuccess() {
              NotifyUser.showProgress();
              snServiceProvider.get().requestJoinGroup(session.getUserHash(),
                  session.getCurrentState().getStateToken(),
                  new AsyncCallbackSimple<SocialNetworkRequestResult>() {
                    @Override
                    public void onSuccess(final SocialNetworkRequestResult result) {
                      NotifyUser.hideProgress();
                      switch ((result)) {
                      case accepted:
                        NotifyUser.info(i18n.t("You are now member of this group"));
                        MyGroupsChangedEvent.fire(eventBus);
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
      stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGN_IN,
          session.getCurrentStateToken().toString()));
    }

  }

}
