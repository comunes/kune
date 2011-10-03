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
package cc.kune.chat.client.actions;

import cc.kune.chat.client.ChatClient;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.SimpleResponseCallback;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ChatAboutContentBtn extends ButtonDescriptor {

  public static class ChatAboutContentAction extends RolAction {

    private final Provider<ChatClient> chatClient;
    private final I18nTranslationService i18n;
    private final Session session;

    @Inject
    public ChatAboutContentAction(final Session session, final I18nTranslationService i18n,
        final Provider<ChatClient> chatClient) {
      super(AccessRolDTO.Viewer, true);
      this.session = session;
      this.i18n = i18n;
      this.chatClient = chatClient;
    }

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

  public static final String ID = "k-chatabout-btn";

  @Inject
  public ChatAboutContentBtn(final ChatAboutContentAction action, final CoreResources res,
      final I18nTranslationService i18n) {
    super(action);
    this.withIcon(res.emiteRoom()).withToolTip(i18n.t("Chat and comment on this")).withText(
        i18n.t("Chat about")).withStyles("k-def-docbtn, k-fl").withId(ID);
  }
}
