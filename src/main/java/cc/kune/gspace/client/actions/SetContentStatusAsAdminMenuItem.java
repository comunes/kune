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
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.ConfirmAskEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class SetContentStatusAsAdminMenuItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SetContentStatusAsAdminMenuItem extends MenuItemDescriptor {

  /**
   * The Class SetContentStatusAsAdminAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class SetContentStatusAsAdminAction extends RolAction {

    /** The content service. */
    private final Provider<ContentServiceAsync> contentService;

    /** The event bus. */
    private final EventBus eventBus;

    /** The i18n. */
    private final I18nTranslationService i18n;

    /** The presenter. */
    private final Provider<FolderViewerPresenter> presenter;

    /** The session. */
    private final Session session;

    /** The state manager. */
    private final StateManager stateManager;

    /**
     * Instantiates a new sets the content status as admin action.
     * 
     * @param eventBus
     *          the event bus
     * @param stateManager
     *          the state manager
     * @param session
     *          the session
     * @param contentService
     *          the content service
     * @param i18n
     *          the i18n
     * @param presenter
     *          the presenter
     */
    @Inject
    public SetContentStatusAsAdminAction(final EventBus eventBus, final StateManager stateManager,
        final Session session, final Provider<ContentServiceAsync> contentService,
        final I18nTranslationService i18n, final Provider<FolderViewerPresenter> presenter) {
      super(AccessRolDTO.Administrator, true);
      this.eventBus = eventBus;
      this.stateManager = stateManager;
      this.session = session;
      this.contentService = contentService;
      this.i18n = i18n;
      this.presenter = presenter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
     * common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      final Boolean confirm = (Boolean) getValue(CONFIRM);
      if (confirm) {
        ConfirmAskEvent.fire(eventBus, i18n.t("Please confirm"), i18n.t("Are you sure?"), i18n.t("Yes"),
            i18n.t("No"), null, null, new OnAcceptCallback() {
              @Override
              public void onSuccess() {
                doAction(event);
              }
            });
      } else {
        doAction(event);
      }
    }

    /**
     * Do action.
     * 
     * @param event
     *          the event
     */
    private void doAction(final ActionEvent event) {
      NotifyUser.showProgress();
      final ContentStatus status = (ContentStatus) getValue(STATUS);
      final StateToken token = ((AbstractContentSimpleDTO) event.getTarget()).getStateToken();
      contentService.get().setStatusAsAdmin(session.getUserHash(), token, status,
          new AsyncCallbackSimple<StateAbstractDTO>() {
            @Override
            public void onSuccess(final StateAbstractDTO state) {
              // Is this necessary?
              // session.setCurrentState(state);
              final StateToken parent = token.copy().clearDocument();
              if (session.getCurrentStateToken().equals(parent)) {
                stateManager.refreshCurrentStateWithoutCache();
                // Warning: the previous action
                // is asynchronous (it gets a
                // content)
                presenter.get().refreshState();
              } else {
                stateManager.gotoStateToken(parent, false);
              }
              NotifyUser.hideProgress();
            }
          });
    }

  }

  /** The Constant CONFIRM. */
  private static final String CONFIRM = "setctnconfirm";

  /** The Constant STATUS. */
  private static final String STATUS = "setctnstatus";

  /**
   * Instantiates a new sets the content status as admin menu item.
   * 
   * @param action
   *          the action
   * @param status
   *          the status
   * @param confirm
   *          the confirm
   */
  public SetContentStatusAsAdminMenuItem(final SetContentStatusAsAdminAction action,
      final ContentStatus status, final Boolean confirm) {
    super(action);
    action.putValue(CONFIRM, confirm);
    action.putValue(STATUS, status);
  }

}
