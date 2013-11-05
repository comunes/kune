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
package cc.kune.gspace.client.viewers;

import javax.annotation.Nonnull;

import cc.kune.common.client.ui.EditEvent;
import cc.kune.common.client.ui.EditEvent.EditHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.gspace.client.actions.RenameAction;
import cc.kune.gspace.client.actions.RenameListener;
import cc.kune.gspace.client.tool.ContentViewer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class FolderViewerPresenter extends
    Presenter<FolderViewerPresenter.FolderViewerView, FolderViewerPresenter.FolderViewerProxy> implements
    ContentViewer {

  @ProxyCodeSplit
  public interface FolderViewerProxy extends Proxy<FolderViewerPresenter> {
  }

  public interface FolderViewerView extends AbstractFolderViewerView {
    long NO_DATE = 0;
  }

  private HandlerRegistration editHandler;
  private final FolderViewerUtils folderViewerUtils;
  private final Provider<RenameAction> renameAction;
  private final Session session;

  @Inject
  public FolderViewerPresenter(final EventBus eventBus, final FolderViewerView view,
      final FolderViewerProxy proxy, final Session session, final Provider<RenameAction> renameAction,
      final FolderViewerUtils folderViewerUtils) {
    super(eventBus, view, proxy);
    this.session = session;
    this.folderViewerUtils = folderViewerUtils;
    this.renameAction = renameAction;
  }

  @Override
  public void attach() {
    getView().attach();
    if (editHandler == null) {
      createEditHandler();
    }
  }

  private void createEditHandler() {
    // Duplicate in DocViewerPresenter
    editHandler = getView().getEditTitle().addEditHandler(new EditHandler() {
      @Override
      public void fire(final EditEvent event) {
        renameAction.get().rename(session.getCurrentStateToken(), session.getCurrentState().getTitle(),
            event.getText(), new RenameListener() {
              @Override
              public void onFail(final StateToken token, final String oldTitle) {
                getView().setEditableTitle(oldTitle);
              }

              @Override
              public void onSuccess(final StateToken token, final String title) {
                getView().setEditableTitle(title);
              }
            });
      }
    });
  }

  @Override
  public void detach() {
    getView().detach();
  }

  public void editTitle() {
    getView().editTitle();
  }

  public void highlightTitle() {
    getView().highlightTitle();
  }

  public void refreshState() {
    setContent((HasContent) session.getCurrentState());
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  @Override
  public void setContent(@Nonnull final HasContent state) {
    folderViewerUtils.setContent(getView(), state);
  }
}
