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
package cc.kune.gspace.client.options.logo;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.GroupChangedEvent;
import cc.kune.core.client.events.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.options.GroupOptions;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupOptLogoPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupOptLogoPresenter extends EntityOptLogoPresenter {

  /**
   * Instantiates a new group opt logo presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param session
   *          the session
   * @param entityOptions
   *          the entity options
   * @param stateManager
   *          the state manager
   * @param userService
   *          the user service
   * @param view
   *          the view
   * @param i18n
   *          the i18n
   */
  @Inject
  public GroupOptLogoPresenter(final EventBus eventBus, final Session session,
      final GroupOptions entityOptions, final StateManager stateManager,
      final Provider<UserServiceAsync> userService, final GroupOptLogoView view,
      final I18nTranslationService i18n) {
    super(eventBus, session, entityOptions, userService, i18n);
    init(view);
    stateManager.onGroupChanged(true, new GroupChangedHandler() {
      @Override
      public void onGroupChanged(final GroupChangedEvent event) {
        setState();
      }
    });
  }

  /**
   * Inits the.
   * 
   * @param view
   *          the view
   */
  private void init(final GroupOptLogoView view) {
    super.init(view);
    view.setNormalGroupsLabels();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.logo.EntityOptLogoPresenter#setState()
   */
  @Override
  protected void setState() {
    view.setUploadParams(session.getUserHash(), session.getCurrentStateToken().toString());
  }
}
