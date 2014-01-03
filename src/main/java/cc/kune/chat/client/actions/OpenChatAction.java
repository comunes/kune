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

// TODO: Auto-generated Javadoc
/**
 * The Class OpenChatAction.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class OpenChatAction extends RolAction {

  /** The chat client. */
  private final Provider<ChatClient> chatClient;
  
  /** The i18n. */
  private final I18nTranslationService i18n;
  
  /** The session. */
  private final Session session;
  
  /** The sign in. */
  private final Provider<SignIn> signIn;
  
  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new open chat action.
   *
   * @param chatClient the chat client
   * @param session the session
   * @param stateManager the state manager
   * @param signIn the sign in
   * @param i18n the i18n
   */
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

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common.client.actions.ActionEvent)
   */
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
