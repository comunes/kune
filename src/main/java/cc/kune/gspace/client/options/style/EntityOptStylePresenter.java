/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.gspace.client.options.style;

import cc.kune.common.client.ui.UploadFinishedEvent;
import cc.kune.common.client.ui.UploadFinishedEvent.UploadFinishedHandler;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.rpcservices.UpDownServiceAsync;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.services.ImageSize;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.utils.ChangedLogosRegistry;
import cc.kune.gspace.client.options.EntityOptions;
import cc.kune.gspace.client.style.ClearBackgroundImageEvent;
import cc.kune.gspace.client.style.GSpaceBackgroundManager;
import cc.kune.gspace.client.style.SetBackgroundImageEvent;
import cc.kune.gspace.client.themes.GSpaceThemeSelectorPresenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityOptStylePresenter.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class EntityOptStylePresenter implements EntityOptStyle {

  /** The back manager. */
  private final GSpaceBackgroundManager backManager;

  private final ChangedLogosRegistry changedLogosRegistry;

  /** The entity options. */
  private final EntityOptions entityOptions;

  /** The event bus. */
  private final EventBus eventBus;

  /** The file download utils. */
  private final ClientFileDownloadUtils fileDownloadUtils;

  /** The group service. */
  private final Provider<GroupServiceAsync> groupService;

  /** The session. */
  private final Session session;

  /** The state manager. */
  private final StateManager stateManager;

  private final UpDownServiceAsync upDownService;

  /** The view. */
  private EntityOptStyleView view;

  /**
   * Instantiates a new entity opt style presenter.
   *
   * @param eventBus
   *          the event bus
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param entityOptions
   *          the entity options
   * @param groupService
   *          the group service
   * @param backManager
   *          the back manager
   * @param styleSelector
   *          the style selector
   * @param i18n
   *          the i18n
   * @param fileDownloadUtils
   *          the file download utils
   */
  protected EntityOptStylePresenter(final EventBus eventBus, final Session session,
      final StateManager stateManager, final EntityOptions entityOptions,
      final Provider<GroupServiceAsync> groupService, final GSpaceBackgroundManager backManager,
      final GSpaceThemeSelectorPresenter styleSelector, final ClientFileDownloadUtils fileDownloadUtils,
      final ChangedLogosRegistry changedLogosRegistry, final UpDownServiceAsync upDownService) {
    this.eventBus = eventBus;
    this.session = session;
    this.stateManager = stateManager;
    this.entityOptions = entityOptions;
    this.groupService = groupService;
    this.backManager = backManager;
    this.fileDownloadUtils = fileDownloadUtils;
    this.changedLogosRegistry = changedLogosRegistry;
    this.upDownService = upDownService;
  }

  /**
   * Clear back image.
   */
  private void clearBackImage() {
    groupService.get().clearGroupBackImage(session.getUserHash(), session.getCurrentStateToken(),
        new AsyncCallbackSimple<GroupDTO>() {
      @Override
      public void onSuccess(final GroupDTO result) {
        view.clearBackImage();
        backManager.clearBackgroundImage();
        ClearBackgroundImageEvent.fire(eventBus);
      }
    });
  }

  /**
   * Gets the view.
   *
   * @return the view
   */
  public IsWidget getView() {
    return view;
  }

  /**
   * Inits the.
   *
   * @param view
   *          the view
   */
  public void init(final EntityOptStyleView view) {
    this.view = view;
    entityOptions.addTab(view, view.getTabTitle());
    view.getClearBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        clearBackImage();
      }
    });
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        setState();
      }
    });
    eventBus.addHandler(SetBackgroundImageEvent.getType(),
        new SetBackgroundImageEvent.SetBackgroundImageHandler() {
      @Override
      public void onSetBackImage(final SetBackgroundImageEvent event) {
        backManager.setBackgroundImage();
        final StateToken token = event.getToken();
        changedLogosRegistry.add(token.getGroup());
        setBackImage(token);
      }
    });
    eventBus.addHandler(ClearBackgroundImageEvent.getType(),
        new ClearBackgroundImageEvent.ClearBackgroundImageHandler() {
      @Override
      public void onClearBackImage(final ClearBackgroundImageEvent event) {
        view.clearBackImage();
      }
    });
    view.addUploadFinishedHandler(new UploadFinishedHandler() {
      @Override
      public void onUploadFinished(final UploadFinishedEvent event) {
        upDownService.uploadBackground(session.getUserHash(), session.getCurrentStateToken(),
            event.getFile(), new AsyncCallbackSimple<Void>() {
          @Override
          public void onFailure(final Throwable caught) {
            super.onFailure(caught);
            view.reset();
          }

          @Override
          public void onSuccess(final Void result) {
            onSubmitComplete();
            view.reset();
          }
        });
      }
    });

  }

  /**
   * On submit complete.
   *
   * @param uploader
   *          the uploader
   */
  private void onSubmitComplete() {
    // final String response = uploader.getServerMessage().getMessage();
    SetBackgroundImageEvent.fire(eventBus, session.getCurrentState().getGroup().getStateToken());
  }

  /**
   * Sets the back image.
   *
   * @param token
   *          the new back image
   */
  private void setBackImage(final StateToken token) {
    view.setBackImage(fileDownloadUtils.getBackgroundResizedUrl(token, ImageSize.thumb));
  }

  /**
   * Sets the state.
   */
  private void setState() {
    final StateAbstractDTO state = session.getCurrentState();
    final GroupDTO group = state.getGroup();
    if (group.getBackgroundImage() == null) {
      view.clearBackImage();
    } else {
      setBackImage(group.getStateToken());
    }
  }

}
