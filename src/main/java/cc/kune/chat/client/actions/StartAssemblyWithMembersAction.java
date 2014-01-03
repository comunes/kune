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

import java.util.Date;

import cc.kune.chat.client.ChatClient;
import cc.kune.chat.client.resources.ChatResources;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class StartAssemblyWithMembersAction.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StartAssemblyWithMembersAction extends OpenGroupPublicChatRoomAction {

  /** The content service. */
  private final Provider<ContentServiceAsync> contentService;

  /**
   * Instantiates a new start assembly with members action.
   *
   * @param session the session
   * @param accessRightsClientManager the access rights client manager
   * @param chatClient the chat client
   * @param stateManager the state manager
   * @param i18n the i18n
   * @param res the res
   * @param contentService the content service
   */
  @Inject
  public StartAssemblyWithMembersAction(final Session session,
      final AccessRightsClientManager accessRightsClientManager, final ChatClient chatClient,
      final StateManager stateManager, final I18nTranslationService i18n, final ChatResources res,
      final Provider<ContentServiceAsync> contentService) {
    super(session, accessRightsClientManager, chatClient, stateManager, i18n, res);
    this.contentService = contentService;
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.actions.OpenGroupPublicChatRoomAction#actionPerformed(cc.kune.common.client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    NotifyUser.askConfirmation(i18n.t("Please confirm"),
        i18n.t("Do you want to collaboratively edit your meeting minutes in a new document?"),
        new SimpleResponseCallback() {
          @Override
          public void onCancel() {
          }

          @Override
          public void onSuccess() {
            contentService.get().writeTo(
                session.getUserHash(),
                session.getCurrentStateToken(),
                false,
                i18n.t("Meeting minutes of [%s] on [%s]", session.getCurrentGroupShortName(),
                    DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG).format(new Date())),
                i18n.t("You can collaboratively edit the meeting minutes, shown in this document."),
                new AsyncCallbackSimple<String>() {
                  @Override
                  public void onSuccess(final String url) {
                    stateManager.gotoHistoryToken(url);
                    NotifyUser.info("Now you can edit this message");
                  }
                });
          }
        });
    super.actionPerformed(event);
  }

}
