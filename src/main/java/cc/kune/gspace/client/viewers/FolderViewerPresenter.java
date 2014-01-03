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

// TODO: Auto-generated Javadoc
/**
 * The Class FolderViewerPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FolderViewerPresenter extends
    Presenter<FolderViewerPresenter.FolderViewerView, FolderViewerPresenter.FolderViewerProxy> implements
    ContentViewer {

  /**
   * The Interface FolderViewerProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface FolderViewerProxy extends Proxy<FolderViewerPresenter> {
  }

  /**
   * The Interface FolderViewerView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface FolderViewerView extends AbstractFolderViewerView {

    /** The no date. */
    long NO_DATE = 0;
  }

  /** The edit handler. */
  private HandlerRegistration editHandler;

  /** The folder viewer utils. */
  private final FolderViewerUtils folderViewerUtils;

  /** The rename action. */
  private final Provider<RenameAction> renameAction;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new folder viewer presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param session
   *          the session
   * @param renameAction
   *          the rename action
   * @param folderViewerUtils
   *          the folder viewer utils
   */
  @Inject
  public FolderViewerPresenter(final EventBus eventBus, final FolderViewerView view,
      final FolderViewerProxy proxy, final Session session, final Provider<RenameAction> renameAction,
      final FolderViewerUtils folderViewerUtils) {
    super(eventBus, view, proxy);
    this.session = session;
    this.folderViewerUtils = folderViewerUtils;
    this.renameAction = renameAction;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.ContentViewer#attach()
   */
  @Override
  public void attach() {
    getView().attach();
    if (editHandler == null) {
      createEditHandler();
    }
  }

  /**
   * Creates the edit handler.
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.ContentViewer#detach()
   */
  @Override
  public void detach() {
    getView().detach();
  }

  /**
   * Edits the title.
   */
  public void editTitle() {
    getView().editTitle();
  }

  /**
   * Highlight title.
   */
  public void highlightTitle() {
    getView().highlightTitle();
  }

  /**
   * Refresh state.
   */
  public void refreshState() {
    setContent((HasContent) session.getCurrentState());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tool.ContentViewer#setContent(cc.kune.core.shared
   * .dto.HasContent)
   */
  @Override
  public void setContent(@Nonnull final HasContent state) {
    folderViewerUtils.setContent(getView(), state);
  }
}
