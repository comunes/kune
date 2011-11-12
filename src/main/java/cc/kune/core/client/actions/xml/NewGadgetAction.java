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
package cc.kune.core.client.actions.xml;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;

import com.google.inject.Provider;

public class NewGadgetAction extends RolAction {

  private final String body;
  private final Provider<ContentServiceAsync> contentService;
  private final ContentViewerPresenter contentViewer;
  private final String gadgetName;
  private final I18nTranslationService i18n;
  private final Session session;
  private final StateManager stateManager;
  private final String title;
  private final String typeId;

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

  @Override
  public void actionPerformed(final ActionEvent event) {
    contentService.get().addNewContentWithGadget(session.getUserHash(), session.getCurrentStateToken(),
        gadgetName, typeId, i18n.t(title), i18n.t(body), new AsyncCallbackSimple<StateContentDTO>() {
          @Override
          public void onSuccess(final StateContentDTO result) {
            NotifyUser.info(i18n.t("'[%s]' created succesfully", title),
                i18n.t(CoreMessages.GADGETS_EXPERIMENTAL));
            stateManager.setRetrievedStateAndGo(result);
            contentViewer.blinkTitle();
          }
        });
  }

}