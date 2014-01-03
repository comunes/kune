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
package cc.kune.core.client.actions.xml;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;

import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class NewGadgetAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NewGadgetAction extends RolAction {

  /** The body. */
  private final String body;

  /** The content service. */
  private final Provider<ContentServiceAsync> contentService;

  /** The content viewer. */
  private final ContentViewerPresenter contentViewer;

  /** The gadget name. */
  private final String gadgetName;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The session. */
  private final Session session;

  /** The state manager. */
  private final StateManager stateManager;

  /** The title. */
  private final String title;

  /** The type id. */
  private final String typeId;

  /**
   * Instantiates a new new gadget action.
   * 
   * @param contentService
   *          the content service
   * @param contentViewerPresenter
   *          the content viewer presenter
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   * @param rol
   *          the rol
   * @param authNeeded
   *          the auth needed
   * @param gadgetName
   *          the gadget name
   * @param typeId
   *          the type id
   * @param iconUrl
   *          the icon url
   * @param title
   *          the title
   * @param body
   *          the body
   */
  public NewGadgetAction(final Provider<ContentServiceAsync> contentService,
      final ContentViewerPresenter contentViewerPresenter, final StateManager stateManager,
      final Session session, final I18nTranslationService i18n, final AccessRolDTO rol,
      final boolean authNeeded, final String gadgetName, final String typeId, final String iconUrl,
      final String title, final String body) {
    super(rol, authNeeded);
    this.contentService = contentService;
    this.contentViewer = contentViewerPresenter;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.gadgetName = gadgetName;
    this.session = session;
    this.typeId = typeId;
    this.title = title;
    this.body = body;
    putValue(Action.SMALL_ICON, iconUrl);
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
    final StateToken parentToken = session.getCurrentStateToken();
    contentService.get().addNewContentWithGadget(session.getUserHash(), parentToken, gadgetName, typeId,
        i18n.t(title), i18n.t(body), new AsyncCallbackSimple<StateContentDTO>() {
          @Override
          public void onSuccess(final StateContentDTO result) {
            NotifyUser.info(i18n.t("'[%s]' created succesfully", i18n.t(title)),
                i18n.t(CoreMessages.GADGETS_EXPERIMENTAL));
            stateManager.removeCache(parentToken);
            stateManager.setRetrievedStateAndGo(result);
            contentViewer.blinkTitle();
          }
        });
  }

}
