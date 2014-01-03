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

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.errors.NameInUseException;
import cc.kune.core.client.errors.NameNotPermittedException;
import cc.kune.core.client.events.RenameContentEvent;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class RenameAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class RenameAction {

  /** The content cache. */
  private final ContentCache contentCache;

  /** The content service. */
  private final Provider<ContentServiceAsync> contentService;

  /** The error handler. */
  private final ErrorHandler errorHandler;

  /** The event bus. */
  private final EventBus eventBus;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new rename action.
   * 
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   * @param eventBus
   *          the event bus
   * @param contentService
   *          the content service
   * @param errorHandler
   *          the error handler
   * @param contentCache
   *          the content cache
   */
  @Inject
  public RenameAction(final I18nTranslationService i18n, final Session session, final EventBus eventBus,
      final Provider<ContentServiceAsync> contentService, final ErrorHandler errorHandler,
      final ContentCache contentCache) {
    this.i18n = i18n;
    this.session = session;
    this.eventBus = eventBus;
    this.contentService = contentService;
    this.errorHandler = errorHandler;
    this.contentCache = contentCache;
  }

  /**
   * Rename.
   * 
   * @param token
   *          the token
   * @param oldName
   *          the old name
   * @param newName
   *          the new name
   * @param listener
   *          the listener
   */
  public void rename(final StateToken token, final String oldName, final String newName,
      final RenameListener listener) {
    if (!newName.equals(oldName)) {
      NotifyUser.showProgress(i18n.t("Renaming"));
      final AsyncCallback<StateAbstractDTO> asyncCallback = new AsyncCallback<StateAbstractDTO>() {
        @Override
        public void onFailure(final Throwable caught) {
          NotifyUser.hideProgress();
          if (caught instanceof NameInUseException) {
            NotifyUser.error(i18n.tWithNT("This name already exists",
                "It is used when a file or a folder with the same name already exists"));
          } else if (caught instanceof NameNotPermittedException) {
            NotifyUser.error(i18n.tWithNT("This name is not permitted",
                "It is used when a file or a folder does not have a permitted name"));
          } else {
            errorHandler.process(caught);
            NotifyUser.error(i18n.t("Error renaming"));
          }
          listener.onFail(token, oldName);
        }

        @Override
        public void onSuccess(final StateAbstractDTO state) {
          NotifyUser.hideProgress();
          session.setCurrentState(state);
          listener.onSuccess(token, state.getTitle());
          RenameContentEvent.fire(eventBus, token, oldName, newName);
          contentCache.remove(token);
          // remove parent cache
          if (state instanceof StateContentDTO) {
            contentCache.remove(((StateContentDTO) state).getContainer().getStateToken());
          } else if (state instanceof StateContainerDTO) {
            final ContainerDTO container = ((StateContainerDTO) state).getContainer();
            contentCache.remove(container.getParentToken());
          }
        }
      };
      if (token.isComplete()) {
        contentService.get().renameContent(session.getUserHash(), token, newName, asyncCallback);
      } else {
        contentService.get().renameContainer(session.getUserHash(), token, newName, asyncCallback);
      }
    }
  }
}
