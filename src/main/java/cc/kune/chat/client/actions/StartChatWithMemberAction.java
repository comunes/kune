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
import cc.kune.chat.client.resources.ChatResources;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class StartChatWithMemberAction.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StartChatWithMemberAction extends AbstractExtendedAction {
  
  /** The chat client. */
  private final Provider<ChatClient> chatClient;
  
  /** The i18n. */
  private final I18nTranslationService i18n;

  /**
   * Instantiates a new start chat with member action.
   *
   * @param i18n the i18n
   * @param res the res
   * @param chatClient the chat client
   */
  @Inject
  public StartChatWithMemberAction(final I18nTranslationService i18n, final ChatResources res,
      final Provider<ChatClient> chatClient) {
    this.i18n = i18n;
    this.chatClient = chatClient;
    putValue(NAME, i18n.t("Chat with this group member"));
    putValue(Action.SMALL_ICON, res.chat());
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common.client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    chatClient.get().show();
    if (chatClient.get().isXmppLoggedIn()) {
      String username = null;
      if (event.getTarget() instanceof GroupDTO) {
        username = ((GroupDTO) event.getTarget()).getShortName();
      } else if (event.getTarget() instanceof UserSimpleDTO) {
        username = ((UserSimpleDTO) event.getTarget()).getShortName();
      }
      if (username != null) {
        chatClient.get().chat(username);
      }
    } else {
      NotifyUser.important(i18n.t("To start a chat you need to be 'online'"));
    }
  }
}
