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
package cc.kune.core.client.dnd;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.gspace.client.viewers.items.FolderItemWidget;

import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * The Class FolderContainerDropController is responsable of the drop process to
 * folders. Must not be a @singleton, and should exist one drop controller per
 * item
 */
public class FolderContainerDropController extends AbstractDropController {

  private final ContentCache contentCache;
  private final ContentServiceAsync contentService;
  private final ErrorHandler erroHandler;
  private final I18nTranslationService i18n;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public FolderContainerDropController(final KuneDragController dragController,
      final ContentServiceAsync contentService, final Session session, final StateManager stateManager,
      final ErrorHandler erroHandler, final I18nTranslationService i18n, final ContentCache contentCache) {
    super(dragController);
    registerType(FolderItemWidget.class);
    this.contentService = contentService;
    this.session = session;
    this.stateManager = stateManager;
    this.erroHandler = erroHandler;
    this.i18n = i18n;
    this.contentCache = contentCache;
  }

  @Override
  public void onDropAllowed(final Widget widget, final SimpleDropController dropController) {
    dropController.getDropTarget().removeStyleName("k-drop-allowed-hover");
    if (widget instanceof FolderItemWidget) {
      NotifyUser.showProgress(i18n.t("Moving"));
      if (getTarget() != null) {
        final StateToken destToken = (StateToken) getTarget();
        widget.removeFromParent();
        final StateToken tokenToMove = ((FolderItemWidget) widget).getToken();
        contentService.moveContent(session.getUserHash(), tokenToMove, destToken,
            new AsyncCallback<StateContainerDTO>() {
              @Override
              public void onFailure(final Throwable caught) {
                erroHandler.process(caught);
                stateManager.refreshCurrentState();
                NotifyUser.hideProgress();
              }

              @Override
              public void onSuccess(final StateContainerDTO result) {
                NotifyUser.hideProgress();
                contentCache.remove(tokenToMove);
                contentCache.remove(destToken);
              }
            });
      } else {
        NotifyUser.info(i18n.t(TextUtils.IN_DEVELOPMENT));
        NotifyUser.hideProgress();
      }
    }
  }

}
