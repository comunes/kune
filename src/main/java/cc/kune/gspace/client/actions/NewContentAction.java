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

// TODO: Auto-generated Javadoc
/**
 * The Class NewContentAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NewContentAction extends RolAction {

  /** The Constant ID. */
  public static final String ID = "ctnnewid";

  /** The Constant NEW_NAME. */
  public static final String NEW_NAME = "ctnnewname";

  /** The content service. */
  private final Provider<ContentServiceAsync> contentService;

  /** The content viewer. */
  private final ContentViewerPresenter contentViewer;

  /** The session. */
  private final Session session;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new new content action.
   * 
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param contentService
   *          the content service
   * @param contentViewerPresenter
   *          the content viewer presenter
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
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
