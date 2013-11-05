/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class NewContentAction extends RolAction {

  public static final String ID = "ctnnewid";
  public static final String NEW_NAME = "ctnnewname";

  private final Provider<ContentServiceAsync> contentService;
  private final ContentViewerPresenter contentViewer;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public NewContentAction(final Session session, final StateManager stateManager,
      final Provider<ContentServiceAsync> contentService,
      final ContentViewerPresenter contentViewerPresenter) {
    super(AccessRolDTO.Editor, true);
    this.session = session;
    this.stateManager = stateManager;
    this.contentService = contentService;
    this.contentViewer = contentViewerPresenter;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    NotifyUser.showProgress();
    // stateManager.gotoStateToken(((HasContent)
    // session.getCurrentState()).getContainer().getStateToken());
    final String newName = (String) getValue(NEW_NAME);
    final StateToken parentToken = session.getCurrentStateToken().copy().clearDocument();
    contentService.get().addContent(session.getUserHash(), parentToken, newName, (String) getValue(ID),
        new AsyncCallbackSimple<StateContentDTO>() {
          @Override
          public void onSuccess(final StateContentDTO state) {
            stateManager.removeCache(parentToken);
            stateManager.setRetrievedStateAndGo(state);
            NotifyUser.hideProgress();
            // stateManager.refreshCurrentGroupState();
            // contextNavigator.setEditOnNextStateChange(true);
            // NotifyUser.info(i18n.tWithNT("[%s] created",
            // "New content created, for instance", newName));
            contentViewer.blinkTitle();
          }
        });
  }
}
