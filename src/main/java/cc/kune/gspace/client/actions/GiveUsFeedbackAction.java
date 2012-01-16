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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.SimpleResponseCallback;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GiveUsFeedbackAction extends AbstractExtendedAction {

  private final Provider<ContentServiceAsync> contentService;
  private final I18nUITranslationService i18n;
  private final Session session;
  private final Provider<SignIn> signIn;
  private final StateManager stateManager;

  @Inject
  public GiveUsFeedbackAction(final Session session, final Provider<SignIn> signIn,
      final StateManager stateManager, final I18nUITranslationService i18n,
      final Provider<ContentServiceAsync> contentService) {
    this.session = session;
    this.signIn = signIn;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.contentService = contentService;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    if (session.isLogged()) {
      NotifyUser.askConfirmation(
          i18n.t("Confirm, please:"),
          i18n.t(
              "Do you want to write us with some positive or negative feedback about [%s]? This can help us to improve these services",
              i18n.getSiteCommonName()), new SimpleResponseCallback() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onSuccess() {
              contentService.get().sendFeedback(session.getUserHash(),
                  i18n.t("Feedback of [%s]", session.getCurrentUser().getShortName()),
                  i18n.t("Edit and write here your feedback."), new AsyncCallbackSimple<String>() {
                    @Override
                    public void onSuccess(final String url) {
                      stateManager.gotoHistoryToken(url);
                    }
                  });
            }
          });
    } else {
      signIn.get().setErrorMessage(i18n.t("Sign in or create an account to give us feedback"),
          NotifyLevel.info);
      stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGN_IN,
          session.getCurrentStateToken().toString()));
    }
  }
}
