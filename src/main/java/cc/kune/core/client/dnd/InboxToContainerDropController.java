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
package cc.kune.core.client.dnd;

import org.waveprotocol.box.webclient.search.CustomDigestDomImpl;

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

import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class is responsible of the drop process of waves to folders. Should be
 * a singleton and change the target on every statechange
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class InboxToContainerDropController extends AbstractDropController {

  private final ContentCache contentCache;
  private final ContentServiceAsync contentService;
  private final ErrorHandler erroHandler;
  private final I18nTranslationService i18n;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public InboxToContainerDropController(final KuneDragController dragController,
      final ContentServiceAsync contentService, final Session session, final StateManager stateManager,
      final ErrorHandler erroHandler, final I18nTranslationService i18n, final ContentCache contentCache) {
    super(dragController);
    registerType(CustomDigestDomImpl.class);
    this.contentService = contentService;
    this.session = session;
    this.stateManager = stateManager;
    this.erroHandler = erroHandler;
    this.i18n = i18n;
    this.contentCache = contentCache;
  }

  /**
   * Move.
   *
   * @param widget
   *          the widget
   * @param destToken
   *          the dest token
   */
  private void move(final Widget widget, final StateToken destToken) {
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
  }

  /**
   * Not implemented.
   */
  private void notImplemented() {
    NotifyUser.info(i18n.t(TextUtils.IN_DEVELOPMENT));
    NotifyUser.hideProgress();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.dnd.AbstractDropController#onDropAllowed(com.google
   * .gwt.user.client.ui.Widget,
   * com.allen_sauer.gwt.dnd.client.drop.SimpleDropController)
   */
  @Override
  public void onDropAllowed(final Widget widget, final SimpleDropController dropController) {
    dropController.getDropTarget().removeStyleName("k-drop-allowed-hover");
    if (widget instanceof CustomDigestDomImpl) {
      notImplemented();
    }
  }

  @Override
  public void onPreviewAllowed(final Widget widget, final SimpleDropController dropController)
      throws VetoDragException {
    if (session.isCurrentStateAContent()) {
      throw new VetoDragException();
    }
    super.onPreviewAllowed(widget, dropController);
  }
}
