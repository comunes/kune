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
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class AddAsBuddyAction.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AddAsBuddyAction extends AbstractExtendedAction {
  
  /** The chat engine. */
  private final ChatClient chatEngine;
  
  /** The session. */
  private final Session session;
  
  /** The sn service. */
  private final Provider<SocialNetServiceAsync> snService;

  /**
   * Instantiates a new adds the as buddy action.
   *
   * @param chatEngine the chat engine
   * @param stateManager the state manager
   * @param i18n the i18n
   * @param img the img
   * @param snService the sn service
   * @param session the session
   */
  @Inject
  public AddAsBuddyAction(final ChatClient chatEngine, final StateManager stateManager,
      final I18nTranslationService i18n, final IconicResources img,
      final Provider<SocialNetServiceAsync> snService, final Session session) {
    super();
    this.chatEngine = chatEngine;
    this.snService = snService;
    this.session = session;
    putValue(Action.NAME, i18n.t(CoreMessages.ADD_AS_A_BUDDY));
    putValue(Action.SMALL_ICON, img.add());
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common.client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    String username = null;
    if (event.getTarget() instanceof GroupDTO) {
      username = ((GroupDTO) event.getTarget()).getShortName();
    } else if (event.getTarget() instanceof UserSimpleDTO) {
      username = ((UserSimpleDTO) event.getTarget()).getShortName();
    }
    if (username != null) {
      chatEngine.addNewBuddy(username);
      setEnabled(false);
      snService.get().addAsBuddie(session.getUserHash(), username, new AsyncCallbackSimple<Void>() {
        @Override
        public void onSuccess(final Void result) {
        }
      });
    }
  }
}
