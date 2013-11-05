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
package cc.kune.core.client.rpcservices;

import cc.kune.common.client.notify.ConfirmAskEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentServiceHelper.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContentServiceHelper {

  /** The cache. */
  private final ContentCache cache;
  
  /** The content service. */
  private final Provider<ContentServiceAsync> contentService;
  
  /** The def callback. */
  private final AsyncCallbackSimple<StateContainerDTO> defCallback;
  
  /** The event bus. */
  private final EventBus eventBus;
  
  /** The folder viewer. */
  private final FolderViewerPresenter folderViewer;
  
  /** The i18n. */
  private final I18nTranslationService i18n;
  
  /** The session. */
  private final Session session;
  
  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new content service helper.
   *
   * @param session the session
   * @param i18n the i18n
   * @param eventBus the event bus
   * @param contentService the content service
   * @param cache the cache
   * @param folderViewer the folder viewer
   * @param stateManager the state manager
   */
  @Inject
  public ContentServiceHelper(final Session session, final I18nTranslationService i18n,
      final EventBus eventBus, final Provider<ContentServiceAsync> contentService,
      final ContentCache cache, final FolderViewerPresenter folderViewer, final StateManager stateManager) {
    this.cache = cache;
    this.folderViewer = folderViewer;
    this.session = session;
    this.i18n = i18n;
    this.eventBus = eventBus;
    this.contentService = contentService;
    this.stateManager = stateManager;
    defCallback = new AsyncCallbackSimple<StateContainerDTO>() {
      @Override
      public void onFailure(final Throwable caught) {
        // Should we do something with
        // ContainerNotEmptyException?
        super.onFailure(caught);
        NotifyUser.hideProgress();
      }

      @Override
      public void onSuccess(final StateContainerDTO state) {
        final StateToken parentToken = state.getStateToken();
        if (session.getCurrentStateToken().equals(parentToken)) {
          stateManager.setRetrievedStateAndGo(state);
        } else {
          stateManager.gotoStateToken(parentToken, false);
        }
        NotifyUser.hideProgress();
      }
    };

  }

  /**
   * Adds the container.
   *
   * @param id the id
   * @param newName the new name
   */
  public void addContainer(final String id, final String newName) {
    NotifyUser.showProgress();
    // stateManager.gotoStateToken(((HasContent)
    // session.getCurrentState()).getContainer().getStateToken());
    final StateToken parentToken = session.getCurrentStateToken();
    contentService.get().addFolder(session.getUserHash(), parentToken, newName, id,
        new AsyncCallbackSimple<StateContainerDTO>() {
          @Override
          public void onSuccess(final StateContainerDTO state) {
            stateManager.removeCache(parentToken);
            stateManager.setRetrievedStateAndGo(state);
            NotifyUser.hideProgress();
            // NotifyUser.info(i18n.tWithNT("[%s] created",
            // "New folder created, for instance", newName));
            folderViewer.highlightTitle();
          }
        });
    cache.remove(parentToken);
  }

  /**
   * Adds the participants.
   *
   * @param token the token
   * @param subGroup the sub group
   */
  public void addParticipants(final StateToken token, final SocialNetworkSubGroup subGroup) {
    contentService.get().addParticipants(session.getUserHash(), token,
        session.getCurrentGroupShortName(), subGroup, new AsyncCallback<Boolean>() {
          @Override
          public void onFailure(final Throwable caught) {
            NotifyUser.important(i18n.t("Seems that the list of partipants were added partially. Please, retry"));
          }

          @Override
          public void onSuccess(final Boolean result) {
            NotifyUser.info(result ? subGroup.equals(SocialNetworkSubGroup.PUBLIC) ? i18n.t("Shared with general public. Now anyone can participate")
                : i18n.t("Shared with members")
                : i18n.t("All these members are already partipating"));
          }
        });
  }

  /**
   * Del content.
   *
   * @param token the token
   */
  public void delContent(final StateToken token) {
    ConfirmAskEvent.fire(eventBus, i18n.t("Please confirm"), i18n.t("Are you sure?"), i18n.t("Yes"),
        i18n.t("No"), null, null, new OnAcceptCallback() {
          @Override
          public void onSuccess() {
            NotifyUser.showProgress();
            contentService.get().delContent(session.getUserHash(), token, defCallback);
          }
        });
  }

  /**
   * Purge all.
   *
   * @param token the token
   */
  public void purgeAll(final StateToken token) {
    ConfirmAskEvent.fire(eventBus, i18n.t("Please confirm"), i18n.t("Are you sure?"), i18n.t("Yes"),
        i18n.t("No"), null, null, new OnAcceptCallback() {
          @Override
          public void onSuccess() {
            NotifyUser.showProgress();
            contentService.get().purgeAll(session.getUserHash(), token, defCallback);
          }
        });
  }

  /**
   * Purge content.
   *
   * @param token the token
   */
  public void purgeContent(final StateToken token) {
    ConfirmAskEvent.fire(eventBus, i18n.t("Please confirm"), i18n.t("Are you sure?"), i18n.t("Yes"),
        i18n.t("No"), null, null, new OnAcceptCallback() {
          @Override
          public void onSuccess() {
            NotifyUser.showProgress();
            contentService.get().purgeContent(session.getUserHash(), token, defCallback);
          }
        });
  }
}
