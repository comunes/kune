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
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class ChatAboutContentBtn.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ChatAboutContentBtn extends MenuItemDescriptor {

  /**
   * The Class ChatAboutContentAction.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class ChatAboutContentAction extends RolAction {

    /** The chat client. */
    private final Provider<ChatClient> chatClient;
    
    /** The i18n. */
    private final I18nTranslationService i18n;
    
    /** The session. */
    private final Session session;

    /**
     * Instantiates a new chat about content action.
     *
     * @param session the session
     * @param i18n the i18n
     * @param chatClient the chat client
     */
    @Inject
    public ChatAboutContentAction(final Session session, final I18nTranslationService i18n,
        final Provider<ChatClient> chatClient) {
      super(AccessRolDTO.Viewer, true);
      this.session = session;
      this.i18n = i18n;
      this.chatClient = chatClient;
    }

    /* (non-Javadoc)
     * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      NotifyUser.askConfirmation(
          i18n.t("Confirm, please:"),
          i18n.t("This will open a specific chatroom to chat about this page or document (it's useful to chat with others about something while reading/modifing it). Are you sure?"),
          new SimpleResponseCallback() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onSuccess() {
              final String subject = i18n.t("Chat about: [%s]", session.getContentState().getTitle());
              final StateToken token = session.getCurrentStateToken();
              chatClient.get().joinRoom(token.toString().replaceAll("\\.", "-"), subject,
                  session.getCurrentUserInfo().getShortName());
              chatClient.get().show();
            }
          });
    }

  }

  /** The Constant ID. */
  public static final String ID = "k-chatabout-btn";

  /**
   * Instantiates a new chat about content btn.
   *
   * @param action the action
   * @param res the res
   * @param optionsMenu the options menu
   * @param i18n the i18n
   */
  @Inject
  public ChatAboutContentBtn(final ChatAboutContentAction action, final ChatResources res,
      final ContentViewerOptionsMenu optionsMenu, final I18nTranslationService i18n) {
    super(action);
    this.withIcon(res.chat()).withToolTip(i18n.t("Chat and comment on this")).withText(
        i18n.t("Chat about")).withStyles("k-def-docbtn, k-fl").withId(ID).withParent(optionsMenu, false);
  }
}
