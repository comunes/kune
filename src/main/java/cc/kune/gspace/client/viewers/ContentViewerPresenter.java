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

import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.ui.EditEvent;
import cc.kune.common.client.ui.EditEvent.EditHandler;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.RenameAction;
import cc.kune.gspace.client.actions.RenameListener;
import cc.kune.gspace.client.tool.ContentViewer;
import cc.kune.wave.client.kspecific.WaveClientManager;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentViewerPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContentViewerPresenter extends
    Presenter<ContentViewerPresenter.ContentViewerView, ContentViewerPresenter.ContentViewerProxy>
    implements ContentViewer {

  /**
   * The Interface ContentViewerProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface ContentViewerProxy extends Proxy<ContentViewerPresenter> {
  }

  /**
   * The Interface ContentViewerView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ContentViewerView extends View {

    /**
     * Attach.
     */
    void attach();

    /**
     * Blink title.
     */
    void blinkTitle();

    /**
     * Clear.
     */
    void clear();

    /**
     * Detach.
     */
    void detach();

    /**
     * Gets the edits the title.
     * 
     * @return the edits the title
     */
    HasEditHandler getEditTitle();

    /**
     * Sets the content.
     * 
     * @param state
     *          the new content
     */
    void setContent(StateContentDTO state);

    /**
     * Sets the editable content.
     * 
     * @param state
     *          the new editable content
     */
    void setEditableContent(StateContentDTO state);

    /**
     * Sets the editable title.
     * 
     * @param title
     *          the new editable title
     */
    void setEditableTitle(String title);

    /**
     * Sets the footer actions.
     * 
     * @param actions
     *          the new footer actions
     */
    void setFooterActions(GuiActionDescCollection actions);

    /**
     * Sets the subheader actions.
     * 
     * @param actions
     *          the new subheader actions
     */
    void setSubheaderActions(GuiActionDescCollection actions);

    /**
     * Sign in.
     */
    void signIn();

    /**
     * Sign out.
     */
    void signOut();

  }

  /** The actions registry. */
  private final ActionRegistryByType actionsRegistry;

  /** The edit handler. */
  private HandlerRegistration editHandler;

  /** The path toolbar utils. */
  private final PathToolbarUtils pathToolbarUtils;

  /** The rename action. */
  private final Provider<RenameAction> renameAction;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new content viewer presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param stateManager
   *          the state manager
   * @param proxy
   *          the proxy
   * @param session
   *          the session
   * @param actionsRegistry
   *          the actions registry
   * @param renameAction
   *          the rename action
   * @param pathToolbarUtils
   *          the path toolbar utils
   * @param wavClientManager
   *          the wav client manager
   */
  @Inject
  public ContentViewerPresenter(final EventBus eventBus, final ContentViewerView view,
      final StateManager stateManager, final ContentViewerProxy proxy, final Session session,
      final ActionRegistryByType actionsRegistry, final Provider<RenameAction> renameAction,
      final PathToolbarUtils pathToolbarUtils, final WaveClientManager wavClientManager) {
    super(eventBus, view, proxy);
    this.session = session;
    this.actionsRegistry = actionsRegistry;
    this.renameAction = renameAction;
    this.pathToolbarUtils = pathToolbarUtils;
    session.onUserSignOut(true, new UserSignOutHandler() {
      @Override
      public void onUserSignOut(final UserSignOutEvent event) {
        getView().signOut();
      }
    });
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        getView().signIn();
      }
    });
    stateManager.addBeforeStateChangeListener(new BeforeActionListener() {
      @Override
      public boolean beforeAction() {
        getView().detach();
        return true;
      }
    });
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
   * Blink title.
   */
  public void blinkTitle() {
    getView().blinkTitle();
  }

  /**
   * Creates the edit handler.
   */
  private void createEditHandler() {
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
    getView().clear();
    final StateContentDTO stateContent = (StateContentDTO) state;
    final AccessRights rights = stateContent.getContentRights();
    Log.info("Content rights: " + rights);
    if (session.isLogged() && rights.isEditable()) {
      if (stateContent.isParticipant()) {
        // is already participant, show wave editor
        final org.waveprotocol.box.webclient.client.Session waveSession = org.waveprotocol.box.webclient.client.Session.get();
        if (waveSession != null && waveSession.isLoggedIn()) {
          getView().setEditableContent(stateContent);
        } else {
          getView().setContent(stateContent);
          // When logged setEditable!
        }
      } else {
        // add "participate" action
        getView().setContent(stateContent);
      }
    } else {
      if (rights.isVisible()) {
        // Show contents
        getView().setContent(stateContent);
      } else {
        throw new UIException("Unexpected status in Viewer");
      }
    }
    final GuiActionDescCollection topActions = actionsRegistry.getCurrentActions(
        stateContent.getToolName(), stateContent.getGroup(), stateContent.getTypeId(),
        session.isLogged(), rights, ActionGroups.TOPBAR);
    final GuiActionDescCollection bottomActions = actionsRegistry.getCurrentActions(
        stateContent.getToolName(), stateContent.getGroup(), stateContent.getTypeId(),
        session.isLogged(), rights, ActionGroups.BOTTOMBAR);
    final GuiActionDescCollection pathActions = pathToolbarUtils.createPath(stateContent.getGroup(),
        stateContent.getContainer(), true, false);
    bottomActions.addAll(pathActions);
    getView().setSubheaderActions(topActions);
    getView().setFooterActions(bottomActions);
  }
}
