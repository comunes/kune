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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class NewContainerBtn extends ButtonDescriptor {

  public static class NewContainerAction extends RolAction {

    private final ContentCache cache;
    private final Provider<ContentServiceAsync> contentService;
    private final FolderViewerPresenter folderViewer;
    private final I18nTranslationService i18n;
    private final Session session;
    private final StateManager stateManager;

    @Inject
    public NewContainerAction(final Session session, final StateManager stateManager,
        final I18nTranslationService i18n, final Provider<ContentServiceAsync> contentService,
        final ContentCache cache, final FolderViewerPresenter folderViewer) {
      super(AccessRolDTO.Editor, true);
      this.session = session;
      this.stateManager = stateManager;
      this.i18n = i18n;
      this.contentService = contentService;
      this.cache = cache;
      this.folderViewer = folderViewer;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      NotifyUser.showProgressProcessing();
      stateManager.gotoStateToken(((HasContent) session.getCurrentState()).getContainer().getStateToken());
      final String newName = (String) getValue(NEW_NAME);
      contentService.get().addFolder(session.getUserHash(), session.getCurrentStateToken(), newName,
          (String) getValue(ID), new AsyncCallbackSimple<StateContainerDTO>() {
            @Override
            public void onSuccess(final StateContainerDTO state) {
              // contextNavigator.setEditOnNextStateChange(true);
              stateManager.setRetrievedStateAndGo(state);
              NotifyUser.hideProgress();
              NotifyUser.info(i18n.tWithNT("[%s] created", "New folder created, for instance", newName));
              // folderViewer.editTitle();
            }
          });
      cache.removeContent(session.getCurrentStateToken());
    }

  }

  private static final String ID = "ctnernewid";
  private static final String NEW_NAME = "ctnernewname";

  public NewContainerBtn(final I18nTranslationService i18n, final NewContainerAction action,
      final ImageResource icon, final String title, final String tooltip, final String newName,
      final String id) {
    super(action);
    // The name given to this new content
    action.putValue(NEW_NAME, newName);
    action.putValue(ID, id);
    this.withText(title).withToolTip(tooltip).withIcon(icon).withStyles("k-def-docbtn, k-fr");
  }
}
