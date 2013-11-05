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
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.inject.Inject;

public class CopyContentMenuItem extends MenuItemDescriptor {

  public static class CopyContentAction extends RolAction {

    private final ContentServiceAsync contentService;
    private final I18nTranslationService i18n;
    private final Session session;
    private final StateManager stateManager;

    @Inject
    public CopyContentAction(final Session session, final StateManager stateManager,
        final ContentServiceAsync contentService, final I18nTranslationService i18n) {
      super(AccessRolDTO.Editor, true);
      this.session = session;
      this.stateManager = stateManager;
      this.contentService = contentService;
      this.i18n = i18n;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      NotifyUser.showProgress(i18n.t("Copying"));
      final StateToken token = ActionUtils.getActionToken(event);
      final boolean inContent = session.getCurrentStateToken().hasAll();
      final StateToken parentToken = inContent ? session.getContentState().getContainer().getStateToken()
          : session.getCurrentStateToken();
      contentService.copyContent(session.getUserHash(), parentToken, token,
          new AsyncCallbackSimple<StateContentDTO>() {
            @Override
            public void onSuccess(final StateContentDTO result) {
              NotifyUser.hideProgress();
              stateManager.setRetrievedStateAndGo(result);
            }
          });
    }

  }

  @Inject
  public CopyContentMenuItem(final I18nTranslationService i18n, final CopyContentAction action,
      final ContentViewerOptionsMenu optionsMenu, final CommonResources res) {
    super(action);
    this.withParent(optionsMenu, false);
    this.withIcon(res.clear16());
    this.withText(i18n.t("Copy this to a new message"));
  }
}
