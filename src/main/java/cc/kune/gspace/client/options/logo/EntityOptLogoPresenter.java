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
 \*/
package cc.kune.gspace.client.options.logo;

import cc.kune.common.client.ui.UploadFinishedEvent;
import cc.kune.common.client.ui.UploadFinishedEvent.UploadFinishedHandler;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UpDownServiceAsync;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.utils.ChangedLogosRegistry;
import cc.kune.gspace.client.events.CurrentEntityChangedEvent;
import cc.kune.gspace.client.options.EntityOptions;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityOptLogoPresenter.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class EntityOptLogoPresenter implements GroupOptLogo, UserOptLogo {

  private final ChangedLogosRegistry changedLogosRegistry;

  private final ClientFileDownloadUtils downUtils;

  /** The entity options. */
  private final EntityOptions entityOptions;

  /** The event bus. */
  protected final EventBus eventBus;

  /** The session. */
  protected final Session session;

  private final StateManager stateManager;

  private final UpDownServiceAsync upDownService;

  /** The user service. */
  protected final Provider<UserServiceAsync> userService;

  /** The view. */
  protected EntityOptLogoView view;

  /**
   * Instantiates a new entity opt logo presenter.
   *
   * @param eventBus
   *          the event bus
   * @param session
   *          the session
   * @param entityOptions
   *          the entity options
   * @param userService
   *          the user service
   * @param stateManager
   * @param i18n
   *          the i18n
   */
  public EntityOptLogoPresenter(final EventBus eventBus, final Session session,
      final EntityOptions entityOptions, final Provider<UserServiceAsync> userService,
      final ClientFileDownloadUtils downUtils, final ChangedLogosRegistry changedLogosRegistry,
      final UpDownServiceAsync upDownService, final StateManager stateManager) {
    this.eventBus = eventBus;
    this.session = session;
    this.entityOptions = entityOptions;
    this.userService = userService;
    this.downUtils = downUtils;
    this.changedLogosRegistry = changedLogosRegistry;
    this.upDownService = upDownService;
    this.stateManager = stateManager;
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
  protected void init(final EntityOptLogoView view) {
    this.view = view;
    entityOptions.addTab(view, view.getTabTitle());
    view.addUploadFinishedHandler(new UploadFinishedHandler() {
      @Override
      public void onUploadFinished(final UploadFinishedEvent event) {
        upDownService.uploadLogo(session.getUserHash(), session.getCurrentStateToken(), event.getFile(),
            new AsyncCallbackSimple<Void>() {
              @Override
              public void onFailure(final Throwable caught) {
                super.onFailure(caught);
                view.reset();
              }

              @Override
              public void onSuccess(final Void result) {
                onSubmitComplete();
              }
            });
        view.reset();
      }
    });
    stateManager.onStateChanged(true, new StateChangedHandler() {

      @Override
      public void onStateChanged(final StateChangedEvent event) {
        final GroupDTO group = event.getState().getGroup();
        if (group.hasLogo()) {
          setLogo(group);
        } else {
          view.clearLogo();
        }
      }
    });
  }

  /**
   * On submit complete.
   *
   * @param uploader
   *          the uploader
   */
  public void onSubmitComplete() {
    final GroupDTO currentGroup = session.getCurrentState().getGroup();
    changedLogosRegistry.add(currentGroup.getShortName());
    CurrentEntityChangedEvent.fire(eventBus, currentGroup.getShortName(), currentGroup.getLongName());
    setLogo(currentGroup);
    view.reset();
  }

  private void setLogo(final GroupDTO group) {
    view.setLogo(downUtils.getLogoImageUrl(group.getShortName()));
  }
}
