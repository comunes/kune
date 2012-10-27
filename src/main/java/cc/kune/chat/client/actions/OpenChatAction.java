/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.chat.client.actions;

import cc.kune.chat.client.ChatClient;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContainerDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class OpenChatAction extends RolAction {

  private final Provider<ChatClient> chatClient;
  private final I18nTranslationService i18n;
  private final Session session;
  private final Provider<SignIn> signIn;
  private final StateManager stateManager;

  @Inject
  public OpenChatAction(final Provider<ChatClient> chatClient, final Session session,
      final StateManager stateManager, final Provider<SignIn> signIn, final I18nTranslationService i18n) {
    super(AccessRolDTO.Viewer, false);
    this.chatClient = chatClient;
    this.session = session;
    this.stateManager = stateManager;
    this.signIn = signIn;
    this.i18n = i18n;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    if (session.isLogged()) {
      final ChatClient chat = chatClient.get();
      final Object target = event.getTarget();
      final String roomName = (target instanceof AbstractContentSimpleDTO) ? ((AbstractContentSimpleDTO) target).getName()
          : ((StateContainerDTO) session.getCurrentState()).getTitle();
      chat.joinRoom(roomName, session.getCurrentUserInfo().getShortName());
      chat.show();
    } else {
      signIn.get().setErrorMessage(i18n.t("Sign in or create an account to access to this room"),
          NotifyLevel.info);
      stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGN_IN,
          session.getCurrentStateToken().toString()));
    }
  }
}
