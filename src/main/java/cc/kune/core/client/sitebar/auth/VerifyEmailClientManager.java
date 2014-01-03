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
package cc.kune.core.client.sitebar.auth;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.errors.EmailHashExpiredException;
import cc.kune.core.client.errors.EmailHashInvalidException;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.HistoryTokenMustBeAuthCallback;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.gspace.client.options.general.UserOptGeneral;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class VerifyEmailClientManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class VerifyEmailClientManager {

  /**
   * Instantiates a new verify email client manager.
   * 
   * @param session
   *          the session
   * @param tokens
   *          the tokens
   * @param optGeneral
   *          the opt general
   * @param i18n
   *          the i18n
   * @param userService
   *          the user service
   */
  @Inject
  VerifyEmailClientManager(final Session session, final SiteTokenListeners tokens,
      final Provider<UserOptGeneral> optGeneral, final I18nTranslationService i18n,
      final Provider<UserServiceAsync> userService) {
    tokens.put(SiteTokens.VERIFY_EMAIL,
        new HistoryTokenMustBeAuthCallback(i18n.t("Sign in to verify your email")) {
          @Override
          public void onHistoryToken(final String token) {
            userService.get().verifyPasswordHash(session.getUserHash(), token,
                new AsyncCallback<Void>() {
                  @Override
                  public void onFailure(final Throwable caught) {
                    if (caught instanceof EmailHashExpiredException) {
                      NotifyUser.error(i18n.t("Email confirmation code expired"), true);
                    } else if (caught instanceof EmailHashInvalidException) {
                      NotifyUser.error(i18n.t("Invalid confirmation code"), true);
                    } else {
                      NotifyUser.error(i18n.t("Other error trying to verify your password"), true);
                    }
                  }

                  @Override
                  public void onSuccess(final Void result) {
                    NotifyUser.info("Great. Your email is now verified");
                    session.getCurrentUser().setEmailVerified(true);
                    // This get NPE
                    // if (optGeneral.get().isVisible()) {
                    // optGeneral.get().update();
                    // }
                  }
                });
          }
        });

  }
}
