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
package cc.kune.gspace.client.options.logo;

import cc.kune.core.client.events.AvatarChangedEvent;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UpDownServiceAsync;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.utils.ChangedLogosRegistry;
import cc.kune.gspace.client.options.UserOptions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

// TODO: Auto-generated Javadoc
/**
 * The Class UserOptLogoPresenter.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserOptLogoPresenter extends EntityOptLogoPresenter {

  /**
   * Instantiates a new user opt logo presenter.
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
  public UserOptLogoPresenter(final EventBus eventBus, final Session session,
      final UserOptions entityOptions, final StateManager stateManager,
      final Provider<UserServiceAsync> userService, final UserOptLogoView view,
      final ClientFileDownloadUtils downUtils, final ChangedLogosRegistry changedLogos,
      final UpDownServiceAsync upDownService) {
    super(eventBus, session, entityOptions, userService, downUtils, changedLogos, upDownService,
        stateManager);
    init(view);
  }

  /**
   * Inits the.
   *
   * @param view
   *          the view
   */
  private void init(final UserOptLogoView view) {
    super.init(view);
    view.setPersonalGroupsLabels();
  }

  @Override
  public void onSubmitComplete() {
    super.onSubmitComplete();
    final GroupDTO group = session.getCurrentState().getGroup();
    if (session.getCurrentUser().getShortName().equals(group.getShortName())) {
      userService.get().getUserAvatarBaser64(session.getUserHash(), group.getStateToken(),
          new AsyncCallbackSimple<String>() {
            @Override
            public void onSuccess(final String photoBinary) {
              AvatarChangedEvent.fire(eventBus, photoBinary);
            }
          });
    }
  }
}
